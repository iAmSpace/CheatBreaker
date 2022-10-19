package com.cheatbreaker.client.bridge.client.audio;

import paulscode.sound.SoundSystem;

public interface SoundManagerBridge {
    void playSound(String sound, float volume);
    SoundSystem bridge$getSoundSystem();
}
