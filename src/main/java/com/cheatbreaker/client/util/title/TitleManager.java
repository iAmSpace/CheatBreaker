package com.cheatbreaker.client.util.title;

import com.cheatbreaker.client.event.type.GuiDrawEvent;
import com.cheatbreaker.client.event.type.TickEvent;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;

public class TitleManager {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final List<Title> titles = Lists.newArrayList();

    public void onDraw(GuiDrawEvent event) {
        GL11.glEnable(3042);
        for (Title title : this.titles) {
            boolean isTitle = Title.getType(title) == Title.TitleType.TITLE;
            float f = isTitle ? (float)4 : 1.875f * 0.8f;
            float f2 = isTitle ? (float)-30 : (float)10;
            GL11.glScalef(f *= Title.getScale(title), f, f);
            float f3 = 255;
            if (title.isFadingIn()) {
                long var8_8 = Title.getFadeInTime(title) - (System.currentTimeMillis() - title.startTime);
                f3 = 1.0f - var8_8 / (float)Title.getFadeInTime(title);
            } else if (title.isFullyVisible()) {
                long var8_8 = Title.getDisplayTime(title) - (System.currentTimeMillis() - title.startTime);
                f3 = var8_8 <= 0.0f ? 0.0f : var8_8 / (float)Title.getFadeOutTime(title);
            }
            f3 = Math.min(1.0f, Math.max(0.0f, f3));
            if ((double)f3 <= 0.8611111044883728 * 0.17419354972680576) {
                f3 = 1.6f * 0.09375f;
            }
            this.mc.fontRenderer.drawStringWithShadow(Title.getMessage(title), (int)(((float)(event.getResolution().getScaledWidth() / 2) / f) - (this.mc.fontRenderer.getStringWidth(Title.getMessage(title)) / 2f)), (int)(((float)(event.getResolution().getScaledHeight() / 2 - this.mc.fontRenderer.FONT_HEIGHT / 2) + f2) / f), new Color(1.0f, 1.0f, 1.0f, f3).getRGB());
            GL11.glScalef(1.0f / f, 1.0f / f, 1.0f / f);
        }
        GL11.glDisable(3042);
    }

    public void onTick(TickEvent event) {
        if (!this.titles.isEmpty()) {
            this.titles.removeIf(t -> t.startTime + Title.getDisplayTime(t) < System.currentTimeMillis());
        }
    }

    public List<Title> getTitles() {
        return this.titles;
    }
}
 