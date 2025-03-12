package chryseonanddav.tlocc.registry;

import chryseonanddav.tlocc.TLOCC;
import chryseonanddav.tlocc.common.ModFoodComponents;
import chryseonanddav.tlocc.common.block.*;
import chryseonanddav.tlocc.common.items.ContractItem;
import chryseonanddav.tlocc.common.items.TeleporterItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.LinkedHashMap;
import java.util.Map;

public class BlockRegistry {
//    private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

    public static final Block MUG = registerBlock("mug", new MugBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));
    public static final Block AQUARIUM_GLASS_PANE = registerBlock("aquarium_glass_pane", new AquariumGlassPaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block IRON_SCAFFOLDING = registerBlock("iron_scaffolding", new IronScaffoldingBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).requiresTool().strength(3.0f, 2.0f).sounds(BlockSoundGroup.LANTERN)));
    public static final Block BRAZIER = registerBlock("brazier", new BrazierV2Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).requiresTool().strength(3.0f, 2.0f).luminance(state -> 15).sounds(BlockSoundGroup.LANTERN)));
    public static final Block SOUL_BRAZIER = registerBlock("soul_brazier", new BrazierV2Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).requiresTool().strength(3.0f, 2.0f).luminance(state -> 10).sounds(BlockSoundGroup.LANTERN)));

    // Fixed missing parentheses
    public static final Block CHOCOLATE_EGG = registerEdibleBlock("chocolate_egg", new ChocolateEggBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(0.1F).nonOpaque()));
    public static final Block CHISELED_CHOCOLATE_EGG = registerEdibleBlock("chiseled_chocolate_egg", new ChiseledChocolateEggBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(0.1F).nonOpaque()));
    public static final Block CHOCOLATE_RAVEN = registerEdibleBlock("chocolate_raven", new ChocolateRavenBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(0.1F).nonOpaque()));
    public static final Block CHOCOLATE_RAT = registerEdibleBlock("chocolate_rat", new ChocolateRatBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(0.1F).nonOpaque()));
    public static final Block CHOCOLATE_SQUIRREL = registerEdibleBlock("chocolate_squirrel", new ChocolateSquirrelBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(0.1F).nonOpaque()));
    public static final Block CHOCOLATE_FROG = registerEdibleBlock("chocolate_frog", new ChocolateFrogBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(0.1F).nonOpaque()));
    public static final Block CHOCOLATE_CREWMATE = registerEdibleBlock("chocolate_crewmate", new ChocolateCrewmateBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(0.1F).nonOpaque()));

    public static final Block ACACIA_CONNECTED_LOG_BLOCK = registerBlock("acacia_connected_log", new ConnectedLogsBlock(AbstractBlock.Settings.copy(Blocks.ACACIA_PLANKS)));
    public static final Block BIRCH_CONNECTED_LOG_BLOCK = registerBlock("birch_connected_log", new ConnectedLogsBlock(AbstractBlock.Settings.copy(Blocks.BIRCH_PLANKS)));
    public static final Block CRIMSON_CONNECTED_LOG_BLOCK = registerBlock("crimson_connected_log", new ConnectedLogsBlock(AbstractBlock.Settings.copy(Blocks.CRIMSON_PLANKS)));
    public static final Block DARK_OAK_CONNECTED_LOG_BLOCK = registerBlock("dark_oak_connected_log", new ConnectedLogsBlock(AbstractBlock.Settings.copy(Blocks.DARK_OAK_PLANKS)));
    public static final Block JUNGLE_CONNECTED_LOG_BLOCK = registerBlock("jungle_connected_log", new ConnectedLogsBlock(AbstractBlock.Settings.copy(Blocks.JUNGLE_PLANKS)));
    public static final Block MANGROVE_CONNECTED_LOG_BLOCK = registerBlock("mangrove_connected_log", new ConnectedLogsBlock(AbstractBlock.Settings.copy(Blocks.MANGROVE_PLANKS)));
    public static final Block OAK_CONNECTED_LOG_BLOCK = registerBlock("oak_connected_log", new ConnectedLogsBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));
    public static final Block SPRUCE_CONNECTED_LOG_BLOCK = registerBlock("spruce_connected_log", new ConnectedLogsBlock(AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS)));
    public static final Block WARPED_CONNECTED_LOG_BLOCK = registerBlock("warped_connected_log", new ConnectedLogsBlock(AbstractBlock.Settings.copy(Blocks.WARPED_PLANKS)));


//    public static final Item CONTRACT = createItem("contract", new ContractItem(new FabricItemSettings().fireproof().rarity(Rarity.EPIC).maxCount(1)));
//    public static final Item GRIMOIRE = createItem("grimoire", new Item(new FabricItemSettings().fireproof().rarity(Rarity.EPIC).maxCount(1)));
//    public static final Item TELEPORTER = createItem("teleporter", new TeleporterItem(new FabricItemSettings().fireproof().rarity(Rarity.EPIC).maxCount(1)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, TLOCC.id(name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(
                Registries.ITEM,
                TLOCC.id(name),
                new BlockItem(block, new FabricItemSettings())
        );
    }

    private static Block registerEdibleBlock(String name, Block block) {
        registerEdibleBlockItem(name, block);
        return Registry.register(Registries.BLOCK, TLOCC.id(name), block);
    }

    private static Item registerEdibleBlockItem(String name, Block block) {
        Item item = Registry.register(
                Registries.ITEM,
                TLOCC.id(name),
                new BlockItem(block, new FabricItemSettings().food(ModFoodComponents.CHOCOLATE))
        );

        return item;
    }

//    private static <T extends Item> T createItem(String name, T item) {
//        ITEMS.put(item, new Identifier(TLOCC.MOD_ID, name));
//        return item;
//    }

    public static void register() {
//        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
//            entries.add(CONTRACT);
//            entries.add(TELEPORTER);
//            entries.add(GRIMOIRE);
//        });
        // Add building blocks
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.add(MUG);
            entries.add(AQUARIUM_GLASS_PANE);
            entries.add(IRON_SCAFFOLDING);
            entries.add(BRAZIER);
            entries.add(SOUL_BRAZIER);
            entries.add(ACACIA_CONNECTED_LOG_BLOCK);
            entries.add(BIRCH_CONNECTED_LOG_BLOCK);
            entries.add(CRIMSON_CONNECTED_LOG_BLOCK);
            entries.add(DARK_OAK_CONNECTED_LOG_BLOCK);
            entries.add(JUNGLE_CONNECTED_LOG_BLOCK);
            entries.add(MANGROVE_CONNECTED_LOG_BLOCK);
            entries.add(OAK_CONNECTED_LOG_BLOCK);
            entries.add(SPRUCE_CONNECTED_LOG_BLOCK);
            entries.add(WARPED_CONNECTED_LOG_BLOCK);
        });

        // Add edible blocks to the Food & Drink tab
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.add(CHOCOLATE_EGG);
            entries.add(CHISELED_CHOCOLATE_EGG);
            entries.add(CHOCOLATE_RAVEN);
            entries.add(CHOCOLATE_RAT);
            entries.add(CHOCOLATE_SQUIRREL);
            entries.add(CHOCOLATE_FROG);
            entries.add(CHOCOLATE_CREWMATE);
        });
    }
}
