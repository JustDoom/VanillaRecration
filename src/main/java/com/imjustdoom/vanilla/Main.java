package com.imjustdoom.vanilla;

import com.imjustdoom.vanilla.gamedata.loottables.LootTableManager;
import com.imjustdoom.vanilla.gamedata.loottables.VanillaLootTables;
import net.minestom.server.MinecraftServer;
import net.minestom.server.extensions.Extension;

public class Main extends Extension {

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public Main(){
        instance = this;
    }

    @Override
    public void initialize() {
        VanillaLootTables.register(new LootTableManager());

        new PlayerInit();
    }

    @Override
    public void terminate() {

    }
}