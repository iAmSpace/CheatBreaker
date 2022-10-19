package com.cheatbreaker.client.mixin.client.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GuiIngame.class)
public abstract class MixinGuiIngame extends Gui {
}
