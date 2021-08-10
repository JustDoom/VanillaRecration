package com.imjustdoom.vanilla.gamedata.loottables.entries;

import com.imjustdoom.vanilla.gamedata.conditions.Condition;
import com.imjustdoom.vanilla.gamedata.loottables.LootTable;
import net.minestom.server.data.Data;
import net.minestom.server.item.ItemStack;

import java.util.List;

public class AnotherLootTableEntry extends LootTable.Entry {
    private final LootTable table;

    public AnotherLootTableEntry(AnotherLootTableType type, LootTable table, int weight, int quality, List<Condition> conditions) {
        super(type, weight, quality, conditions);
        this.table = table;
    }

    @Override
    public void generate(List<ItemStack> output, Data arguments) {
        output.addAll(table.generate(arguments));
    }
}