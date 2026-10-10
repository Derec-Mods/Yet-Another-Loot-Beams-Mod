package me.clefal.lootbeams.utils;

import lombok.experimental.UtilityClass;
import net.minecraft.resources.Identifier;

@UtilityClass
public class ResourceLocationHelper {
    public Identifier fromNameAndPath(String name, String path){
        //? if !=1.20.1 {
        return Identifier.fromNamespaceAndPath(name, path);
        //?} else {
        /*return new Identifier(name, path);
        *///?}

    }

    public Identifier fromWholeName(String location){
        //? if !=1.20.1 {
        return Identifier.bySeparator(location, ':');
        //?} else {
        /*return Identifier.of(location, ':');
        *///?}

    }
}
