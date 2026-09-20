//? malum {
package me.clefal.lootbeams.compat.multiversion_compat;

import me.clefal.lootbeams.bus.SubscribeEvent;
import me.clefal.lootbeams.utils.ModUtils;
import com.sammy.malum.MalumMod;



import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import me.clefal.lootbeams.data.lbitementity.rarity.LBColor;
import me.clefal.lootbeams.data.lbitementity.rarity.LBRarity;
import me.clefal.lootbeams.events.RegisterLBRarityEvent;
import me.clefal.lootbeams.modules.ILBCompatModule;

import java.util.Optional;

//? if 1.21.1 {
import com.sammy.malum.registry.common.item.MalumDataComponents;
//?} else {
/*import com.sammy.malum.common.item.spirit.RitualShardItem;
*///?}

public class MalumCompatModule implements ILBCompatModule {
    public static final MalumCompatModule INSTANCE = new MalumCompatModule();

    @Override
    public boolean shouldBeEnable() {
        return ModUtils.isModLoaded(MalumMod.MALUM);
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.info("Detected Malum, enable MalumCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(INSTANCE);
        }
    }

    @SubscribeEvent
    public void onEnable(RegisterLBRarityEvent.Pre event) {
        event.register(itemEntity ->
                Optional.ofNullable(itemEntity.getItem())
                        //? if 1.20.1 {
                        /*.flatMap(x -> Optional.ofNullable(RitualShardItem.getRitualType(x)))
                        .map(x -> x.spirit)
                        *///?} else {
                        .flatMap(x -> Optional.ofNullable(x.get(MalumDataComponents.SPIRIT_JAR_CONTENTS)).map(data -> data.spirit()))
                        //?}
                        .map(x -> {
                            LBRarity old = LBRarity.ofVanillaRarity(x.getItemRarity());
                            return LBItemEntity.of(itemEntity,
                                    LBRarity.of(
                                            old.name(),
                                            LBColor.ofMutable(x.getTextColor(false)),
                                            -1
                                    ));
                        })
);
    }
}
//?}
