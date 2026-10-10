package me.clefal.lootbeams.utils;

public class ResourceLocationHelper {
    public static Identifier fromNameAndPath(String name, String path){
        //? if !=1.20.1 {
        return Identifier.fromNamespaceAndPath(name, path);
        //?} else {
        /*return new Identifier(name, path);
        *///?}

    }

    public static Identifier fromWholeName(String location){
        //? if !=1.20.1 {
        return Identifier.bySeparator(location, ':');
        //?} else {
        /*return Identifier.of(location, ':');
        *///?}

    }
}
