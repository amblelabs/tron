package amble.tron.core.entities;

import amble.tron.core.TronEntities;
import amble.tron.core.TronAttachmentUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class LightcycleBatonThrownEntity extends PersistentProjectileEntity {
    private ItemStack batonStack;
    private boolean spawnedCycle;

    public LightcycleBatonThrownEntity(EntityType<LightcycleBatonThrownEntity> entityType, World world) {
        super(entityType, world);
        this.batonStack = new ItemStack(amble.tron.core.TronItems.LIGHTCYCLE_BATON);
    }

    public LightcycleBatonThrownEntity(World world, PlayerEntity player, ItemStack itemStack) {
        super(TronEntities.LIGHTCYCLE_BATON_THROWN, player, world);
        this.batonStack = itemStack.copy();
    }

    @Override
    public boolean hasNoGravity() {
        return false;
    }

    @Override
    public ItemStack asItemStack() {
        return new ItemStack(amble.tron.core.TronItems.LIGHTCYCLE_BATON);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (!this.getWorld().isClient && !this.spawnedCycle) {
            this.spawnedCycle = true;
            LightCycleEntity cycle = new LightCycleEntity(TronEntities.LIGHT_CYCLE, this.getWorld());
            Vec3d hitPos = blockHitResult.getPos().add(new Vec3d(blockHitResult.getSide().getOffsetX(), blockHitResult.getSide().getOffsetY(), blockHitResult.getSide().getOffsetZ()).multiply(0.5));
            cycle.refreshPositionAndAngles(hitPos.x, hitPos.y, hitPos.z, this.getYaw(), 0.0f);
            cycle.beginSpawnAnimation();
            cycle.setBeamActive(false);

            Entity owner = this.getOwner();
            if (owner instanceof PlayerEntity player) {
                Vector3f factionColor = TronAttachmentUtil.getFactionColor(player);
                if (factionColor != null) {
                    cycle.setColor(factionColor);
                }
            }

            this.getWorld().spawnEntity(cycle);
            this.playSound(SoundEvents.BLOCK_BEACON_POWER_SELECT, 0.8f, 1.2f);
            this.discard();
            return;
        }

        super.onBlockHit(blockHitResult);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Baton", 10)) {
            this.batonStack = ItemStack.fromNbt(nbt.getCompound("Baton"));
        }
        this.spawnedCycle = nbt.getBoolean("SpawnedCycle");
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.put("Baton", this.batonStack.writeNbt(new NbtCompound()));
        nbt.putBoolean("SpawnedCycle", this.spawnedCycle);
    }
}


