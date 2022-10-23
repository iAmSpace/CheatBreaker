package com.cheatbreaker.bridge;

import com.cheatbreaker.bridge.client.MinecraftBridge;
import com.cheatbreaker.bridge.client.renderer.TessellatorBridge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

public class Ref {
    public static MinecraftBridge getMinecraft() {
        return (MinecraftBridge) Minecraft.getMinecraft();
    }

    public static TessellatorBridge getTessellator() {
        return (TessellatorBridge) Tessellator.instance;
    }

    public static void modified$drawRect(float left, float top, float right, float bottom, int color) {
        float tempVar;

        if (left < right)
        {
            tempVar = left;
            left = right;
            right = tempVar;
        }

        if (top < bottom)
        {
            tempVar = top;
            top = bottom;
            bottom = tempVar;
        }

        float a = (float)(color >> 24 & 255) / 255.0F;
        float r = (float)(color >> 16 & 255) / 255.0F;
        float g = (float)(color >> 8 & 255) / 255.0F;
        float b = (float)(color & 255) / 255.0F;

        TessellatorBridge tessellator = getTessellator();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(r, g, b, a);
        tessellator.bridge$startDrawingQuads();
        tessellator.bridge$addVertex(left, bottom, 0.0D);
        tessellator.bridge$addVertex(right, bottom, 0.0D);
        tessellator.bridge$addVertex(right, top, 0.0D);
        tessellator.bridge$addVertex(left, top, 0.0D);
        tessellator.bridge$finish();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void modified$drawGradientRect(float left, float top, float right, float bottom, int startColor, int endColor)
    {
        float startA = (float)(startColor >> 24 & 255) / 255.0F;
        float startR = (float)(startColor >> 16 & 255) / 255.0F;
        float startG = (float)(startColor >> 8 & 255) / 255.0F;
        float startB = (float)(startColor & 255) / 255.0F;

        float endA = (float)(endColor >> 24 & 255) / 255.0F;
        float endR = (float)(endColor >> 16 & 255) / 255.0F;
        float endG = (float)(endColor >> 8 & 255) / 255.0F;
        float endB = (float)(endColor & 255) / 255.0F;

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        TessellatorBridge tessellator = getTessellator();
        tessellator.bridge$startDrawingQuads();
        tessellator.bridge$setColorRGBA_F(startR, startG, startB, startA);
        tessellator.bridge$addVertex(right, top, 0);
        tessellator.bridge$addVertex(left, top, 0);
        tessellator.bridge$setColorRGBA_F(endR, endG, endB, endA);
        tessellator.bridge$addVertex(left, bottom, 0);
        tessellator.bridge$addVertex(right, bottom, 0);
        tessellator.bridge$finish();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public static void modified$drawBoxWithOutLine(float f, float f2, float f3, float f4, float f5, int n, int n2) {
        modified$drawRect(f, f2, f3, f4, n2);
        modified$drawRect(f - f5, f2 - f5, f, f4 + f5, n);
        modified$drawRect(f3, f2 - f5, f3 + f5, f4 + f5, n);
        modified$drawRect(f, f2 - f5, f3, f2, n);
        modified$drawRect(f, f4, f3, f4 + f5, n);
    }

    public static void modified$drawRectWithOutline(float f, float f2, float f3, float f4, float f5, int n, int n2) {
        modified$drawRect(f + f5, f2 + f5, f3 - f5, f4 - f5, n2);
        modified$drawRect(f, f2 + f5, f + f5, f4 - f5, n);
        modified$drawRect(f3 - f5, f2 + f5, f3, f4 - f5, n);
        modified$drawRect(f, f2, f3, f2 + f5, n);
        modified$drawRect(f, f4 - f5, f3, f4, n);
    }

    public static ScaledResolution createScaledResolution() {
        return new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
    }
}
