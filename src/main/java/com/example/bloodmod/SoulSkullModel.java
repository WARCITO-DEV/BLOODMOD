package com.example.bloodmod;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.Identifier;

/** Modelo de la Soul Skull (generado desde SOULSKULL.bbmodel) con sus 3 animaciones. */
public class SoulSkullModel extends EntityModel<SoulSkullRenderState> {

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath("bloodmod", "soul_skull"), "main");

    private final KeyframeAnimation idle;
    private final KeyframeAnimation atk;
    private final KeyframeAnimation follow;

    public SoulSkullModel(ModelPart root) {
        super(root);
        this.idle = SOULSKULLAnimation.IDLE.bake(root);
        this.atk = SOULSKULLAnimation.ATK.bake(root);
        this.follow = SOULSKULLAnimation.FOLLOW.bake(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // El modelo original mira hacia +Z (la cara está en ese lado), pero Minecraft
        // espera que mire hacia -Z: por eso se gira 180 grados. Se baja 8 px para que
        // la calavera quede apoyada en la base de la hitbox.
        PartDefinition soul = root.addOrReplaceChild("soul", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, (float) Math.PI, 0.0F));

        PartDefinition bone10 = soul.addOrReplaceChild("bone10",
                CubeListBuilder.create()
                        .texOffs(0, 9).addBox(-3.55F, -1.2F, -3.3F, 7F, 5F, 6F),
                PartPose.offset(0.05F, 10.2F, 0.3F));

        bone10.addOrReplaceChild("JAW",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.05F, 0F, -0.3F, 8F, 2F, 7F)
                        .texOffs(4, 20).addBox(-4.05F, -2F, 5.7F, 1F, 2F, 1F)
                        .texOffs(0, 20).addBox(2.95F, -2F, 5.7F, 1F, 2F, 1F)
                        .texOffs(8, 20).addBox(2.95F, -1F, 3.7F, 1F, 1F, 1F)
                        .texOffs(12, 20).addBox(-4.05F, -1F, 3.7F, 1F, 1F, 1F)
                        .texOffs(16, 20).addBox(-2.05F, -1F, 5.7F, 1F, 1F, 1F)
                        .texOffs(20, 20).addBox(0.95F, -1F, 5.7F, 1F, 1F, 1F),
                PartPose.offset(0F, 3.8F, -3F));

        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void setupAnim(SoulSkullRenderState state) {
        super.setupAnim(state);
        this.idle.apply(state.idleAnim, state.ageInTicks);
        this.atk.apply(state.atkAnim, state.ageInTicks);
        this.follow.apply(state.followAnim, state.ageInTicks);
    }
}
