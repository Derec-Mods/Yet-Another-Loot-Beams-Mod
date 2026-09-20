//? if forge {
/*package me.clefal.lootbeams.compat.forge_1_20_1.mine_and_slash;

import me.clefal.lootbeams.utils.ModUtils;
import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import me.clefal.lootbeams.data.lbitementity.rarity.LBColor;
import me.clefal.lootbeams.data.lbitementity.rarity.LBRarity;
import me.clefal.lootbeams.events.RegisterConfigConditionEvent;
import me.clefal.lootbeams.events.RegisterLBRarityEvent;
import me.clefal.lootbeams.modules.ILBCompatModule;
import me.clefal.lootbeams.bus.SubscribeEvent;
import com.robertx22.addons.orbs_of_crafting.currency.IItemAsCurrency;
import com.robertx22.mine_and_slash.database.data.gear_slots.GearSlot;
import com.robertx22.mine_and_slash.database.data.gear_types.bases.SlotFamily;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.interfaces.IRarityItem;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.gemrunes.GemItem;
import net.minecraft.network.chat.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MineAndSlashCompatModule implements ILBCompatModule {

    public final static MineAndSlashCompatModule INSTANCE = new MineAndSlashCompatModule();

    private final List<String> rarities = new ArrayList<>();

    public static LBRarity getNonSoulRarity() {
        return LBRarity.of(Component.translatable("lootbeams.mod_rarity.non_soul"), LBColor.of(new Color(121, 121, 121).getRGB()), -1);
    }

    @Override
    public boolean shouldBeEnable() {
        return ModUtils.isModLoaded(SlashRef.MODID);
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.info("Detected Mine and Slash, enable MineAndSlashCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(INSTANCE);
            INSTANCE.rarities.add("common");
            INSTANCE.rarities.add("uncommon");
            INSTANCE.rarities.add("rare");
            INSTANCE.rarities.add("epic");
            INSTANCE.rarities.add("legendary");
            INSTANCE.rarities.add("mythic");
            INSTANCE.rarities.add("unique");
            INSTANCE.rarities.add("runeword");

        }
    }

    @SubscribeEvent
    public void onEnable(RegisterLBRarityEvent.Pre event) {
        event.register(itemEntity -> {
                    var stack = itemEntity.getItem();
                    if (StackSaving.GEARS.has(stack)) {
                        var rarity = StackSaving.GEARS.loadFrom(stack).getRarity();
                        return Optional.of(LBItemEntity.of(itemEntity, LBRarity.of(
                                rarity.locName(),
                                LBColor.of(rarity.textFormatting().getColor()),
                                rarities.indexOf(rarity.guid)
                        )));
                    }
                    if (ICommonDataItem.load(stack) != null) {
                        var rarity = ICommonDataItem.load(stack).getRarity();
                        return Optional.of(LBItemEntity.of(itemEntity, LBRarity.of(
                                rarity.locName(),
                                LBColor.of(rarity.textFormatting().getColor()),
                                rarities.indexOf(rarity.guid)
                        )));
                    }
                    if (stack.getItem() instanceof IRarityItem) {
                        var rarity = ((IRarityItem) stack.getItem()).getItemRarity(stack);
                        return Optional.of(LBItemEntity.of(itemEntity, LBRarity.of(
                                rarity.locName(),
                                LBColor.of(rarity.textFormatting().getColor()),
                                rarities.indexOf(rarity.guid)
                        )));
                    }
                    if (stack.getItem() instanceof GemItem) {
                        var rarity = ((GemItem) stack.getItem()).getBaseGem().getRarity();
                        return Optional.of(LBItemEntity.of(itemEntity, LBRarity.of(
                                rarity.locName(),
                                LBColor.of(rarity.textFormatting().getColor()),
                                rarities.indexOf(rarity.guid)
                        )));
                    }
                    if (StackSaving.OMEN.has(stack)) {
                        var rarity = StackSaving.OMEN.loadFrom(stack).getRarity();
                        return Optional.of(LBItemEntity.of(itemEntity, LBRarity.of(
                                rarity.locName(),
                                LBColor.of(rarity.textFormatting().getColor()),
                                rarities.indexOf(rarity.guid)
                        )));
                    }
                    if (GearSlot.getSlotOf(itemEntity.getItem()) != null) {
                        return Optional.of(LBItemEntity.of(itemEntity, getNonSoulRarity()));
                    }
                    return Optional.empty();
                });

    }

    @SubscribeEvent
    public void registerEquipmentCondition(RegisterConfigConditionEvent.RegisterEquipmentItemEvent event) {
        event.conditions.add(lbItemEntity -> {
            if (lbItemEntity.item().getItem().getClass().toString().contains("com.robertx22.mine_and_slash.vanilla_mc.items.gearitems"))
                return true;
            GearSlot slotOf = GearSlot.getSlotOf(lbItemEntity.item().getItem());
            return (slotOf != null && slotOf.fam != SlotFamily.NONE);
        });
    }

    @SubscribeEvent
    public void registerWhitelistCondition(RegisterConfigConditionEvent.RegisterWhitelistEvent event) {
        //currency, I prefer to show the currency always.
        event.conditions.add(lbItemEntity -> lbItemEntity.item().getItem().getItem() instanceof IItemAsCurrency);
    }

}
*///?}
