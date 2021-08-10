package com.imjustdoom.vanilla.gamedata.loottables.entries;

import com.imjustdoom.vanilla.gamedata.conditions.Condition;
import com.imjustdoom.vanilla.gamedata.loottables.LootTable;
import com.imjustdoom.vanilla.gamedata.loottables.LootTableEntryType;
import com.imjustdoom.vanilla.gamedata.loottables.LootTableFunction;
import com.imjustdoom.vanilla.gamedata.loottables.LootTableManager;

import java.util.List;

public class AlternativesType implements LootTableEntryType {
    @Override
    public LootTable.Entry create(LootTableManager lootTableManager, String name, List<Condition> conditions, List<LootTable.Entry> children, boolean expand, List<LootTableFunction> functions, int weight, int quality) {
        return new AlternativesEntry(this, children, weight, quality, conditions);
    }
}