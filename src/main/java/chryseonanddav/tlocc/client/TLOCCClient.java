package chryseonanddav.tlocc.client;

import chryseonanddav.tlocc.common.entity.remains.PlayerRemainsEntityRenderer;
import chryseonanddav.tlocc.registry.BlockRegistry;
import chryseonanddav.tlocc.registry.EntityTypeRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class TLOCCClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.AQUARIUM_GLASS_PANE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.IRON_SCAFFOLDING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BRAZIER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.SOUL_BRAZIER, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.MUG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.CHOCOLATE_EGG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.CHISELED_CHOCOLATE_EGG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.CHOCOLATE_RAT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.CHOCOLATE_CREWMATE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.CHOCOLATE_RAVEN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.CHOCOLATE_FROG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.CHOCOLATE_SQUIRREL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.ACACIA_CONNECTED_LOG_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BIRCH_CONNECTED_LOG_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.CRIMSON_CONNECTED_LOG_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.DARK_OAK_CONNECTED_LOG_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.JUNGLE_CONNECTED_LOG_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.MANGROVE_CONNECTED_LOG_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.OAK_CONNECTED_LOG_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.SPRUCE_CONNECTED_LOG_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.WARPED_CONNECTED_LOG_BLOCK, RenderLayer.getCutout());

        //EntityModelLayerRegistry.registerModelLayer(EntityTypeRegistry.PLAYER_REMAINS_MODEL_LAYER, PlayerRemainsEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityTypeRegistry.PLAYER_REMAINS_TYPE, PlayerRemainsEntityRenderer::new);

        //EntityRendererRegistry.register(EntityTypeRegistry.GLASS_ITEM_FRAME, GlassItemFrameEntityRenderer::new);

        //ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register((((spriteAtlasTexture, registry) -> {
        //    registry.register(Grimoire.id("particle/spear_pierce"));
        //})));


    }
}
