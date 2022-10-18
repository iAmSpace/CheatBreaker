package com.cheatbreaker.client.mixin.client.renderer.entity;

import com.cheatbreaker.client.bridge.client.renderer.entity.RenderBridge;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Render.class)
public abstract class MixinRender implements RenderBridge {
    @Shadow protected abstract ResourceLocation getEntityTexture(Entity p_110775_1_);

    public ResourceLocation bridge$getEntityTexture(Entity entity) {
        return this.getEntityTexture(entity);
    }
}
