package me.clefal.lootbeams.bus;

/**
 * From https://github.com/TUsama/NirvanaLib
 * Nirvana was out of date for 1.21.x and we needed this in-tree.
 */
public class Event {
    private boolean canceled;

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
