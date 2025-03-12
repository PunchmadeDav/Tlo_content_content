package chryseonanddav.tlocc.registry;

import chryseonanddav.tlocc.TLOCC;
import chryseonanddav.tlocc.common.items.ContractItem;
import chryseonanddav.tlocc.common.items.TeleporterItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.LinkedHashMap;
import java.util.Map;

public class ItemRegistry {
    private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();
    public static final Item CONTRACT = createItem("contract", new ContractItem(new FabricItemSettings().fireproof().rarity(Rarity.UNCOMMON).maxCount(1)));
    public static final Item GRIMOIRE = createItem("grimoire", new Item(new FabricItemSettings().fireproof().rarity(Rarity.UNCOMMON).maxCount(1)));
    public static final Item TELLEPORTER = createItem("teleporter", new TeleporterItem(new FabricItemSettings().fireproof().rarity(Rarity.UNCOMMON).maxCount(1)));

    private static <T extends Item> T createItem(String name, T item) {
        ITEMS.put(item, new Identifier(TLOCC.MOD_ID, name));
        return item;
    }

    public static void register() {
        // Add building blocks
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(CONTRACT);
            entries.add(GRIMOIRE);
            entries.add(TELLEPORTER);
        });
    }
}
