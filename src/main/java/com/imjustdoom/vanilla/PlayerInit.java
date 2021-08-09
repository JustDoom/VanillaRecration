package com.imjustdoom.vanilla;

import com.imjustdoom.vanilla.blocks.VanillaBlocks;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.ItemEntity;
import net.minestom.server.event.item.ItemDropEvent;
import net.minestom.server.event.item.PickupItemEvent;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.network.ConnectionManager;
import net.minestom.server.utils.time.TimeUnit;

public class PlayerInit {

    public PlayerInit() {

        ConnectionManager connectionManager = MinecraftServer.getConnectionManager();
        connectionManager.addPlayerInitialization(player -> {
            player.addEventCallback(PlayerBlockBreakEvent.class, event -> {
                if (player.getGameMode() != GameMode.CREATIVE) {
                    VanillaBlocks.dropOnBreak(player.getInstance(), event.getBlockPosition());
                }
            });

            player.addEventCallback(PickupItemEvent.class, event -> {
                boolean couldAdd = player.getInventory().addItemStack(event.getItemStack());
                event.setCancelled(!couldAdd); // Cancel event if player does not have enough inventory space
            });

            player.addEventCallback(ItemDropEvent.class, event -> {
                ItemStack droppedItem = event.getItemStack();

                ItemEntity itemEntity = new ItemEntity(droppedItem, player.getPosition().clone().add(0, 1.5f, 0));
                itemEntity.setPickupDelay(500, TimeUnit.MILLISECOND);
                itemEntity.setInstance(player.getInstance());
                Vec velocity = player.getPosition().direction().mul(6);
                itemEntity.setVelocity(velocity);
            });
        });
    }
}
