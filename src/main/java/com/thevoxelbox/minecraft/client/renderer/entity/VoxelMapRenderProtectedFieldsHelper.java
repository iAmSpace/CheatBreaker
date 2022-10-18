package com.thevoxelbox.minecraft.client.renderer.entity;

import com.cheatbreaker.client.bridge.client.renderer.entity.RenderBridge;
import com.cheatbreaker.client.bridge.client.renderer.entity.RendererLivingEntityBridge;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class VoxelMapRenderProtectedFieldsHelper {
	public static ResourceLocation getRendersResourceLocation(Render render, Entity entity) {
		return ((RenderBridge) render).bridge$getEntityTexture(entity);
	}

	public static ModelBase getRendersModel(RendererLivingEntity render) {
		return ((RendererLivingEntityBridge) render).bridge$getMainModel();
	}

	public static ModelBase getRendersPassModel(Render render, Entity entity) {
		((RendererLivingEntityBridge)((RendererLivingEntity) render)).bridge$shouldRenderPass((EntityLivingBase) entity, 0, 0.0F);
		return ((RendererLivingEntityBridge)((RendererLivingEntity) render)).bridge$getRenderPassModel();
	}

	public static void preRender(EntityLivingBase par1EntityLivingBase, RendererLivingEntity render) {
		((RendererLivingEntityBridge) render).bridge$preRenderCallback(par1EntityLivingBase, 1.0F);
	}
}
