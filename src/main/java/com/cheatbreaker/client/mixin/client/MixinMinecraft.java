package com.cheatbreaker.client.mixin.client;

import com.cheatbreaker.client.bridge.client.MinecraftBridge;
import com.cheatbreaker.client.bridge.client.audio.SoundHandlerBridge;
import com.cheatbreaker.client.ui.mainmenu.MainMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements MinecraftBridge {
    @Shadow public abstract SoundHandler getSoundHandler();
    @Shadow private static int debugFPS;

    @Shadow public abstract void displayGuiScreen(GuiScreen guiScreenIn);

    @ModifyArg(method = "startGame", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V"))
    public String startGameTitleOverride(String newTitle) {
        return "CheatBreaker";
    }

    public SoundHandlerBridge bridge$getSoundHandler() {
        return (SoundHandlerBridge) this.getSoundHandler();
    }

    public int bridge$getDebugFPS() {
        return debugFPS;
    }

    @Inject(method = "displayGuiScreen", at = @At("HEAD"), cancellable = true)
    public void impl$displayGuiScreen(GuiScreen screen, CallbackInfo callbackInfo)  {
        if (screen instanceof GuiMainMenu) {
            this.displayGuiScreen(new MainMenu());
            callbackInfo.cancel();
        }
    }
}
