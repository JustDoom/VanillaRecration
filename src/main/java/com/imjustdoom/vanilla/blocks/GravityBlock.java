package com.imjustdoom.vanilla.blocks;

import com.imjustdoom.vanilla.entity.FallingBlockEntity;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.data.Data;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.utils.time.TimeUnit;
import org.jetbrains.annotations.NotNull;

public class GravityBlock extends VanillaBlock {
    public GravityBlock(Block baseBlock) {
        super(baseBlock);
    }

    @Override
    public void onPlace(Instance instance, Point blockPosition, Data data) {
        instance.scheduleUpdate(2, TimeUnit.SERVER_TICK, blockPosition);
    }

    @Override
    public void update(Instance instance, Point blockPosition, Data data) {
        Block below = Block.fromStateId(instance.getBlockStateId(blockPosition.x(), blockPosition.y()-1, blockPosition.z()));
        if(below.isAir()) {
            instance.scheduleUpdate(2, TimeUnit.TICK, blockPosition);
        }
    }

    @Override
    public void scheduledUpdate(Instance instance, Point blockPosition, Data data) {
        Block below = Block.fromStateId(instance.getBlockStateId(blockPosition.getX(), blockPosition.getY()-1, blockPosition.getZ()));
        if(below.isAir()) {
            instance.setBlock(blockPosition, Block.AIR);

            Pos initialPosition = new Pos(blockPosition.x() + 0.5f, Math.round(blockPosition.y()), blockPosition.z() + 0.5f);
            FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(getBaseBlock(), this, initialPosition);

            fallingBlockEntity.setInstance(instance);
        }
    }

    @Override
    protected BlockPropertyList createPropertyValues() {
        return new BlockPropertyList();
    }

    @Override
    public @NotNull NamespaceID getNamespaceId() {
        return null;
    }
}