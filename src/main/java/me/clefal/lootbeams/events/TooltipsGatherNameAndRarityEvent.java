package me.clefal.lootbeams.events;

import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import net.minecraft.network.chat.Component;
import me.clefal.lootbeams.bus.Event;

import java.util.EnumMap;
import java.util.Map;

public class TooltipsGatherNameAndRarityEvent extends Event {

    public enum Case{
        NAME,
        RARITY
    }
    public LBItemEntity lbItemEntity;
    public Map<Case, Component> gather = new EnumMap<>(Case.class);

    public TooltipsGatherNameAndRarityEvent(LBItemEntity lbItemEntity) {
        this.lbItemEntity = lbItemEntity;
    }
}
