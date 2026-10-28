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

/** Modelo de Chester (generado desde WYT_chester.bbmodel) con todas sus animaciones. */
public class ChesterModel extends EntityModel<ChesterRenderState> {

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath("bloodmod", "chester"), "main");

    private final KeyframeAnimation walk, correr, expresiones, abriendose, abriendoseSentado,
            cerrandose, cerrandoseSentado, cerrado, abierto, sentado, olfato, muerte;

    public ChesterModel(ModelPart root) {
        super(root);
        this.walk = ChesterAnimation.WALK.bake(root);
        this.correr = ChesterAnimation.CORRER.bake(root);
        this.expresiones = ChesterAnimation.EXPRESIONES.bake(root);
        this.abriendose = ChesterAnimation.ABRIENDOSE.bake(root);
        this.abriendoseSentado = ChesterAnimation.ABRIENDOSE_SENTADO.bake(root);
        this.cerrandose = ChesterAnimation.CERRANDOSE.bake(root);
        this.cerrandoseSentado = ChesterAnimation.CERRANDOSE_SENTADO.bake(root);
        this.cerrado = ChesterAnimation.ESTATICO_CERRADO.bake(root);
        this.abierto = ChesterAnimation.ESTATICO_ABIERTO.bake(root);
        this.sentado = ChesterAnimation.SENTADO.bake(root);
        this.olfato = ChesterAnimation.OLFATO.bake(root);
        this.muerte = ChesterAnimation.MUERTE.bake(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // El modelo original tiene el frente (el cierre) hacia +X; Minecraft espera -Z,
        // así que se gira 90 grados.
        PartDefinition giro = root.addOrReplaceChild("giro", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, (float) Math.PI / 2.0F, 0.0F));

        PartDefinition b_chester = giro.addOrReplaceChild("chester",
                CubeListBuilder.create()
                .texOffs(0, 0).addBox(-0.1F, -0.3F, -6F, 10.3F, 6.45F, 11F),
                PartPose.offset(-4.8F, 15.9F, 0.3F));

        PartDefinition b_lengua = b_chester.addOrReplaceChild("lengua",
                CubeListBuilder.create()
                .texOffs(5, 39).addBox(0.5F, -0.8F, -1.4F, 8.6F, 1.5F, 3.3F),
                PartPose.offset(0.5F, -0.6F, -0.6F));

        PartDefinition b_pata4 = b_chester.addOrReplaceChild("pata4",
                CubeListBuilder.create()
                .texOffs(0, 0).addBox(-1F, 0F, 0F, 2F, 3F, 2F),
                PartPose.offset(0.9F, 5.15F, 5F));

        PartDefinition b_pata2 = b_chester.addOrReplaceChild("pata2",
                CubeListBuilder.create()
                .texOffs(0, 0).addBox(-1F, 0F, 0F, 2F, 3F, 2F),
                PartPose.offset(9.2F, 5.15F, 5F));

        PartDefinition b_pata = b_chester.addOrReplaceChild("pata",
                CubeListBuilder.create()
                .texOffs(0, 0).addBox(-1F, 0F, -2F, 2F, 3F, 2F),
                PartPose.offset(0.9F, 5.15F, -6F));

        PartDefinition b_pata3 = b_chester.addOrReplaceChild("pata3",
                CubeListBuilder.create()
                .texOffs(0, 0).addBox(-1F, -0.1F, -2F, 2F, 3F, 2F),
                PartPose.offset(9.2F, 5.25F, -6F));

        PartDefinition b_dientes2 = b_chester.addOrReplaceChild("dientes2",
                CubeListBuilder.create()
                .texOffs(24, 33).addBox(0.15F, -0.65F, 4.6F, 1F, 1.4F, 0F)
                .texOffs(26, 33).addBox(2.15F, -0.45F, 4.6F, 1F, 1.2F, 0F)
                .texOffs(28, 33).addBox(6.15F, -0.25F, -4.4F, 1F, 1F, 0F)
                .texOffs(30, 33).addBox(4.15F, -0.45F, -4.4F, 1F, 1.2F, 0F)
                .texOffs(30, 33).addBox(4.15F, -0.45F, 4.6F, 1F, 1.2F, 0F)
                .texOffs(30, 33).addBox(6.55F, 0.15F, 4.6F, 1F, 1.2F, 0F)
                .texOffs(18, 32).addBox(2.15F, -1.25F, -4.4F, 1F, 2F, 0F)
                .texOffs(32, 33).addBox(0.15F, -0.65F, -4.4F, 1F, 1.4F, 0F),
                PartPose.offsetAndRotation(9.55F, -0.95F, -0.3F, (float) Math.toRadians(180), 0.0F, (float) Math.toRadians(180)));

        b_dientes2.addOrReplaceChild("c1",
                CubeListBuilder.create()
                .texOffs(12, 34).addBox(-0.45F, -0.45F, 0F, 1F, 1.2F, 0F),
                PartPose.offsetAndRotation(0F, 0.5F, -2.1F, 0.0F, (float) Math.toRadians(90), 0.0F));

        b_dientes2.addOrReplaceChild("c2",
                CubeListBuilder.create()
                .texOffs(12, 34).addBox(-0.45F, -0.45F, 0F, 1F, 1.2F, 0F),
                PartPose.offsetAndRotation(0F, 0.1F, -3.4F, 0.0F, (float) Math.toRadians(90), 0.0F));

        b_dientes2.addOrReplaceChild("c3",
                CubeListBuilder.create()
                .texOffs(12, 34).addBox(-0.45F, -0.45F, 0F, 1F, 1.2F, 0F),
                PartPose.offsetAndRotation(0F, 0.5F, 2.3F, 0.0F, (float) Math.toRadians(90), 0.0F));

        b_dientes2.addOrReplaceChild("c4",
                CubeListBuilder.create()
                .texOffs(12, 34).addBox(-0.45F, -0.45F, 0F, 1F, 1.2F, 0F),
                PartPose.offsetAndRotation(0F, 0F, 0.9F, 0.0F, (float) Math.toRadians(90), 0.0F));

        b_dientes2.addOrReplaceChild("c5",
                CubeListBuilder.create()
                .texOffs(12, 34).addBox(-0.45F, -0.45F, 0F, 1F, 1.2F, 0F),
                PartPose.offsetAndRotation(0F, 0F, 3.8F, 0.0F, (float) Math.toRadians(90), 0.0F));

        b_dientes2.addOrReplaceChild("c6",
                CubeListBuilder.create()
                .texOffs(14, 34).addBox(-0.45F, -0.45F, 0F, 1F, 1.2F, 0F),
                PartPose.offsetAndRotation(0F, 0F, -0.6F, 0.0F, (float) Math.toRadians(90), 0.0F));

        PartDefinition b_tapa = b_chester.addOrReplaceChild("tapa",
                CubeListBuilder.create()
                .texOffs(0, 32).addBox(5.1F, -5.5F, 5.1F, 1F, 2F, 1F)
                .texOffs(4, 32).addBox(5.1F, -5.5F, -7.1F, 1F, 2F, 1F)
                .texOffs(0, 17).addBox(-0.3F, -4.6F, -6.1F, 10.7F, 4.275F, 11.2F),
                PartPose.offset(0.2F, 0F, 0F));

        PartDefinition b_SEGURO = b_tapa.addOrReplaceChild("SEGURO",
                CubeListBuilder.create()
                .texOffs(8, 32).addBox(-0.5F, -1F, -0.5F, 1F, 2F, 1F),
                PartPose.offset(10.9F, -0.3F, -0.5F));

        PartDefinition b_dientes = b_tapa.addOrReplaceChild("dientes",
                CubeListBuilder.create()
                .texOffs(20, 32).addBox(0.05F, -0.65F, 4.5F, 1.3F, 1.4F, 0F)
                .texOffs(12, 32).addBox(2.35F, -1.25F, 4.5F, 1F, 2F, 0F)
                .texOffs(22, 32).addBox(3.95F, -0.45F, 4.5F, 1.4F, 1.2F, 0F)
                .texOffs(24, 32).addBox(5.95F, -0.25F, 4.5F, 1.4F, 1F, 0F)
                .texOffs(26, 32).addBox(5.95F, -0.25F, -4.7F, 1.4F, 1F, 0F)
                .texOffs(28, 32).addBox(3.95F, -0.45F, -4.7F, 1.4F, 1.2F, 0F)
                .texOffs(14, 32).addBox(1.95F, -1.25F, -4.7F, 1.4F, 2F, 0F)
                .texOffs(30, 32).addBox(-0.05F, -0.65F, -4.7F, 1.4F, 1.4F, 0F),
                PartPose.offsetAndRotation(9.55F, 0.45F, -0.3F, (float) Math.toRadians(180), (float) Math.toRadians(-180), 0.0F));

        b_dientes.addOrReplaceChild("c7",
                CubeListBuilder.create()
                .texOffs(32, 32).addBox(-4.55F, -0.35F, -0.1F, 1.1F, 1.1F, 0F),
                PartPose.offsetAndRotation(-0.4F, 0F, 0.3F, 0.0F, (float) Math.toRadians(90), 0.0F));

        b_dientes.addOrReplaceChild("c8",
                CubeListBuilder.create()
                .texOffs(20, 33).addBox(-2.75F, -0.45F, -0.1F, 1F, 1.2F, 0F),
                PartPose.offsetAndRotation(-0.4F, 0F, 0.5F, 0.0F, (float) Math.toRadians(90), 0.0F));

        b_dientes.addOrReplaceChild("c9",
                CubeListBuilder.create()
                .texOffs(20, 33).addBox(-2.75F, -0.45F, -0.1F, 1F, 1.2F, 0F),
                PartPose.offsetAndRotation(-0.4F, 0F, -5.1F, 0.0F, (float) Math.toRadians(90), 0.0F));

        b_dientes.addOrReplaceChild("c10",
                CubeListBuilder.create()
                .texOffs(20, 33).addBox(-2.75F, -0.45F, -0.1F, 1F, 1.2F, 0F),
                PartPose.offsetAndRotation(-0.4F, 0F, -6.4F, 0.0F, (float) Math.toRadians(90), 0.0F));

        b_dientes.addOrReplaceChild("c11",
                CubeListBuilder.create()
                .texOffs(22, 33).addBox(-0.65F, -1.05F, -0.1F, 1.8F, 1.8F, 0F),
                PartPose.offsetAndRotation(-0.4F, 0.3F, 1.1F, 0.0F, (float) Math.toRadians(90), 0.0F));

        b_dientes.addOrReplaceChild("c12",
                CubeListBuilder.create()
                .texOffs(16, 32).addBox(-0.85F, -1.55F, -0.1F, 1.7F, 2.3F, 0F),
                PartPose.offsetAndRotation(-0.4F, 0.9F, -1.2F, 0.0F, (float) Math.toRadians(90), 0.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(ChesterRenderState state) {
        super.setupAnim(state);
        this.walk.apply(state.walkAnim, state.ageInTicks);
        this.correr.apply(state.correrAnim, state.ageInTicks);
        this.expresiones.apply(state.expresionesAnim, state.ageInTicks);
        this.abriendose.apply(state.abriendoseAnim, state.ageInTicks);
        this.abriendoseSentado.apply(state.abriendoseSentadoAnim, state.ageInTicks);
        this.cerrandose.apply(state.cerrandoseAnim, state.ageInTicks);
        this.cerrandoseSentado.apply(state.cerrandoseSentadoAnim, state.ageInTicks);
        this.cerrado.apply(state.cerradoAnim, state.ageInTicks);
        this.abierto.apply(state.abiertoAnim, state.ageInTicks);
        this.sentado.apply(state.sentadoAnim, state.ageInTicks);
        this.olfato.apply(state.olfatoAnim, state.ageInTicks);
        this.muerte.apply(state.muerteAnim, state.ageInTicks);
    }
}
