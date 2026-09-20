package me.clefal.lootbeams.utils;

import java.util.List;

/**
 * From https://github.com/TUsama/NirvanaLib
 * Nirvana was out of date for 1.21.x and we needed this in-tree.
 */
public class ModUtils {
    public static boolean isModLoaded(String id) {
        //? if fabric {
        /*return net.fabricmc.loader.api.FabricLoader.getInstance().isModLoaded(id);
        *///?} elif forge {
        /*return net.minecraftforge.fml.ModList.get().isLoaded(id);
        *///?} else {
        return net.neoforged.fml.ModList.get().isLoaded(id);
        //?}
    }

    public static List<String> getModList() {
        //? if fabric {
        /*return net.fabricmc.loader.api.FabricLoader.getInstance().getAllMods().stream()
                .map(container -> container.getMetadata().getId())
                .toList();
        *///?} elif forge {
        /*return net.minecraftforge.fml.ModList.get().getMods().stream()
                .map(net.minecraftforge.forgespi.language.IModInfo::getModId)
                .toList();
        *///?} else {
        return net.neoforged.fml.ModList.get().getMods().stream()
                .map(net.neoforged.neoforgespi.language.IModInfo::getModId)
                .toList();
        //?}
    }
}
