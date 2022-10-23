package com.cheatbreaker.bridge.client;

import com.cheatbreaker.bridge.client.audio.SoundHandlerBridge;
import com.cheatbreaker.bridge.client.gui.FontRendererBridge;
import com.cheatbreaker.bridge.util.TimerBridge;

public interface MinecraftBridge {
    SoundHandlerBridge bridge$getSoundHandler();
    int bridge$getDebugFPS();
    TimerBridge bridge$getTimer();
    FontRendererBridge bridge$getFontRenderer();
}
