package com.imjustdoom.vanilla.gamedata.loottables.entries;

import com.imjustdoom.vanilla.gamedata.conditions.Condition;
import com.imjustdoom.vanilla.gamedata.loottables.LootTable;
import com.imjustdoom.vanilla.gamedata.loottables.LootTableEntryType;
import com.imjustdoom.vanilla.gamedata.loottables.LootTableFunction;
import com.imjustdoom.vanilla.gamedata.loottables.LootTableManager;
import net.minestom.server.utils.NamespaceID;

import java.io.FileNotFoundException;
import java.util.List;

public class AnotherLootTableType implements LootTableEntryType {
    @Override
    public LootTable.Entry create(LootTableManager lootTableManager, String name, List<Condition> conditions, List<LootTable.Entry> children, boolean expand, List<LootTableFunction> functions, int weight, int quality) {
        try {
            return new AnotherLootTableEntry(this, lootTableManager.load(NamespaceID.from(name)), weight, quality, conditions);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(name+" is not a valid loot table name", e);
        }
    }
}