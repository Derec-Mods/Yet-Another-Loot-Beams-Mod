package me.clefal.lootbeams.modules.beam;

import com.mojang.blaze3d.systems.RenderSystem;
import me.clefal.lootbeams.CommonClass;
import me.clefal.lootbeams.config.configs.LightConfig;
import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import me.clefal.lootbeams.data.lbitementity.rarity.LBColor;
import me.clefal.lootbeams.data.new_render.LootBeamRenderState;
import me.clefal.lootbeams.duck.PoseCopy;
import me.clefal.lootbeams.modules.dynamicprovider.DynamicProvider;
import me.clefal.lootbeams.modules.dynamicprovider.DynamicProviderModule;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
//? <26.2
import net.minecraft.client.renderer.MultiBufferSource;
//? >=26.2
//import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import java.util.function.Function;

public class BeamRenderer {

    private static final ResourceLocation MAIN_BEAM = CommonClass.id("textures/entity/main_beam.png");
    private static final ResourceLocation BEAM_TOP = CommonClass.id("textures/entity/beam_top.png");
    public static final BeamRenderer INSTANCE = new BeamRenderer();

    private boolean isShaderOn = false;

    public BeamRenderer() {
    }


    //? <26.2 {
    public void renderLootBeam(PoseStack stack, MultiBufferSource buffer, float partialTick, LBItemEntity LBItemEntity, boolean isShaderOn){
        PoseCopy last = (PoseCopy) ((Object) stack.last());
        this.isShaderOn = isShaderOn;
        renderLootBeam(buffer, LootBeamRenderState.BeamRenderState.make(LBItemEntity, last.loot_Beams_Refork$copy(), partialTick, isShaderOn));
    }

    public void renderLootBeam(MultiBufferSource buffer, LootBeamRenderState.BeamRenderState renderState) {
    //?} else {
    /*public void renderLootBeam(SubmitNodeCollector buffer, LootBeamRenderState.BeamRenderState renderState) {
        this.isShaderOn = renderState.isShaderOn;
    *///?}

        LBColor color = renderState.rarity.color();
        int lifeTime = renderState.fadeIn;

        PoseStack.Pose pose = renderState.poseStack;
        PoseStack stack = new PoseStack();
        stack.last().pose().set(pose.pose());
        stack.last().normal().set(pose.normal());



        LightConfig.Beam beamConfig = LightConfig.lightConfig.beam;
        LightConfig.Glow glowConfig = LightConfig.lightConfig.glow;
        int fadeInTime = beamConfig.beam_fade_in_time.get();

        var fadeInFactor = 1.0f * lifeTime / fadeInTime;
        int R = color.red();
        int G = color.green();
        int B = color.blue();

        float preBeamAlpha = beamConfig.beam_alpha.get();

        LocalPlayer player = Minecraft.getInstance().player;
        double distance = Mth.sqrt((float) player.distanceToSqr(renderState.location));
        float fadeDistance = beamConfig.beam_fade_in_distance.get();
        //Clefal: we don't actually need that much beamAlpha gimmick.
        //We should never cancel the beam, just make it hard to see.
        if (distance > fadeDistance) {
            float m = (float) distance - fadeDistance;

            preBeamAlpha *= 1 / Math.max(m / fadeDistance, 1.0f);
        }


        float beamRadius = 0.08f * beamConfig.beam_radius.get();
        float beamHeight = beamConfig.beam_height.get();
        float yOffset = beamConfig.beam_y_offset.get();
        if (beamConfig.common_shorter_beam) {
            if (renderState.rarity.absoluteOrdinal() <= 0) {
                beamHeight *= 0.65f;
                yOffset -= yOffset;
            }
        }


        int beamAlpha = ((int) (preBeamAlpha * 255));
        Optional<DynamicProvider> dynamicProvider1 = DynamicProviderModule.getDynamicProvider();
        if (dynamicProvider1.isPresent()) {
            beamAlpha *= Math.min(dynamicProvider1.get().getBeamLightFactor(), 1);
            beamHeight += dynamicProvider1.get().getBeamLightFactor() - 0.3f;
            beamRadius += 0.005f * dynamicProvider1.get().getGlowFactor();
        }

        beamAlpha *= fadeInFactor;
        beamHeight *= fadeInFactor;
        Vector3f playerPos = player.getPosition(renderState.partialTick).toVector3f();
        Vector3f targetPos = renderState.location.toVector3f();
        Vector3f sub = targetPos.sub(playerPos);
        Vector3f direction = sub.normalize();
        double v = Math.atan2(direction.x(), direction.z());

        float bloomRadius = beamRadius * 1.35f;
        int bloomAlpha = (int) (beamAlpha * 0.4f);

        stack.pushPose();
        stack.mulPose(Axis.YP.rotation((float) v));
        //Render main beam
        {
            stack.pushPose();
            stack.translate(0, yOffset + 1, 0);
            //main beam
            {

                emit(buffer, stack, MAIN_BEAM, R, G, B, beamAlpha, -beamRadius, beamRadius, -beamHeight, beamHeight, 0.001f);
                //main beam bloom
                {
                    emit(buffer, stack, MAIN_BEAM, R, G, B, bloomAlpha, -bloomRadius, bloomRadius, -beamHeight, beamHeight, 0.001f);
                }
            }

            //beam top
            {
                if (!isShaderOn){
                    emit(buffer, stack, BEAM_TOP, R, G, B, beamAlpha, beamRadius, -beamRadius, beamHeight * 3 / 2, beamHeight, 0.001f);
                } else {
                    beamHeight = beamHeight - 0.25f;
                    emit(buffer, stack, BEAM_TOP, R, G, B, beamAlpha, -beamRadius, beamRadius, beamHeight, beamHeight * 3 / 2, 0.001f);
                }

                //beam top bloom
                {
                    if (!isShaderOn){
                        emit(buffer, stack, BEAM_TOP, R, G, B, bloomAlpha, bloomRadius, -bloomRadius, beamHeight * 3 / 2, beamHeight, 0.001f);
                    } else {
                        emit(buffer, stack, BEAM_TOP, R, G, B, bloomAlpha, -bloomRadius, bloomRadius, beamHeight, beamHeight * 3 / 2, 0.001f);
                    }

                }
            }



            stack.popPose();
        }

        stack.popPose();


        {

            if (glowConfig.enable_glow && renderState.onGround) {

                stack.pushPose();
                //stack.translate(0, 0.01, 0);
                stack.mulPose(Axis.XP.rotationDegrees(90));
                float radius = glowConfig.glow_effect_radius.get();
                stack.translate(0, -radius, -0.01f);
                emit(buffer, stack, BeamRenderType.GLOW_TEXTURE, R, G, B, ((int) (beamAlpha * 0.4f)), -radius, radius, 0, 1, 0);
                stack.popPose();
            }

        }

    }


