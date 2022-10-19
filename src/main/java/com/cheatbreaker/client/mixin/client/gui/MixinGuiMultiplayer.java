package com.cheatbreaker.client.mixin.client.gui;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMultiplayer.class)
public class MixinGuiMultiplayer {
    @Shadow private GuiScreen field_146798_g;

    @Inject(method = "<init>(Lnet/minecraft/client/gui/GuiScreen;)V", at = @At("RETURN"))
    public void impl$init(GuiScreen screen, CallbackInfo callbackInfo) {
        if (screen instanceof GuiMainMenu)
            this.field_146798_g = new GuiScreen();
    }
}
