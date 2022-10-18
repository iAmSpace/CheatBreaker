package com.cheatbreaker.client.bridge.client.renderer.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public interface RenderBridge {
    ResourceLocation bridge$getEntityTexture(Entity entity);
}
