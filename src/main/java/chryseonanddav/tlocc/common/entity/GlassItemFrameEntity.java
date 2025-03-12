package chryseonanddav.tlocc.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class GlassItemFrameEntity extends ItemFrameEntity {
    public GlassItemFrameEntity(EntityType<? extends ItemFrameEntity> entityType, World world) {
        super(entityType, world);
    }

    //public static GlassItemFrameEntity create(World world, BlockPos blockPos, Direction direction) {
    //    GlassItemFrameEntity entity =  new GlassItemFrameEntity(EntityTypeRegistry.GLASS_ITEM_FRAME, world);
    //    entity.attachmentPos = blockPos;
    //    entity.setFacing(direction);
//
    //    return entity;
    //}

    @Override
    public boolean isInvisible() {
        return !getHeldItemStack().getItem().equals(Items.AIR);
    }
}
