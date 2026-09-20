package me.clefal.lootbeams.data.lbitementity;

import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.data.lbitementity.rarity.ILBRarityApplier;
import me.clefal.lootbeams.data.lbitementity.rarity.LBRarity;
import me.clefal.lootbeams.events.RegisterLBRarityEvent;
import me.clefal.lootbeams.modules.ILBModulePersistentData;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Rarity;

import java.util.*;

public class InternalLBItemEntityProvider implements ILBModulePersistentData {
    public final static InternalLBItemEntityProvider INSTANCE = new InternalLBItemEntityProvider();
    private final static LinkedList<ILBRarityApplier> sources = new LinkedList<>();

    static {
        INSTANCE.initData();
    }


    public static LBItemEntity getLBItemEntity(ItemEntity entity){
        Iterator<ILBRarityApplier> iterator = sources.iterator();
        while (iterator.hasNext()){
            ILBRarityApplier next = iterator.next();
            Optional<LBItemEntity> apply = next.apply(entity);
            if (apply.isPresent()) {
                return apply.get();
            }
        }
        return LBItemEntity.of(entity, LBRarity.ofVanillaRarity(Rarity.COMMON));
    }

    @Override
    public void initData() {
        ArrayList<ILBRarityApplier> appliers = new ArrayList<>();
        LootBeamsConstants.EVENT_BUS.post(new RegisterLBRarityEvent.Pre(appliers));
        LootBeamsConstants.EVENT_BUS.post(new RegisterLBRarityEvent.Post(appliers));
        sources.addAll(appliers);
        //vanilla rarity transformer
        sources.add(itemEntity -> Optional.of(LBItemEntity.of(itemEntity, LBRarity.ofVanillaRarity(itemEntity.getItem().getRarity()))));
    }

    @Override
    public void updateData() {
        throw new UnsupportedOperationException();
    }
}
