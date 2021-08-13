package com.imjustdoom.vanilla.entity;

import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.ItemEntity;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockHandler;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import java.util.Random;

public class FallingBlockEntity extends LivingEntity {
    private final Block baseBlock;
    private final BlockHandler toPlace;

    public FallingBlockEntity(Block baseBlock, BlockHandler toPlace, Pos initialPosition) {
        super(EntityType.FALLING_BLOCK);
        this.baseBlock = baseBlock;
        this.toPlace = toPlace;
        //setGravity(0.025f, getGravityAcceleration());
        setBoundingBox(0.98f, 0.98f, 0.98f);
    }

    @Override
    public void update(long time) {
        if(isOnGround()) {
            Point position = getPosition().toBlockPosition().subtract(0, 1, 0);
            if(instance.getBlockStateId(position) != Block.AIR.stateId()) {
                // landed on non-full block, break into item
                Material correspondingItem = Material.valueOf(baseBlock.name()); // TODO: ugly way of finding corresponding item, change
                ItemStack stack = ItemStack.of(correspondingItem, (byte) 1);
                ItemEntity itemForm = new ItemEntity(stack, new Pos(position.x()+0.5f, position.y(), position.z()+0.5f));

                Random rng = new Random();
                itemForm.getVelocity().setX((float) rng.nextGaussian()*2f);
                itemForm.getVelocity().setY(rng.nextFloat()*2.5f+2.5f);
                itemForm.getVelocity().setZ((float) rng.nextGaussian()*2f);

                itemForm.setInstance(instance);
            } else {
                if(toPlace != null) {
                    instance.setSeparateBlocks(position.x(), position.y(), position.z(), baseBlock.stateId(), toPlace.getCustomBlockId());
                } else {
                    instance.setBlock(getPosition().toBlockPosition(), baseBlock);
                }
            }
            remove();
        }
    }
}