    //? <26.2 {
    private void emit(MultiBufferSource buffer, PoseStack stack, ResourceLocation location, int red, int green, int blue, int alpha, float minX, float maxX, float minY, float maxY, float z) {
        draw(stack.last(), buffer.getBuffer(BeamRenderType.getBeamRendertype(location, isShaderOn)), red, green, blue, alpha, minX, maxX, minY, maxY, z);
    }
    //?} else {
    /*private void emit(SubmitNodeCollector buffer, PoseStack stack, ResourceLocation location, int red, int green, int blue, int alpha, float minX, float maxX, float minY, float maxY, float z) {
        buffer.submitCustomGeometry(stack, BeamRenderType.getBeamRendertype(location, isShaderOn), (pose, consumer) ->
                draw(pose, consumer, red, green, blue, alpha, minX, maxX, minY, maxY, z));
    }
    *///?}

    private void draw(PoseStack.Pose matrixentry, VertexConsumer builder, int red, int green, int blue, int alpha, float minX, float maxX, float minY, float maxY, float z){
        if (isShaderOn) {
            drawOnShader(matrixentry, builder, red, green, blue, alpha, minX, maxX, minY, maxY, z);
        } else {
            drawWithoutShader(matrixentry, builder, red, green, blue, alpha, minX, maxX, minY, maxY, z);
        }

    }

    private void drawWithoutShader(PoseStack.Pose matrixentry, VertexConsumer builder, int red, int green, int blue, int alpha, float minX, float maxX, float minY, float maxY, float z){
        Matrix4f matrixpose = matrixentry.pose();
        //? legacy {
        /*builder.vertex(matrixpose, minX, minY, z).color(red, green, blue, alpha).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(matrixentry.normal(), 0.0F, 1.0F, 0.0F).endVertex();
        builder.vertex(matrixpose, minX, maxY, z).color(red, green, blue, alpha).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(matrixentry.normal(), 0.0F, 1.0F, 0.0F).endVertex();
        builder.vertex(matrixpose, maxX, maxY, z).color(red, green, blue, alpha).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(matrixentry.normal(), 0.0F, 1.0F, 0.0F).endVertex();
        builder.vertex(matrixpose, maxX, minY, z).color(red, green, blue, alpha).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(matrixentry.normal(), 0.0F, 1.0F, 0.0F).endVertex();
        *///? } else {
        builder.addVertex(matrixpose, minX, minY, z).setColor(red, green, blue, alpha).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(matrixentry, 0.0F, 1.0F, 0.0F);
        builder.addVertex(matrixpose, minX, maxY, z).setColor(red, green, blue, alpha).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(matrixentry, 0.0F, 1.0F, 0.0F);
        builder.addVertex(matrixpose, maxX, maxY, z).setColor(red, green, blue, alpha).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(matrixentry, 0.0F, 1.0F, 0.0F);
        builder.addVertex(matrixpose, maxX, minY, z).setColor(red, green, blue, alpha).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(matrixentry, 0.0F, 1.0F, 0.0F);
        //? }

    }

    private void drawOnShader(PoseStack.Pose matrixentry, VertexConsumer builder, int red, int green, int blue, int alpha, float minX, float maxX, float minY, float maxY, float z){
        Matrix4f matrixpose = matrixentry.pose();
        //? legacy {
        /*builder.vertex(matrixpose, minX, minY, z).uv(0, 1).color(red, green, blue, alpha).uv2(15728880).endVertex();
        builder.vertex(matrixpose, minX, maxY, z).uv(0, 0).color(red, green, blue, alpha).uv2(15728880).endVertex();
        builder.vertex(matrixpose, maxX, maxY, z).uv(1, 0).color(red, green, blue, alpha).uv2(15728880).endVertex();
        builder.vertex(matrixpose, maxX, minY, z).uv(1, 1).color(red, green, blue, alpha).uv2(15728880).endVertex();
        *///? } else {
        builder.addVertex(matrixpose, minX, minY, z).setUv(0, 1).setColor(red, green, blue, alpha).setLight(15728880);
        builder.addVertex(matrixpose, minX, maxY, z).setUv(0, 0).setColor(red, green, blue, alpha).setLight(15728880);
        builder.addVertex(matrixpose, maxX, maxY, z).setUv(1, 0).setColor(red, green, blue, alpha).setLight(15728880);
        builder.addVertex(matrixpose, maxX, minY, z).setUv(1, 1).setColor(red, green, blue, alpha).setLight(15728880);
        //? }

    }
}
