//? >= 26.2 {
/*package me.clefal.lootbeams.mixin.refactor;

import me.clefal.lootbeams.utils.Tuple3;
import com.llamalad7.mixinextras.sugar.Local;
import me.clefal.lootbeams.modules.Hooker;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.extract.LevelExtractor;
import net.minecraft.client.renderer.state.LevelRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelExtractor.class)
public class LevelExtractorMixin {
    @Inject(
            method = "extractVisibleEntities", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/extract/LevelExtractor;extractEntity(Lnet/minecraft/world/entity/Entity;F)Lnet/minecraft/client/renderer/entity/state/EntityRenderState;",
            shift = At.Shift.AFTER
    ))
    private void grabEntity(Camera camera, Frustum frustum, DeltaTracker deltaTracker, LevelRenderState renderState, CallbackInfo ci, @Local Entity entity, @Local float f) {
        if (entity instanceof ItemEntity item) {
            var x = Mth.lerp((double) f, entity.xOld, entity.getX());
            var y = Mth.lerp((double) f, entity.yOld, entity.getY());
            var z = Mth.lerp((double) f, entity.zOld, entity.getZ());
            Hooker.retainedEntities.add(Tuple3.of(item, deltaTracker.getGameTimeDeltaTicks(), new Vec3(x, y, z)));
        }
    }
}
*///? }
