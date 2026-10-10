package me.clefal.lootbeams;

import me.clefal.lootbeams.utils.ResourceLocationHelper;
import net.minecraft.resources.Identifier;

public class CommonClass {


    public static void init() {

    }

    public static Identifier id(String path) {
        return ResourceLocationHelper.fromNameAndPath(LootBeamsConstants.MODID, path);
    }
}