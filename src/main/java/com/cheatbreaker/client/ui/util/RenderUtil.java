package com.cheatbreaker.client.ui.util;

import com.cheatbreaker.client.bridge.Ref;
import com.cheatbreaker.client.bridge.client.renderer.TessellatorBridge;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderUtil {
    protected static float zLevel = 0.0f;

    public static void lIIIIlIIllIIlIIlIIIlIIllI(float f, float f2, float f3, float f4, int n, int n2) {
        float f5 = 3.875f * 0.0010080645f;
        TessellatorBridge tessellator = Ref.getTessellator();
        tessellator.bridge$startDrawingQuads();
        tessellator.bridge$addVertexWithUV(f, f2 + (float)n2, zLevel, f3 * f5, (f4 + (float)n2) * f5);
        tessellator.bridge$addVertexWithUV(f + (float)n, f2 + (float)n2, zLevel, (f3 + (float)n) * f5, (f4 + (float)n2) * f5);
        tessellator.bridge$addVertexWithUV(f + (float)n, f2, zLevel, (f3 + (float)n) * f5, f4 * f5);
        tessellator.bridge$addVertexWithUV(f, f2, zLevel, f3 * f5, f4 * f5);
        tessellator.bridge$finish();
    }

    public static void drawIcon(ResourceLocation resourceLocation, float f, float f2, float f3) {
        float f4 = f * 2.0f;
        float f5 = f * 2.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        GL11.glEnable(3042);
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        GL11.glBegin(7);
        GL11.glTexCoord2d(f6 / f, f7 / f);
        GL11.glVertex2d(f2, f3);
        GL11.glTexCoord2d(f6 / f, (f7 + f) / f);
        GL11.glVertex2d(f2, f3 + f5);
        GL11.glTexCoord2d((f6 + f) / f, (f7 + f) / f);
        GL11.glVertex2d(f2 + f4, f3 + f5);
        GL11.glTexCoord2d((f6 + f) / f, f7 / f);
        GL11.glVertex2d(f2 + f4, f3);
        GL11.glEnd();
        GL11.glDisable(3042);
    }

    public static void lIIIIIIIIIlIllIIllIlIIlIl(ResourceLocation resourceLocation, float f, float f2, float f3) {
        float f4 = f * 2.0f;
        float f5 = f * 2.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        GL11.glEnable(3042);
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        GL11.glBegin(7);
        GL11.glTexCoord2d(f6 / f, f7 / f);
        GL11.glVertex2d(f2, f3);
        GL11.glTexCoord2d(f6 / f, (f7 + f) / f);
        GL11.glVertex2d(f2, f3 + f5);
        GL11.glTexCoord2d((f6 + f) / f, (f7 + f) / f);
        GL11.glVertex2d(f2 + f4, f3 + f5);
        GL11.glTexCoord2d((f6 + f) / f, f7 / f);
        GL11.glVertex2d(f2 + f4, f3);
        GL11.glEnd();
        GL11.glDisable(3042);
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI(ResourceLocation resourceLocation, float f, float f2, float f3, float f4) {
        float f5 = f3 / 2.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        GL11.glEnable(3042);
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        GL11.glBegin(7);
        GL11.glTexCoord2d(f6 / f5, f7 / f5);
        GL11.glVertex2d(f, f2);
        GL11.glTexCoord2d(f6 / f5, (f7 + f5) / f5);
        GL11.glVertex2d(f, f2 + f4);
        GL11.glTexCoord2d((f6 + f5) / f5, (f7 + f5) / f5);
        GL11.glVertex2d(f + f3, f2 + f4);
        GL11.glTexCoord2d((f6 + f5) / f5, f7 / f5);
        GL11.glVertex2d(f + f3, f2);
        GL11.glEnd();
        GL11.glDisable(3042);
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI(int x, int y, int width, int height, float f, int n5) {
        int n6 = height - y;
        int n7 = width - x;
        int n8 = n5 - height;
        GL11.glScissor((int)((float)x * f), (int)((float)n8 * f), (int)((float)n7 * f), (int)((float)n6 * f));
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI(double d, double d2, double d3, double d4, double d5, int n) {
        int n2;
        float f = (float)(n >> 24 & 0xFF) / (float)255;
        float f2 = (float)(n >> 16 & 0xFF) / (float)255;
        float f3 = (float)(n >> 8 & 0xFF) / (float)255;
        float f4 = (float)(n & 0xFF) / (float)255;
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5, 0.5, 0.5);
        d *= 2;
        d2 *= 2;
        d3 *= 2;
        d4 *= 2;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glEnable(2848);
        GL11.glBegin(9);
        for (n2 = 0; n2 <= 90; n2 += 3) {
            GL11.glVertex2d(d + d5 + Math.sin((double)n2 * (6.5973445528769465 * 0.4761904776096344) / (double)180) * (d5 * (double)-1), d2 + d5 + Math.cos((double)n2 * (42.5 * 0.07391982714328925) / (double)180) * (d5 * (double)-1));
        }
        for (n2 = 90; n2 <= 180; n2 += 3) {
            GL11.glVertex2d(d + d5 + Math.sin((double)n2 * (0.5711986642890533 * 5.5) / (double)180) * (d5 * (double)-1), d4 - d5 + Math.cos((double)n2 * (0.21052631735801697 * 14.922564993369743) / (double)180) * (d5 * (double)-1));
        }
        for (n2 = 0; n2 <= 90; n2 += 3) {
            GL11.glVertex2d(d3 - d5 + Math.sin((double)n2 * (4.466951941998311 * 0.7032967209815979) / (double)180) * d5, d4 - d5 + Math.cos((double)n2 * (28.33333396911621 * 0.11087973822685955) / (double)180) * d5);
        }
        for (n2 = 90; n2 <= 180; n2 += 3) {
            GL11.glVertex2d(d3 - d5 + Math.sin((double)n2 * ((double)0.6f * 5.2359875479235365) / (double)180) * d5, d2 + d5 + Math.cos((double)n2 * (2.8529412746429443 * 1.1011767685204017) / (double)180) * d5);
        }
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glScaled(2, 2, 2);
        GL11.glPopAttrib();
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI(double d, double d2, double d3) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        TessellatorBridge tessellator = Ref.getTessellator();
        tessellator.bridge$startDrawing(6);
        tessellator.bridge$addVertex(d, d2, zLevel);
        double d4 = 3.0 * 2.0943951023931953;
        double d5 = d4 / (double)30;
        for (double d6 = -d5; d6 < d4; d6 += d5) {
            tessellator.bridge$addVertex(d + d3 * Math.cos(-d6), d2 + d3 * Math.sin(-d6), zLevel);
        }
        tessellator.bridge$finish();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI(double d, double d2, double d3, double d4, double d5, int n, double d6) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        d5 = (d5 + (double)n) % (double)n;
        TessellatorBridge tessellator = Ref.getTessellator();
        for (double d7 = (double)360 / (double)n * d5; d7 < (double)360 / (double)n * (d5 + d6); d7 += 1.0) {
            double d8 = d7 * (0.6976743936538696 * 4.502949631183398) / (double)180;
            double d9 = (d7 - 1.0) * (1.9384295391612096 * 1.6206896305084229) / (double)180;
            double[] arrd = new double[]{Math.cos(d8) * d3, -Math.sin(d8) * d3, Math.cos(d9) * d3, -Math.sin(d9) * d3};
            double[] arrd2 = new double[]{Math.cos(d8) * d4, -Math.sin(d8) * d4, Math.cos(d9) * d4, -Math.sin(d9) * d4};
            tessellator.bridge$startDrawing(7);
            tessellator.bridge$addVertex(d + arrd2[0], d2 + arrd2[1], 0.0);
            tessellator.bridge$addVertex(d + arrd2[2], d2 + arrd2[3], 0.0);
            tessellator.bridge$addVertex(d + arrd[2], d2 + arrd[3], 0.0);
            tessellator.bridge$addVertex(d + arrd[0], d2 + arrd[1], 0.0);
            tessellator.bridge$finish();
        }
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
    }

    public static void drawHorizontalLine(float left, float right, float top, int color) {
        if (right < left) {
            float temp = left;
            left = right;
            right = temp;
        }
        Ref.modified$drawRect(left, top, right + 1.0f, top + 1.0f, color);
    }

    public static void drawVerticalLine(float left, float top, float bottom, int color) {
        if (bottom < top) {
            float temp = top;
            top = bottom;
            bottom = temp;
        }
        Ref.modified$drawRect(left, top + 1.0f, left + 1.0f, bottom, color);
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI(float f, float f2, float f3, float f4, int n, int n2, int n3) {
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Ref.modified$drawGradientRect((f *= 2.0f) + 1.0f, (f2 *= 2.0f) + 1.0f, (f3 *= 2.0f) - 1.0f, (f4 *= 2.0f) - 1.0f, n2, n3);
        RenderUtil.drawVerticalLine(f, f2 + 1.0f, f4 - 2.0f, n);
        RenderUtil.drawVerticalLine(f3 - 1.0f, f2 + 1.0f, f4 - 2.0f, n);
        RenderUtil.drawHorizontalLine(f + 2.0f, f3 - (float)3, f2, n);
        RenderUtil.drawHorizontalLine(f + 2.0f, f3 - (float)3, f4 - 1.0f, n);
        RenderUtil.drawHorizontalLine(f + 1.0f, f + 1.0f, f2 + 1.0f, n);
        RenderUtil.drawHorizontalLine(f3 - 2.0f, f3 - 2.0f, f2 + 1.0f, n);
        RenderUtil.drawHorizontalLine(f3 - 2.0f, f3 - 2.0f, f4 - 2.0f, n);
        RenderUtil.drawHorizontalLine(f + 1.0f, f + 1.0f, f4 - 2.0f, n);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
}
