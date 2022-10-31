package com.cheatbreaker.bridge.client;

import com.cheatbreaker.bridge.client.audio.SoundHandlerBridge;
import com.cheatbreaker.bridge.client.gui.FontRendererBridge;
import com.cheatbreaker.bridge.client.gui.GuiScreenBridge;
import com.cheatbreaker.bridge.util.TimerBridge;
import com.cheatbreaker.client.ui.module.CBModulesGui;
import net.minecraft.client.gui.GuiScreen;

public interface MinecraftBridge {
    SoundHandlerBridge bridge$getSoundHandler();
    int bridge$getDebugFPS();
    TimerBridge bridge$getTimer();
    FontRendererBridge bridge$getFontRenderer();
    GuiScreenBridge bridge$getCurrentScreen();
    void bridge$displayGuiScreen(GuiScreen screen);
}
