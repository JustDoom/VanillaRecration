package com.imjustdoom.vanilla.gamedata.loottables.entries;

import com.imjustdoom.vanilla.gamedata.conditions.Condition;
import com.imjustdoom.vanilla.gamedata.loottables.LootTable;
import com.imjustdoom.vanilla.gamedata.loottables.LootTableEntryType;
import com.imjustdoom.vanilla.gamedata.loottables.LootTableFunction;
import com.imjustdoom.vanilla.gamedata.loottables.LootTableManager;
import net.minestom.server.MinecraftServer;
import net.minestom.server.utils.NamespaceID;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * minecraft:tag
 */
public class TagType implements LootTableEntryType {
    @Override
    public LootTable.Entry create(LootTableManager lootTableManager, String name, List<Condition> conditions, List<LootTable.Entry> children, boolean expand, List<LootTableFunction> functions, int weight, int quality) {
        try {
            return new TagEntry(this, MinecraftServer.getTagManager().load(NamespaceID.from(name), "items"), expand, weight, quality, conditions);
        } catch (FileNotFoundException e) {
            MinecraftServer.getExceptionManager().handleException(e);
            return null;
        }
    }
}