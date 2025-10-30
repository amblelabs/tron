package amble.tron.core;

import amble.tron.core.entities.IdentityDiscThrownEntity;
import amble.tron.core.entities.LightCycleEntity;
import amble.tron.core.entities.lighttrail.LightTrailSegmentEntity;
import dev.amble.lib.container.impl.EntityContainer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;

public class TronEntities implements EntityContainer {

    public static final EntityType<IdentityDiscThrownEntity> IDENTITY_DISC = FabricEntityTypeBuilder
            .<IdentityDiscThrownEntity>create( SpawnGroup.MISC, IdentityDiscThrownEntity::new)
                    .dimensions(new EntityDimensions(0.5f, 0.5f, false)).fireImmune().build();

    public static final EntityType<LightCycleEntity> LIGHT_CYCLE = FabricEntityTypeBuilder
            .create(SpawnGroup.MISC, LightCycleEntity::new)
            .dimensions(new EntityDimensions(1, 1, false)).fireImmune().build();

    public static final EntityType<LightTrailSegmentEntity> LIGHT_TRAIL_SEGMENT = FabricEntityTypeBuilder
            .<LightTrailSegmentEntity>create(SpawnGroup.MISC, LightTrailSegmentEntity::new)
            .dimensions(new EntityDimensions(1, 1, false)).fireImmune().build();
}
