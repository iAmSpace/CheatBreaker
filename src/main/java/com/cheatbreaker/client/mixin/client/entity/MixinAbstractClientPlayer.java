package com.cheatbreaker.client.mixin.client.entity;

import com.cheatbreaker.client.bridge.client.entity.AbstractClientPlayerBridge;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractClientPlayer.class)
public class MixinAbstractClientPlayer implements AbstractClientPlayerBridge {
    @Shadow private ResourceLocation locationCape;

    public void bridge$setLocationCape(ResourceLocation location) {
        this.locationCape = location;
    }
}
