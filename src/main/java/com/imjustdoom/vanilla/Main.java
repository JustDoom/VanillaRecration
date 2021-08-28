package com.imjustdoom.vanilla;

import com.imjustdoom.vanilla.commands.GamemodeCommand;
import com.imjustdoom.vanilla.commands.StopCommand;
import com.imjustdoom.vanilla.commands.TeleportCommand;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.ItemEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.item.ItemDropEvent;
import net.minestom.server.event.item.PickupItemEvent;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSkinInitEvent;
import net.minestom.server.extensions.Extension;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.item.ItemStack;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class Main extends Extension {

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public Main(){
        instance = this;
    }

    private final Map<Player, PlayerSkin> skinMap = new HashMap<>();

    @Override
    public void initialize() {
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();
        instanceContainer.setChunkGenerator(new NoiseTestGenerator());

        MinecraftServer.getCommandManager().register(new GamemodeCommand());
        MinecraftServer.getCommandManager().register(new StopCommand());
        MinecraftServer.getCommandManager().register(new TeleportCommand());

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();

        globalEventHandler.addListener(PlayerSkinInitEvent.class, event -> {
            final Player player = event.getPlayer();

            if(skinMap.containsKey(player)){
                event.setSkin(skinMap.get(player));
            } else {
                PlayerSkin skin = PlayerSkin.fromUsername(player.getUsername());
                skinMap.put(player, skin);
                event.setSkin(skin);
            }
        });

        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();

            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Pos(0, 92, 0));
        });

        globalEventHandler.addListener(ItemDropEvent.class, event -> {
            ItemStack droppedItem = event.getItemStack();
            Player player = event.getPlayer();
            Pos playerPos = player.getPosition();

            ItemEntity itemEntity = new ItemEntity(droppedItem);
            itemEntity.setPickupDelay(Duration.ofMillis(500));
            itemEntity.setInstance(player.getInstance(), playerPos.withY(y -> y + 1.5));
            Vec velocity = playerPos.direction().mul(6);
            itemEntity.setVelocity(velocity);
        });

        globalEventHandler.addListener(PickupItemEvent.class, event -> {
            final Entity entity = event.getLivingEntity();
            if (entity instanceof Player) {
                // Cancel event if player does not have enough inventory space
                final ItemStack itemStack = event.getItemEntity().getItemStack();
                event.setCancelled(!((Player) entity).getInventory().addItemStack(itemStack));
            }
        });
    }

    @Override
    public void terminate() {
    }
}