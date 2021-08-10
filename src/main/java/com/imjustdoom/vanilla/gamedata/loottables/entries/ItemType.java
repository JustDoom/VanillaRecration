package com.imjustdoom.vanilla.gamedata.loottables.entries;

import com.imjustdoom.vanilla.gamedata.conditions.Condition;
import com.imjustdoom.vanilla.gamedata.loottables.LootTable;
import com.imjustdoom.vanilla.gamedata.loottables.LootTableEntryType;
import com.imjustdoom.vanilla.gamedata.loottables.LootTableFunction;
import com.imjustdoom.vanilla.gamedata.loottables.LootTableManager;
import net.minestom.server.item.Material;
import net.minestom.server.utils.NamespaceID;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * minecraft:item
 */
public class ItemType implements LootTableEntryType {

    @Override
    public LootTable.Entry create(LootTableManager lootTableManager, String name, List<Condition> conditions, List<LootTable.Entry> children, boolean expand, List<LootTableFunction> functions, int weight, int quality) {
        NamespaceID itemID = NamespaceID.from(name);
        final Map<NamespaceID, Material> idToMaterial = Arrays.stream(Material.values().toArray())
                .collect(Collectors.toMap(NamespaceID.from(name), mat -> mat));
        return new ItemEntry(this, idToMaterial.getOrDefault(itemID, Material.AIR), weight, quality, functions, conditions);
    }
}