package com.imjustdoom.vanilla.blocks;

import net.minestom.server.coordinate.Point;
import net.minestom.server.data.Data;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockHandler;
import org.jetbrains.annotations.NotNull;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

import java.util.List;
import java.util.Set;

public abstract class VanillaBlock implements BlockHandler {

    private final Block baseBlock;
    private final BlockPropertyList properties;
    private final BlockStates blockStates;
    private final BlockState baseBlockState;

    public VanillaBlock(Block baseBlock) {
        //super(baseBlock, "vanilla_" + baseBlock.name().toLowerCase());
        this.baseBlock = baseBlock;
        this.properties = createPropertyValues();

        // create block states
        this.blockStates = new BlockStates(properties);
        List<String[]> allVariants = properties.getCartesianProduct();
        if (allVariants.isEmpty()) {
            short id = baseBlock.getBlockId();
            BlockState state = new BlockState(id, blockStates);
            blockStates.add(state);
        } else {
            for (String[] variant : allVariants) {
                short id = baseBlock.withProperties(variant);
                BlockState blockState = new BlockState(id, blockStates, variant);
                blockStates.add(blockState);
            }
        }
        baseBlockState = blockStates.getDefault();
    }

    protected abstract BlockPropertyList createPropertyValues();

    public BlockState getBaseBlockState() {
        return baseBlockState;
    }

    public Block getBaseBlock() {
        return baseBlock;
    }

    @Override
    public void onPlace(@NotNull Placement placement) {

    }

    @Override
    public void onDestroy(@NotNull BlockHandler.Destroy destroy) {

    }

    @Override
    public void tick(@NotNull BlockHandler.Tick tick) {

    }

    /**
     * Interact with this block, depending on properties
     */
    @Override
    public boolean onInteract(@NotNull BlockHandler.Interaction interaction) {
        return false;
    }

    public short getBaseBlockId() {
        return baseBlock.getBlockId();
    }

    public short getVisualBlockForPlacement(Player player, Player.Hand hand, Point blockPosition) {
        return getBaseBlockId();
    }

    public Data readBlockEntity(NBTCompound nbt, Instance instance, Point position, Data originalData) {
        return originalData;
    }

    public BlockStates getBlockStates() {
        return blockStates;
    }
}
