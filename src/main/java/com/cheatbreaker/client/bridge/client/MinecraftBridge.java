package com.cheatbreaker.client.bridge.client;

import com.cheatbreaker.client.bridge.client.audio.SoundHandlerBridge;
import com.cheatbreaker.client.bridge.util.TimerBridge;

public interface MinecraftBridge {
    SoundHandlerBridge bridge$getSoundHandler();
    int bridge$getDebugFPS();
    TimerBridge bridge$getTimer();
}
