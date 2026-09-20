package me.clefal.lootbeams.utils;

/**
 * From https://github.com/TUsama/NirvanaLib
 * Nirvana was out of date for 1.21.x and we needed this in-tree.
 */
public record Tuple3<A, B, C>(A _1, B _2, C _3) {
    public static <A, B, C> Tuple3<A, B, C> of(A a, B b, C c) {
        return new Tuple3<>(a, b, c);
    }
}
