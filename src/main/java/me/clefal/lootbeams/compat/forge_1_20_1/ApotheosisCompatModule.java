//? if forge {
/*package me.clefal.lootbeams.compat.forge_1_20_1;

import me.clefal.lootbeams.utils.ModUtils;
import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import me.clefal.lootbeams.data.lbitementity.rarity.LBColor;
import me.clefal.lootbeams.data.lbitementity.rarity.LBRarity;
import me.clefal.lootbeams.events.RegisterLBRarityEvent;
import me.clefal.lootbeams.modules.ILBCompatModule;
import me.clefal.lootbeams.bus.SubscribeEvent;
import dev.shadowsoffire.apotheosis.Apotheosis;
import dev.shadowsoffire.apotheosis.adventure.affix.AffixHelper;
import dev.shadowsoffire.apotheosis.adventure.affix.salvaging.SalvageItem;
import dev.shadowsoffire.apotheosis.adventure.loot.RarityRegistry;
import dev.shadowsoffire.apotheosis.adventure.socket.gem.GemInstance;
import dev.shadowsoffire.apotheosis.adventure.socket.gem.GemItem;


import java.util.Optional;


public class ApotheosisCompatModule implements ILBCompatModule {
    @Override
    public boolean shouldBeEnable() {
        return ModUtils.isModLoaded(Apotheosis.MODID);
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.info("Detected Apotheosis, enable ApotheosisCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(new ApotheosisCompatModule());
        }
    }

    @SubscribeEvent
    public void onEnable(RegisterLBRarityEvent.Pre event) {
        event.register(itemEntity -> {
            var stack = itemEntity.getItem();
            var holder = AffixHelper.hasAffixes(stack) ? AffixHelper.getRarity(stack)
                    : stack.getItem() instanceof GemItem ? GemInstance.unsocketed(stack).rarity()
                    : stack.getItem() instanceof SalvageItem ? RarityRegistry.getMaterialRarity(stack.getItem())
                    : RarityRegistry.INSTANCE.emptyHolder();
            if (holder.is(RarityRegistry.INSTANCE.emptyHolder().getId()) || !holder.isBound()) {
                return Optional.empty();
            }
            return Optional.of(LBItemEntity.of(itemEntity, LBRarity.of(
                    holder.get().toComponent(),
                    LBColor.of(holder.get().getColor().getValue()),
                    holder.get().ordinal()
            )));
        });
    }
}
*///?}
