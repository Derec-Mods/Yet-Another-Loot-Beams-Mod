//? if =1.20.1 && fabric {
/*package me.clefal.lootbeams.compat.fabric_1_20_1;

import me.clefal.lootbeams.utils.ModUtils;
import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import me.clefal.lootbeams.data.lbitementity.rarity.LBColor;
import me.clefal.lootbeams.data.lbitementity.rarity.LBRarity;
import me.clefal.lootbeams.events.RegisterLBRarityEvent;
import me.clefal.lootbeams.modules.ILBCompatModule;
import dev.shadowsoffire.apotheosis.Apotheosis;
import dev.shadowsoffire.apotheosis.adventure.affix.AffixHelper;
import dev.shadowsoffire.apotheosis.adventure.affix.salvaging.SalvageItem;
import dev.shadowsoffire.apotheosis.adventure.loot.RarityRegistry;
import dev.shadowsoffire.apotheosis.adventure.socket.gem.GemInstance;
import dev.shadowsoffire.apotheosis.adventure.socket.gem.GemItem;
import me.clefal.lootbeams.bus.SubscribeEvent;

import java.util.Optional;


public class ZenithCompatModule implements ILBCompatModule {
    public final static ZenithCompatModule INSTANCE = new ZenithCompatModule();
    @Override
    public boolean shouldBeEnable() {
        return ModUtils.isModLoaded(Apotheosis.MODID);
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.info("Detected Zenith, enable ZenithCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(INSTANCE);
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
