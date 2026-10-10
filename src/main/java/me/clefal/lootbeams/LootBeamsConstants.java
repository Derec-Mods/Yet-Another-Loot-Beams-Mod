package me.clefal.lootbeams;

import me.clefal.lootbeams.bus.LBEventBus;
import net.minecraft.resources.Identifier;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LootBeamsConstants {

    public static final String MOD_NAME = "Yet Another Loot Beams Mod";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    public static final String MODID = "lootbeams";
    public static final Identifier LOOT_DROP = Identifier.tryBuild(MODID, "loot_drop");
    public static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger();
    public static final LBEventBus EVENT_BUS = new LBEventBus();
}
