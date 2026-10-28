package com.example.bloodmod;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Animaciones de Chester, convertidas desde chester_animation.json (GeckoLib/Bedrock).
 * CERRANDOSE y CERRANDOSE_SENTADO son "abriendose" y "abriendose_sentado" reproducidas al revés.
 */
public class ChesterAnimation {

    public static final AnimationDefinition WALK = AnimationDefinition.Builder.withLength(0.5F).looping()
        .addAnimation("tapa", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.125F, KeyframeAnimations.degreeVec(0F, 0F, -5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0F, 0F, -2.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.375F, KeyframeAnimations.degreeVec(0F, 0F, -5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("chester", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.125F, KeyframeAnimations.posVec(0F, 0.6F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.posVec(0F, -0.2F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.375F, KeyframeAnimations.posVec(0F, 0.5F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("chester", new AnimationChannel(AnimationChannel.Targets.SCALE,
            new Keyframe(0F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.125F, KeyframeAnimations.scaleVec(1F, 1.1F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.375F, KeyframeAnimations.scaleVec(1F, 1.2F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.125F, KeyframeAnimations.degreeVec(0F, 0F, 50F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.375F, KeyframeAnimations.degreeVec(0F, 0F, -30F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.125F, KeyframeAnimations.degreeVec(0F, 0F, 32.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.375F, KeyframeAnimations.degreeVec(0F, 0F, -27.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.125F, KeyframeAnimations.degreeVec(0F, 0F, -22.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.375F, KeyframeAnimations.degreeVec(0F, 0F, 40F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.125F, KeyframeAnimations.degreeVec(0F, 0F, -27.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.375F, KeyframeAnimations.degreeVec(0F, 0F, 37.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("lengua", new AnimationChannel(AnimationChannel.Targets.SCALE,
            new Keyframe(0F, KeyframeAnimations.scaleVec(0.7F, 1F, 1F), AnimationChannel.Interpolations.LINEAR)))
        .build();

    public static final AnimationDefinition CORRER = AnimationDefinition.Builder.withLength(0.3623F).looping()
        .addAnimation("tapa", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, -32.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1812F, KeyframeAnimations.degreeVec(0F, 0F, -62.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.3623F, KeyframeAnimations.degreeVec(0F, 0F, -32.5F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("chester", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0906F, KeyframeAnimations.posVec(0F, 1.6F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1812F, KeyframeAnimations.posVec(0F, 1.8F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2717F, KeyframeAnimations.posVec(0F, 1.5F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.3623F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("chester", new AnimationChannel(AnimationChannel.Targets.SCALE,
            new Keyframe(0F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0906F, KeyframeAnimations.scaleVec(1F, 1.17F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2717F, KeyframeAnimations.scaleVec(1F, 1.2F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.3623F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0906F, KeyframeAnimations.degreeVec(0F, 0F, 50F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1812F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2717F, KeyframeAnimations.degreeVec(0F, 0F, -30F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.3623F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0906F, KeyframeAnimations.degreeVec(0F, 0F, 32.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1812F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2717F, KeyframeAnimations.degreeVec(0F, 0F, -27.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.3623F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0906F, KeyframeAnimations.degreeVec(0F, 0F, -22.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1812F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2717F, KeyframeAnimations.degreeVec(0F, 0F, 40F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.3623F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0906F, KeyframeAnimations.degreeVec(0F, 0F, -27.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1812F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2717F, KeyframeAnimations.degreeVec(0F, 0F, 37.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.3623F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("lengua", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1812F, KeyframeAnimations.degreeVec(0F, -19.1667F, -5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.3623F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("lengua", new AnimationChannel(AnimationChannel.Targets.SCALE,
            new Keyframe(0F, KeyframeAnimations.scaleVec(0.7F, 1F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.3623F, KeyframeAnimations.scaleVec(0.7F, 1F, 1F), AnimationChannel.Interpolations.LINEAR)))
        .build();

    public static final AnimationDefinition EXPRESIONES = AnimationDefinition.Builder.withLength(1.0013F)
        .addAnimation("tapa", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0323F, KeyframeAnimations.degreeVec(0F, 0F, -2.0536F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0646F, KeyframeAnimations.degreeVec(0F, 0F, -9.4522F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0969F, KeyframeAnimations.degreeVec(0F, 0F, -20.5478F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1292F, KeyframeAnimations.degreeVec(0F, 0F, -27.9464F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1615F, KeyframeAnimations.degreeVec(0F, 0F, -30F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1938F, KeyframeAnimations.degreeVec(0F, 0F, -30F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5814F, KeyframeAnimations.degreeVec(0F, 0F, -30F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.6137F, KeyframeAnimations.degreeVec(0F, 0F, -28.203F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.646F, KeyframeAnimations.degreeVec(0F, 0F, -22.8469F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.7106F, KeyframeAnimations.degreeVec(0F, 0F, -7.1531F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.7429F, KeyframeAnimations.degreeVec(0F, 0F, -1.797F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.7752F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("chester", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0323F, KeyframeAnimations.posVec(0F, 0.753F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0646F, KeyframeAnimations.posVec(0F, 3.4658F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0969F, KeyframeAnimations.posVec(0F, 7.5342F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1292F, KeyframeAnimations.posVec(0F, 10.247F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1615F, KeyframeAnimations.posVec(0F, 11F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1938F, KeyframeAnimations.posVec(0F, 11F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5814F, KeyframeAnimations.posVec(0F, 11F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.6137F, KeyframeAnimations.posVec(0F, 10.3411F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.646F, KeyframeAnimations.posVec(0F, 8.3772F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.7106F, KeyframeAnimations.posVec(0F, 2.6228F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.7429F, KeyframeAnimations.posVec(0F, 0.6589F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.7752F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("chester", new AnimationChannel(AnimationChannel.Targets.SCALE,
            new Keyframe(0F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.1615F, KeyframeAnimations.scaleVec(1F, 1.4F, 1F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.5814F, KeyframeAnimations.scaleVec(1F, 1.4F, 1F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.7752F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("pata", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0323F, KeyframeAnimations.degreeVec(0F, 0F, 3.0804F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0646F, KeyframeAnimations.degreeVec(0F, 0F, 14.1784F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0969F, KeyframeAnimations.degreeVec(0F, 0F, 30.8216F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1292F, KeyframeAnimations.degreeVec(0F, 0F, 41.9196F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1615F, KeyframeAnimations.degreeVec(0F, 0F, 45F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1938F, KeyframeAnimations.degreeVec(0F, 0F, 39.2072F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2261F, KeyframeAnimations.degreeVec(0F, 0F, 21.9409F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2907F, KeyframeAnimations.degreeVec(0F, 0F, -28.6509F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.323F, KeyframeAnimations.degreeVec(0F, 0F, -45.9172F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.3553F, KeyframeAnimations.degreeVec(0F, 0F, -51.71F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.3876F, KeyframeAnimations.degreeVec(0F, 0F, -46.4669F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4199F, KeyframeAnimations.degreeVec(0F, 0F, -32.6129F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4522F, KeyframeAnimations.degreeVec(0F, 0F, -13.3469F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4845F, KeyframeAnimations.degreeVec(0F, 0F, 7.7669F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5168F, KeyframeAnimations.degreeVec(0F, 0F, 27.0329F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5491F, KeyframeAnimations.degreeVec(0F, 0F, 40.8869F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5814F, KeyframeAnimations.degreeVec(0F, 0F, 46.13F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.6137F, KeyframeAnimations.degreeVec(0F, 0F, 43.3669F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.646F, KeyframeAnimations.degreeVec(0F, 0F, 35.1309F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.7106F, KeyframeAnimations.degreeVec(0F, 0F, 10.9991F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.7429F, KeyframeAnimations.degreeVec(0F, 0F, 2.7631F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.7752F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0323F, KeyframeAnimations.degreeVec(0F, 0F, -2.7381F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0646F, KeyframeAnimations.degreeVec(0F, 0F, -12.603F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0969F, KeyframeAnimations.degreeVec(0F, 0F, -27.397F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1292F, KeyframeAnimations.degreeVec(0F, 0F, -37.2619F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1615F, KeyframeAnimations.degreeVec(0F, 0F, -40F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1938F, KeyframeAnimations.degreeVec(0F, 0F, -42.2384F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2261F, KeyframeAnimations.degreeVec(0F, 0F, -48.9104F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2907F, KeyframeAnimations.degreeVec(0F, 0F, -68.4596F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.323F, KeyframeAnimations.degreeVec(0F, 0F, -75.1316F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.3553F, KeyframeAnimations.degreeVec(0F, 0F, -77.37F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.3876F, KeyframeAnimations.degreeVec(0F, 0F, -68.8408F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4199F, KeyframeAnimations.degreeVec(0F, 0F, -46.304F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4522F, KeyframeAnimations.degreeVec(0F, 0F, -14.9633F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4845F, KeyframeAnimations.degreeVec(0F, 0F, 19.3833F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5168F, KeyframeAnimations.degreeVec(0F, 0F, 50.724F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5491F, KeyframeAnimations.degreeVec(0F, 0F, 73.2608F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5814F, KeyframeAnimations.degreeVec(0F, 0F, 81.79F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.6137F, KeyframeAnimations.degreeVec(0F, 0F, 76.8909F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.646F, KeyframeAnimations.degreeVec(0F, 0F, 62.2883F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.7106F, KeyframeAnimations.degreeVec(0F, 0F, 19.5017F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.7429F, KeyframeAnimations.degreeVec(0F, 0F, 4.8991F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.7752F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0323F, KeyframeAnimations.degreeVec(0F, 0F, -2.9092F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0646F, KeyframeAnimations.degreeVec(0F, 0F, -13.3907F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0969F, KeyframeAnimations.degreeVec(0F, 0F, -29.1093F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1292F, KeyframeAnimations.degreeVec(0F, 0F, -39.5908F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1615F, KeyframeAnimations.degreeVec(0F, 0F, -42.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1938F, KeyframeAnimations.degreeVec(0F, 0F, -38.252F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2261F, KeyframeAnimations.degreeVec(0F, 0F, -25.5901F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2907F, KeyframeAnimations.degreeVec(0F, 0F, 11.5101F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.323F, KeyframeAnimations.degreeVec(0F, 0F, 24.172F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.3553F, KeyframeAnimations.degreeVec(0F, 0F, 28.42F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.3876F, KeyframeAnimations.degreeVec(0F, 0F, 24.5187F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4199F, KeyframeAnimations.degreeVec(0F, 0F, 14.2104F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4522F, KeyframeAnimations.degreeVec(0F, 0F, -0.1249F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4845F, KeyframeAnimations.degreeVec(0F, 0F, -15.8351F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5168F, KeyframeAnimations.degreeVec(0F, 0F, -30.1704F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5491F, KeyframeAnimations.degreeVec(0F, 0F, -40.4787F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5814F, KeyframeAnimations.degreeVec(0F, 0F, -44.38F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.6137F, KeyframeAnimations.degreeVec(0F, 0F, -41.7217F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.646F, KeyframeAnimations.degreeVec(0F, 0F, -33.7982F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.7106F, KeyframeAnimations.degreeVec(0F, 0F, -10.5818F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.7429F, KeyframeAnimations.degreeVec(0F, 0F, -2.6583F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.7752F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0323F, KeyframeAnimations.degreeVec(0F, 0F, 2.567F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0646F, KeyframeAnimations.degreeVec(0F, 0F, 11.8153F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0969F, KeyframeAnimations.degreeVec(0F, 0F, 25.6847F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1292F, KeyframeAnimations.degreeVec(0F, 0F, 34.933F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1615F, KeyframeAnimations.degreeVec(0F, 0F, 37.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1938F, KeyframeAnimations.degreeVec(0F, 0F, 34.3948F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2261F, KeyframeAnimations.degreeVec(0F, 0F, 25.1395F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2907F, KeyframeAnimations.degreeVec(0F, 0F, -1.9795F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.323F, KeyframeAnimations.degreeVec(0F, 0F, -11.2348F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.3553F, KeyframeAnimations.degreeVec(0F, 0F, -14.34F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.3876F, KeyframeAnimations.degreeVec(0F, 0F, -11.2469F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4199F, KeyframeAnimations.degreeVec(0F, 0F, -3.0738F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4522F, KeyframeAnimations.degreeVec(0F, 0F, 8.292F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4845F, KeyframeAnimations.degreeVec(0F, 0F, 20.748F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5168F, KeyframeAnimations.degreeVec(0F, 0F, 32.1138F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5491F, KeyframeAnimations.degreeVec(0F, 0F, 40.2869F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5814F, KeyframeAnimations.degreeVec(0F, 0F, 43.38F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.6137F, KeyframeAnimations.degreeVec(0F, 0F, 40.7816F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.646F, KeyframeAnimations.degreeVec(0F, 0F, 33.0367F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.7106F, KeyframeAnimations.degreeVec(0F, 0F, 10.3433F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.7429F, KeyframeAnimations.degreeVec(0F, 0F, 2.5984F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.7752F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("lengua", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0323F, KeyframeAnimations.posVec(-0.2054F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0646F, KeyframeAnimations.posVec(-0.9452F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0969F, KeyframeAnimations.posVec(-2.0548F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1292F, KeyframeAnimations.posVec(-2.7946F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1615F, KeyframeAnimations.posVec(-3F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1938F, KeyframeAnimations.posVec(-3F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5814F, KeyframeAnimations.posVec(-3F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.6137F, KeyframeAnimations.posVec(-2.8203F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.646F, KeyframeAnimations.posVec(-2.2847F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.7106F, KeyframeAnimations.posVec(-0.7153F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.7429F, KeyframeAnimations.posVec(-0.1797F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.7752F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("lengua", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0323F, KeyframeAnimations.degreeVec(-0.609F, 1.7885F, -1.3395F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0646F, KeyframeAnimations.degreeVec(-2.8031F, 8.2323F, -6.1655F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0969F, KeyframeAnimations.degreeVec(-6.0936F, 17.8957F, -13.403F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1292F, KeyframeAnimations.degreeVec(-8.2877F, 24.3395F, -18.229F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1615F, KeyframeAnimations.degreeVec(-8.8967F, 26.128F, -19.5685F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1938F, KeyframeAnimations.degreeVec(-8.3638F, 24.563F, -19.7441F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2261F, KeyframeAnimations.degreeVec(-6.7754F, 19.8982F, -20.2675F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2907F, KeyframeAnimations.degreeVec(-2.1213F, 6.2298F, -21.801F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.323F, KeyframeAnimations.degreeVec(-0.5329F, 1.565F, -22.3244F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.3553F, KeyframeAnimations.degreeVec(0F, 0F, -22.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.3876F, KeyframeAnimations.degreeVec(0.2414F, -1.0453F, -22.0056F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4199F, KeyframeAnimations.degreeVec(0.8792F, -3.8074F, -20.6993F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4522F, KeyframeAnimations.degreeVec(1.7662F, -7.6485F, -18.8827F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4845F, KeyframeAnimations.degreeVec(2.7382F, -11.8579F, -16.8919F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5168F, KeyframeAnimations.degreeVec(3.6252F, -15.699F, -15.0753F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5491F, KeyframeAnimations.degreeVec(4.263F, -18.4611F, -13.769F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5814F, KeyframeAnimations.degreeVec(4.5044F, -19.5064F, -13.2746F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.6137F, KeyframeAnimations.degreeVec(4.2346F, -18.338F, -12.4795F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.646F, KeyframeAnimations.degreeVec(3.4304F, -14.8554F, -10.1095F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.7106F, KeyframeAnimations.degreeVec(1.074F, -4.651F, -3.1651F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.7429F, KeyframeAnimations.degreeVec(0.2698F, -1.1684F, -0.7951F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.7752F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .build();

    public static final AnimationDefinition ABRIENDOSE = AnimationDefinition.Builder.withLength(0.4583F)
        .addAnimation("tapa", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, -2.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0F, 0F, -105.56F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.375F, KeyframeAnimations.degreeVec(0F, 0F, -90F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0F, 0F, -82.5F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("chester", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2917F, KeyframeAnimations.posVec(0F, -2F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0F, 0F, -92.5F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0F, 0F, -87.5F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0F, 0F, -90F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0F, 0F, -90F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("lengua", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0.2083F, KeyframeAnimations.posVec(0F, -1F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2917F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.375F, KeyframeAnimations.posVec(-0.6F, 0.2F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.posVec(0.6F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("lengua", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2083F, KeyframeAnimations.degreeVec(0F, 0F, -7.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("lengua", new AnimationChannel(AnimationChannel.Targets.SCALE,
            new Keyframe(0.2083F, KeyframeAnimations.scaleVec(0.8F, 0.9F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2917F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR)))
        .build();

    public static final AnimationDefinition ABRIENDOSE_SENTADO = AnimationDefinition.Builder.withLength(0.4583F)
        .addAnimation("tapa", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, -2.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0F, 0F, -105.56F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.375F, KeyframeAnimations.degreeVec(0F, 0F, -90F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0F, 0F, -82.5F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("chester", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, -2F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, -92.5F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, -87.5F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, -90F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, -90F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("lengua", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0.2083F, KeyframeAnimations.posVec(0F, -1F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2917F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.375F, KeyframeAnimations.posVec(-0.6F, 0.2F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.posVec(0.6F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("lengua", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2083F, KeyframeAnimations.degreeVec(0F, 0F, -7.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("lengua", new AnimationChannel(AnimationChannel.Targets.SCALE,
            new Keyframe(0.2083F, KeyframeAnimations.scaleVec(0.8F, 0.9F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2917F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR)))
        .build();

    public static final AnimationDefinition CERRANDOSE = AnimationDefinition.Builder.withLength(0.4583F)
        .addAnimation("tapa", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, -82.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0833F, KeyframeAnimations.degreeVec(0F, 0F, -90F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1666F, KeyframeAnimations.degreeVec(0F, 0F, -105.56F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0F, 0F, -2.5F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("chester", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0.1666F, KeyframeAnimations.posVec(0F, -2F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.1666F, KeyframeAnimations.degreeVec(0F, 0F, -92.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.1666F, KeyframeAnimations.degreeVec(0F, 0F, -87.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.1666F, KeyframeAnimations.degreeVec(0F, 0F, -90F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.1666F, KeyframeAnimations.degreeVec(0F, 0F, -90F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("lengua", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0.6F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0833F, KeyframeAnimations.posVec(-0.6F, 0.2F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1666F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.posVec(0F, -1F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("lengua", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.1666F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0F, 0F, -7.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("lengua", new AnimationChannel(AnimationChannel.Targets.SCALE,
            new Keyframe(0.1666F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.scaleVec(0.8F, 0.9F, 1F), AnimationChannel.Interpolations.LINEAR)))
        .build();

    public static final AnimationDefinition CERRANDOSE_SENTADO = AnimationDefinition.Builder.withLength(0.4583F)
        .addAnimation("tapa", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, -82.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0833F, KeyframeAnimations.degreeVec(0F, 0F, -90F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1666F, KeyframeAnimations.degreeVec(0F, 0F, -105.56F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0F, 0F, -2.5F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("chester", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0.4583F, KeyframeAnimations.posVec(0F, -2F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0F, 0F, -92.5F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0F, 0F, -87.5F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0F, 0F, -90F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0F, 0F, -90F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("lengua", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0.6F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0833F, KeyframeAnimations.posVec(-0.6F, 0.2F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1666F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.posVec(0F, -1F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("lengua", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.1666F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0F, 0F, -7.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("lengua", new AnimationChannel(AnimationChannel.Targets.SCALE,
            new Keyframe(0.1666F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.scaleVec(0.8F, 0.9F, 1F), AnimationChannel.Interpolations.LINEAR)))
        .build();

    public static final AnimationDefinition ESTATICO_CERRADO = AnimationDefinition.Builder.withLength(1F).looping()
        .addAnimation("tapa", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, -2.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0F, 0F, -2F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1F, KeyframeAnimations.degreeVec(0F, 0F, -2.5F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("chester", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.posVec(0F, 0.3F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("chester", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.posVec(0F, -0.3F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata2", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.posVec(0F, -0.3F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata3", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.posVec(0F, -0.3F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata4", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.posVec(0F, -0.3F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("lengua", new AnimationChannel(AnimationChannel.Targets.SCALE,
            new Keyframe(0F, KeyframeAnimations.scaleVec(0.6F, 1F, 1F), AnimationChannel.Interpolations.LINEAR)))
        .build();

    public static final AnimationDefinition ESTATICO_ABIERTO = AnimationDefinition.Builder.withLength(0.4583F).looping()
        .addAnimation("tapa", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, -90F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0F, 0F, -95F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0F, 0F, -90F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("chester", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, -2F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.posVec(0F, -1.8F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.posVec(0F, -2F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.posVec(0F, -0.2F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, -92.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0F, 0F, -92.5F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata2", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.posVec(0F, -0.2F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, -87.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0F, 0F, -87.5F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata3", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.posVec(0F, -0.2F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, -90F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0F, 0F, -90F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata4", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.posVec(0F, -0.2F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, -90F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0F, 0F, -90F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("lengua", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0.6F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0833F, KeyframeAnimations.posVec(0.3F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1667F, KeyframeAnimations.posVec(0.6F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.posVec(0.3F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.3333F, KeyframeAnimations.posVec(0.6F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4167F, KeyframeAnimations.posVec(0.3F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.posVec(0.6F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .build();

    public static final AnimationDefinition SENTADO = AnimationDefinition.Builder.withLength(1.5278F).looping()
        .addAnimation("tapa", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, -7.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1F, KeyframeAnimations.degreeVec(0F, 0F, -7.5463F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.6F, KeyframeAnimations.degreeVec(0F, 0F, -8.7458F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.8F, KeyframeAnimations.degreeVec(0F, 0F, -9F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.9F, KeyframeAnimations.degreeVec(0F, 0F, -8.9492F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1F, KeyframeAnimations.degreeVec(0F, 0F, -8.7714F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.3F, KeyframeAnimations.degreeVec(0F, 0F, -7.7148F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4F, KeyframeAnimations.degreeVec(0F, 0F, -7.545F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.5F, KeyframeAnimations.degreeVec(0F, 0F, -7.5F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("chester", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, -2F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.8F, KeyframeAnimations.posVec(0F, -1.8F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.5F, KeyframeAnimations.posVec(0F, -2F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.8F, KeyframeAnimations.posVec(0F, -0.2F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, -92.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.5F, KeyframeAnimations.degreeVec(0F, 0F, -92.5F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata2", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.8F, KeyframeAnimations.posVec(0F, -0.2F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, -87.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.5F, KeyframeAnimations.degreeVec(0F, 0F, -87.5F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata3", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.8F, KeyframeAnimations.posVec(0F, -0.2F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, -90F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.5F, KeyframeAnimations.degreeVec(0F, 0F, -90F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata4", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.8F, KeyframeAnimations.posVec(0F, -0.2F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, -90F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.5F, KeyframeAnimations.degreeVec(0F, 0F, -90F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("lengua", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0.6F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.3F, KeyframeAnimations.posVec(0.3F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.6F, KeyframeAnimations.posVec(0.6F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.8F, KeyframeAnimations.posVec(0.3F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.1F, KeyframeAnimations.posVec(0.6F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4F, KeyframeAnimations.posVec(0.3F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.5F, KeyframeAnimations.posVec(0.6F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .build();

    public static final AnimationDefinition OLFATO = AnimationDefinition.Builder.withLength(1.5833F)
        .addAnimation("tapa", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.125F, KeyframeAnimations.posVec(0F, 0F, -0.1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4167F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5417F, KeyframeAnimations.posVec(0F, 0F, -0.1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.6667F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.8333F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.9583F, KeyframeAnimations.posVec(0F, 0F, -0.1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.0833F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.25F, KeyframeAnimations.posVec(0F, 0F, -0.1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.posVec(0F, 0F, -0.1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("tapa", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("tapa", new AnimationChannel(AnimationChannel.Targets.SCALE,
            new Keyframe(0F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("chester", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("chester", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0F, 0F, 12.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.degreeVec(0F, 0F, 12.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("chester", new AnimationChannel(AnimationChannel.Targets.SCALE,
            new Keyframe(0F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("dientes", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("dientes", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("dientes", new AnimationChannel(AnimationChannel.Targets.SCALE,
            new Keyframe(0F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("dientes2", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("dientes2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("dientes2", new AnimationChannel(AnimationChannel.Targets.SCALE,
            new Keyframe(0F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.posVec(0F, 0.2F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.posVec(0F, 0.2F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0F, 0F, -15F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.degreeVec(0F, 0F, -15F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata", new AnimationChannel(AnimationChannel.Targets.SCALE,
            new Keyframe(0F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata2", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.posVec(0F, 2F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.posVec(0F, 2F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0F, 0F, -15F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.degreeVec(0F, 0F, -15F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata2", new AnimationChannel(AnimationChannel.Targets.SCALE,
            new Keyframe(0F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata3", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.posVec(0F, 2F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.posVec(0F, 2F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0F, 0F, -10F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.degreeVec(0F, 0F, -10F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata3", new AnimationChannel(AnimationChannel.Targets.SCALE,
            new Keyframe(0F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata4", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.posVec(0F, 0.2F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.posVec(0F, 0.2F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0F, 0F, -15F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.degreeVec(0F, 0F, -15F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("pata4", new AnimationChannel(AnimationChannel.Targets.SCALE,
            new Keyframe(0F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("lengua", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("lengua", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("lengua", new AnimationChannel(AnimationChannel.Targets.SCALE,
            new Keyframe(0F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("SEGURO", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("SEGURO", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("SEGURO", new AnimationChannel(AnimationChannel.Targets.SCALE,
            new Keyframe(0F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4167F, KeyframeAnimations.scaleVec(1F, 1.05F, 1.24F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5417F, KeyframeAnimations.scaleVec(1F, 1.1025F, 1.094F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.6667F, KeyframeAnimations.scaleVec(1F, 1.05F, 1.24F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.7917F, KeyframeAnimations.scaleVec(1F, 1.1025F, 1.094F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.9167F, KeyframeAnimations.scaleVec(1F, 1.05F, 1.24F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.0417F, KeyframeAnimations.scaleVec(1F, 1.1025F, 1.094F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.25F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.4583F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR)))
        .build();

    public static final AnimationDefinition MUERTE = AnimationDefinition.Builder.withLength(1.0417F)
        .addAnimation("tapa", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("tapa", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0F, 0F, -95F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.6F, KeyframeAnimations.degreeVec(0F, 0F, -117.5F), AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("tapa", new AnimationChannel(AnimationChannel.Targets.SCALE,
            new Keyframe(0F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("chester", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("chester", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(102.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("chester", new AnimationChannel(AnimationChannel.Targets.SCALE,
            new Keyframe(0F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.1F, KeyframeAnimations.scaleVec(1F, 1.7F, 1F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.3F, KeyframeAnimations.scaleVec(1F, 0.2F, 1F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.5F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("pata", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("pata", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(95F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.6F, KeyframeAnimations.degreeVec(95.3191F, -19.9207F, -1.8169F), AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("pata", new AnimationChannel(AnimationChannel.Targets.SCALE,
            new Keyframe(0F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("pata2", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("pata2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0F, 0F, -95F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.6F, KeyframeAnimations.degreeVec(0F, 0F, -77.5F), AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("pata2", new AnimationChannel(AnimationChannel.Targets.SCALE,
            new Keyframe(0F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("pata3", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("pata3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(92.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.6F, KeyframeAnimations.degreeVec(72.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("pata3", new AnimationChannel(AnimationChannel.Targets.SCALE,
            new Keyframe(0F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("pata4", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("pata4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0F, 0F, 90F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.6F, KeyframeAnimations.degreeVec(0F, 0F, 70F), AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("pata4", new AnimationChannel(AnimationChannel.Targets.SCALE,
            new Keyframe(0F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("lengua", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.5F, KeyframeAnimations.posVec(0F, -1F, 1F), AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("lengua", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-58.5235F, 29.1271F, -37.9763F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.6F, KeyframeAnimations.degreeVec(-67.1069F, 36.0305F, -53.8161F), AnimationChannel.Interpolations.CATMULLROM)))
        .build();
}
