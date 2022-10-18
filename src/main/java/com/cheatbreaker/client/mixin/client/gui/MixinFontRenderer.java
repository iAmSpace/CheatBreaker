package com.cheatbreaker.client.mixin.client.gui;

import com.cheatbreaker.client.bridge.client.gui.FontRendererBridge;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FontRenderer.class)
public class MixinFontRenderer implements FontRendererBridge {
    @Shadow private int[] colorCode;

    public int[] bridge$getColorCode() {
        return this.colorCode;
    }
}
