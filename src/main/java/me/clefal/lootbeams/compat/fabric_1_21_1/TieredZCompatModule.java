//? if =1.21.1 && fabric {
/*package me.clefal.lootbeams.compat.fabric_1_21_1;

import me.clefal.lootbeams.bus.SubscribeEvent;
import me.clefal.lootbeams.utils.ModUtils;
import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import me.clefal.lootbeams.data.lbitementity.rarity.LBColor;
import me.clefal.lootbeams.data.lbitementity.rarity.LBRarity;
import me.clefal.lootbeams.events.RegisterLBRarityEvent;
import me.clefal.lootbeams.modules.ILBCompatModule;
import draylar.tiered.Tiered;
import draylar.tiered.api.PotentialAttribute;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;

public class TieredZCompatModule implements ILBCompatModule {

    public final static TieredZCompatModule INSTANCE = new TieredZCompatModule();

    private List<String> rarities;

    @Override
    public boolean shouldBeEnable() {
        try {
            Class.forName("draylar.tiered.Tiered");
        } catch (ClassNotFoundException e) {
            return false;
        }
        return ModUtils.isModLoaded("tiered");
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.info("Detected TieredZ, enable TieredZCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(INSTANCE);
            this.rarities = List.of(
                    "common",
                    "uncommon",
                    "rare",
                    "epic",
                    "legendary",
                    "unique");
        }
    }

    @SubscribeEvent
    public void onEnable(RegisterLBRarityEvent.Pre event) {
        event.register(itemEntity -> {
                    //copy from ItemStackClientMixin getName
                    ItemStack item = itemEntity.getItem();
                    if (item.get(Tiered.TIER) == null) return Optional.empty();
                    ResourceLocation parse = ResourceLocation.parse(item.get(Tiered.TIER).tier());
                    if (item.get(Tiered.TIER) != null && Tiered.ATTRIBUTE_DATA_LOADER.getItemAttributes().containsKey(parse)) {
                        PotentialAttribute potentialAttribute = Tiered.ATTRIBUTE_DATA_LOADER.getItemAttributes().get(parse);
                        if (potentialAttribute != null) {
                            String id = potentialAttribute.getID();
                            Optional<String> find = this.rarities.stream().filter(id::contains).findFirst();
                            return Optional.of(LBItemEntity.of(itemEntity, LBRarity.of(
                                    Component.translatable(id + ".label"),
                                    LBColor.fromRGB(potentialAttribute.getStyle().getColor().getValue()),
                                    find.isEmpty() ? 0 : this.rarities.indexOf(find.get())
                            )));
                        }
                    }

                    return Optional.empty();

                }


        );

    }
}

*///?}
