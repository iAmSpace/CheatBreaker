package com.cheatbreaker.client.bridge.client.audio;

import paulscode.sound.SoundSystem;

public interface SoundManagerBridge {
    void playSound(String s, float f);
    SoundSystem bridge$getSoundSystem();
}
