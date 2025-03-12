package chryseonanddav.tlocc.mixin;

import chryseonanddav.tlocc.common.entity.remains.PlayerRemainsEntity;
import chryseonanddav.tlocc.registry.BlockRegistry;
import chryseonanddav.tlocc.registry.EntityTypeRegistry;
import chryseonanddav.tlocc.registry.GrimGameruleRegistry;
import chryseonanddav.tlocc.util.TrinketsHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow protected abstract void consumeItem();


    @Inject(method = "applyClimbingSpeed", at = @At("TAIL"), cancellable = true)
    private void grimoire$applyClimbingSpeed(Vec3d motion, CallbackInfoReturnable<Vec3d> cir) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if(entity.isClimbing()) {
            entity.onLanding();
            float f = 0.15f;
            double d = MathHelper.clamp(motion.x, (double)-0.15f, (double)0.15f);
            double e = MathHelper.clamp(motion.z, (double)-0.15f, (double)0.15f);
            double g = Math.max(motion.y, (double)-0.15f);
            if (g < 0.0 && !entity.getBlockStateAtPos().isOf(BlockRegistry.IRON_SCAFFOLDING) && entity.isHoldingOntoLadder() && entity instanceof PlayerEntity) {
                g = 0.0;
            }
            motion = new Vec3d(d, g, e);
        }

        cir.setReturnValue(motion);
    }


    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;onDeath(Lnet/minecraft/entity/damage/DamageSource;)V"))
    private void grimoire$onDeath(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(((LivingEntity)(Object)this).getWorld().getGameRules().getBoolean(GrimGameruleRegistry.CREATE_GRAVE) && (Object)this instanceof PlayerEntity player) {
            if(player.getWorld().getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) return;

            PlayerRemainsEntity playerRemainsEntity = new PlayerRemainsEntity(EntityTypeRegistry.PLAYER_REMAINS_TYPE, player.getWorld());
            playerRemainsEntity.setPosition(player.getPos());

            playerRemainsEntity.resetInventory();

            for(int i = 0;i<player.getInventory().main.size();i++) {
                playerRemainsEntity.addInventoryStack(player.getInventory().main.get(i).copy());
            }
            for(int i = 0;i<player.getInventory().offHand.size();i++) {
                playerRemainsEntity.addInventoryStack(player.getInventory().offHand.get(i).copy());
            }

            for(int i = 0;i<player.getInventory().armor.size();i++) {
                playerRemainsEntity.addInventoryStack(player.getInventory().armor.get(i).copy());
            }

            if(((LivingEntity)(Object)this).getWorld().getGameRules().getBoolean(GrimGameruleRegistry.SAVE_TRINKETS)) {
                if(FabricLoader.getInstance().isModLoaded("trinkets")) {
                    try{
                        TrinketsHelper.findAllEquippedBy(player).forEach(playerRemainsEntity::addInventoryStack);
                        TrinketsHelper.clearAllEquippedTrinkets(player);
                    } catch (Exception ingore) {}
                }
            }

            playerRemainsEntity.setCustomName(player.getDisplayName());
            playerRemainsEntity.setCustomNameVisible(true);

            player.getWorld().spawnEntity(playerRemainsEntity);
            player.getInventory().clear();
        }
    }

    @Inject(method = "onDeath", at = @At("TAIL"))
    public void grimoire$deathCompass(DamageSource damageSource, CallbackInfo ci) {
        if((Object)this instanceof PlayerEntity player) {
            ItemStack stack = new ItemStack(Items.COMPASS);
            NbtCompound nbt = new NbtCompound();
            stack.writeNbt(writeCompassNbt(nbt, player.getBlockPos()));
            player.giveItemStack(stack);
        }
    }

    private NbtCompound writeCompassNbt(NbtCompound nbt, BlockPos pos) {
        nbt.put("LodestonePos", NbtHelper.fromBlockPos(pos));
        nbt.putBoolean("LodestoneTracked", true);
        return nbt;
    }
}
