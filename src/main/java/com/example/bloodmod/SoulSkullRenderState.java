package com.example.bloodmod;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class SoulSkullRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnim = new AnimationState();
    public final AnimationState atkAnim = new AnimationState();
    public final AnimationState followAnim = new AnimationState();
}
