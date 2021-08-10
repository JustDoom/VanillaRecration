package com.imjustdoom.vanilla.gamedata.loottables;

/**
 * import net.minestom.server.gamedata.loottables.entries.*;
 */

import com.imjustdoom.vanilla.gamedata.conditions.AlternativesCondition;
import com.imjustdoom.vanilla.gamedata.conditions.InvertedCondition;
import com.imjustdoom.vanilla.gamedata.conditions.SurvivesExplosionCondition;
import com.imjustdoom.vanilla.gamedata.loottables.entries.*;
import com.imjustdoom.vanilla.gamedata.loottables.tabletypes.BlockType;
import net.minestom.server.utils.NamespaceID;

public final class VanillaLootTables {

    public static void register(LootTableManager tableManager) {
        tableManager.registerEntryType(NamespaceID.from("minecraft:alternatives"), new AlternativesType());
        tableManager.registerEntryType(NamespaceID.from("minecraft:loot_table"), new AnotherLootTableType());
        tableManager.registerEntryType(NamespaceID.from("minecraft:dynamic"), new DynamicType());
        tableManager.registerEntryType(NamespaceID.from("minecraft:item"), new ItemType());
        tableManager.registerEntryType(NamespaceID.from("minecraft:group"), new GroupType());
        tableManager.registerEntryType(NamespaceID.from("minecraft:sequence"), new SequenceType());
        tableManager.registerEntryType(NamespaceID.from("minecraft:tag"), new TagType());

        tableManager.registerConditionDeserializer(NamespaceID.from("minecraft:survives_explosion"), new SurvivesExplosionCondition.Deserializer());
        tableManager.registerConditionDeserializer(NamespaceID.from("minecraft:inverted"), new InvertedCondition.Deserializer(tableManager));
        tableManager.registerConditionDeserializer(NamespaceID.from("minecraft:alternative"), new AlternativesCondition.Deserializer(tableManager));
        tableManager.registerTableType(NamespaceID.from("minecraft:block"), new BlockType());
    }
}