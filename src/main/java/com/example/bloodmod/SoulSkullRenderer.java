package com.example.bloodmod;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class SoulSkullRenderer extends MobRenderer<SoulSkullEntity, SoulSkullRenderState, SoulSkullModel> {

    private static final Identifier TEXTURA =
            Identifier.fromNamespaceAndPath("bloodmod", "textures/entity/soul_skull.png");

    public SoulSkullRenderer(EntityRendererProvider.Context context) {
        super(context, new SoulSkullModel(context.bakeLayer(SoulSkullModel.LAYER)), 0.2f);
    }

    @Override
    public SoulSkullRenderState createRenderState() {
        return new SoulSkullRenderState();
    }

    @Override
    public void extractRenderState(SoulSkullEntity entity, SoulSkullRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.idleAnim.copyFrom(entity.idleAnim);
        state.atkAnim.copyFrom(entity.atkAnim);
        state.followAnim.copyFrom(entity.followAnim);
    }

    @Override
    public Identifier getTextureLocation(SoulSkullRenderState state) {
        return TEXTURA;
    }
}
