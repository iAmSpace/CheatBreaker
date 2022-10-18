package com.cheatbreaker.client.mixin.client.renderer.entity;

import com.cheatbreaker.client.bridge.client.renderer.entity.RendererLivingEntityBridge;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RendererLivingEntity.class)
public abstract class MixinRendererLivingEntity implements RendererLivingEntityBridge {
    @Shadow protected ModelBase renderPassModel;

    @Shadow protected ModelBase mainModel;

    @Shadow protected abstract int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_);

    @Shadow protected abstract void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_);

    public ModelBase bridge$getRenderPassModel() {
        return this.renderPassModel;
    }

    public ModelBase bridge$getMainModel() {
        return this.mainModel;
    }

    public int bridge$shouldRenderPass(EntityLivingBase entity, int i, float v) {
        return this.shouldRenderPass(entity, i, v);
    }

    public void bridge$preRenderCallback(EntityLivingBase par1EntityLivingBase, float v) {
        this.preRenderCallback(par1EntityLivingBase, v);
    }
}
