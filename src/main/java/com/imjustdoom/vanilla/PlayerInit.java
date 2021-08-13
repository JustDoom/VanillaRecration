package com.imjustdoom.vanilla;

import com.justdoom.vanillafeatures.blocks.VanillaBlocks;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.ItemEntity;
import net.minestom.server.event.item.ItemDropEvent;
import net.minestom.server.event.item.PickupItemEvent;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.network.ConnectionManager;
import net.minestom.server.utils.Vector;
import net.minestom.server.utils.time.TimeUnit;

public class PlayerInit {

    public PlayerInit(){

        ConnectionManager connectionManager = MinecraftServer.getConnectionManager();
        connectionManager.addPlayerInitialization(player -> {
                player.addEventCallback(PlayerBlockBreakEvent.class, event -> {
                    if (player.getGameMode() != GameMode.CREATIVE) {
                        VanillaBlocks.dropOnBreak(player.getInstance(), event.getBlockPosition());
                    }
                });
        });
    }
}
