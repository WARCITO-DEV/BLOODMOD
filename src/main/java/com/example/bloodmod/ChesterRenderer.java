package com.example.bloodmod;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class ChesterRenderer extends MobRenderer<ChesterEntity, ChesterRenderState, ChesterModel> {

    private static final Identifier TEXTURA =
            Identifier.fromNamespaceAndPath("bloodmod", "textures/entity/chester.png");

    public ChesterRenderer(EntityRendererProvider.Context context) {
        super(context, new ChesterModel(context.bakeLayer(ChesterModel.LAYER)), 0.4f);
    }

    @Override
    public ChesterRenderState createRenderState() {
        return new ChesterRenderState();
    }

    @Override
    public void extractRenderState(ChesterEntity e, ChesterRenderState s, float partialTick) {
        super.extractRenderState(e, s, partialTick);
        s.walkAnim.copyFrom(e.walkAnim);
        s.correrAnim.copyFrom(e.correrAnim);
        s.expresionesAnim.copyFrom(e.expresionesAnim);
        s.abriendoseAnim.copyFrom(e.abriendoseAnim);
        s.abriendoseSentadoAnim.copyFrom(e.abriendoseSentadoAnim);
        s.cerrandoseAnim.copyFrom(e.cerrandoseAnim);
        s.cerrandoseSentadoAnim.copyFrom(e.cerrandoseSentadoAnim);
        s.cerradoAnim.copyFrom(e.cerradoAnim);
        s.abiertoAnim.copyFrom(e.abiertoAnim);
        s.sentadoAnim.copyFrom(e.sentadoAnim);
        s.olfatoAnim.copyFrom(e.olfatoAnim);
        s.muerteAnim.copyFrom(e.muerteAnim);
    }

    @Override
    public Identifier getTextureLocation(ChesterRenderState state) {
        return TEXTURA;
    }

    // Chester no se tumba de lado al morir: lo anima su propia animación MUERTE.
    // (Sin @Override a propósito: si el método no existe en esta versión, simplemente no hace nada.)
    protected float getFlipDegrees() {
        return 0.0F;
    }
}
