package com.cheatbreaker.client.mixin.client.renderer;

import com.cheatbreaker.client.bridge.client.renderer.TessellatorBridge;
import net.minecraft.client.renderer.Tessellator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Tessellator.class)
public abstract class MixinTessellator implements TessellatorBridge {
    @Shadow public abstract void startDrawingQuads();
    @Shadow public abstract void addVertexWithUV(double p_78374_1_, double p_78374_3_, double p_78374_5_, double p_78374_7_, double p_78374_9_);

    @Shadow public abstract int draw();
    @Shadow public abstract void addVertex(double p_78377_1_, double p_78377_3_, double p_78377_5_);

    @Shadow public abstract void startDrawing(int p_78371_1_);

    @Shadow public abstract void setColorOpaque_I(int p_78378_1_);

    @Shadow public abstract void setColorRGBA_F(float p_78369_1_, float p_78369_2_, float p_78369_3_, float p_78369_4_);

    @Shadow public abstract void setTranslation(double p_78373_1_, double p_78373_3_, double p_78373_5_);

    @Shadow public abstract void setColorRGBA_I(int p_78384_1_, int p_78384_2_);

    public void bridge$startDrawingQuads() {
        this.startDrawingQuads();
    }

    public void bridge$addVertexWithUV(double x, double y, double z, double u, double v) {
        this.addVertexWithUV(x, y, z, u, v);
    }

    public void bridge$finish() {
        this.draw();
    }

    public void bridge$addVertex(double x, double y, double z) {
        this.addVertex(x, y, z);
    }

    public void bridge$startDrawing(int mode) {
        this.startDrawing(mode);
    }

    public void bridge$setColorOpaque_I(int color) {
        this.setColorOpaque_I(color);
    }

    public void bridge$setColorRGBA_F(float r, float g, float b, float a) {
        this.setColorRGBA_F(r, g, b, a);
    }

    public void bridge$setTranslation(double x, double y, double z) {
        this.setTranslation(x, y, z);
    }

    public void bridge$setColorRGBA_I(int color, int alpha) {
        this.setColorRGBA_I(color, alpha);
    }
}
