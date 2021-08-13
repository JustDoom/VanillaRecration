package com.imjustdoom.vanilla.blocks;

import net.minestom.server.coordinate.Point;
import net.minestom.server.data.Data;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockHandler;
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
            short id = baseBlock.stateId();
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

    /**
     * Create data for this block
     *
     * @param blockPosition
     * @param data
     * @return
     */
    @Override
    public Data createData(Instance instance, Point blockPosition, Data data) {
        return data;
    }

    /**
     * Interact with this block, depending on properties
     */
    @Override
    public boolean onInteract(Player player, Player.Hand hand, Point blockPosition, Data data) {
        return false;
    }

    public short getBaseBlockId() {
        return baseBlock.stateId();
    }

    @Override
    public short getCustomBlockId() {
        return baseBlock.stateId();
    }

    @Override
    public int getBreakDelay(Player player, Point position, byte stage, Set<Player> breakers) {
        return -1;
    }

    public short getVisualBlockForPlacement(Player player, Player.Hand hand, Point blockPosition) {
        return getBaseBlockId();
    }

    /**
     * Loads the BlockEntity information from the given NBT, during world loading from the Anvil format.
     * Should be stored in the Data object returned by this function.
     * It is allowed (and encouraged) to modify 'originalData' and returning it.
     * <p>
     * Your method {@link #createData(Instance, Point, Data)} should return a non-null data object if you want to use this method easily
     *
     * @param nbt          the nbt data to read from
     * @param instance     instance in which the tile entity is being loaded
     * @param position     position at which this block is. DON'T CACHE IT
     * @param originalData data present at the current position
     * @return a Data object with the loaded information. Can be originalData, a new object, or even null if you don't use the TE info
     */
    public Data readBlockEntity(NBTCompound nbt, Instance instance, Point position, Data originalData) {
        return originalData;
    }

    public BlockStates getBlockStates() {
        return blockStates;
    }
}
