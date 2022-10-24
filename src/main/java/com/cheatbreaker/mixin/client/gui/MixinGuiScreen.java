package com.cheatbreaker.mixin.client.gui;

import com.cheatbreaker.client.ui.overlay.OverlayGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.awt.*;

@Mixin(GuiScreen.class)
public class MixinGuiScreen extends Gui {
    @Shadow public Minecraft mc;

    @Shadow public int height;

    @Shadow public int width;

    /**
     * @author iAmSpace
     * @reason Custom CheatBreaker Backgrounds (more like voids but we move)
     */
    @Overwrite
    public void drawWorldBackground(int p_146270_1_) {
        if (this.mc.theWorld != null || OverlayGui.getInstance() != null)
            drawGradientRect(0, 0, this.width, this.height,
                    new Color(0xA6101010, true).getRGB(),
                    new Color(0x32101010, true).getRGB());
        else
            this.func_146278_c(p_146270_1_);
    }

    public void func_146278_c(int p_146278_1_)
    {
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_FOG);
        Tessellator var2 = Tessellator.instance;
        this.mc.getTextureManager().bindTexture(optionsBackground);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float var3 = 32.0F;
        var2.startDrawingQuads();
        var2.setColorOpaque_I(4210752);
        var2.addVertexWithUV(0.0D, this.height, 0.0D, 0.0D, (double)((float)this.height / var3 + (float)p_146278_1_));
        var2.addVertexWithUV(this.width, this.height, 0.0D, (float)this.width / var3, (float)this.height / var3 + (float)p_146278_1_);
        var2.addVertexWithUV(this.width, 0.0D, 0.0D, (float)this.width / var3, p_146278_1_);
        var2.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, p_146278_1_);
        var2.draw();
    }
}
