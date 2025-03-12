package chryseonanddav.tlocc;


import chryseonanddav.tlocc.registry.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.util.Identifier;

public class TLOCC implements ModInitializer {

    public static final String MOD_ID = "tlocc";

    @Override
    public void onInitialize() {

        BlockRegistry.register();
        EntityTypeRegistry.register();
        GrimGameruleRegistry.register();

//        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
//            PersistentData.init(PersistentData.getServerState(server));
//        });
    }

    public static Identifier id(String name) {
        return Identifier.of(MOD_ID, name);
    }

}
