package amble.tron.client;

import amble.tron.client.render.IdentityDiscThrownItemRenderer;
import amble.tron.core.TronEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class TronClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(TronEntities.IDENTITY_DISC, IdentityDiscThrownItemRenderer::new);
    }
}
