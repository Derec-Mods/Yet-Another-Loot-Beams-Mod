package me.clefal.lootbeams.data.lbitementity.rarity;

import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import net.minecraft.world.entity.item.ItemEntity;

import java.util.Optional;
import java.util.function.Function;

@FunctionalInterface
public interface ILBRarityApplier extends Function<ItemEntity, Optional<LBItemEntity>> {
}
