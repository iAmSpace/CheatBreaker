package com.cheatbreaker.mixin.client.audio;

import com.cheatbreaker.bridge.client.audio.SoundHandlerBridge;
import com.cheatbreaker.bridge.client.audio.SoundManagerBridge;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SoundHandler.class)
public class MixinSoundHandler implements SoundHandlerBridge {
    @Shadow @Final private SoundManager sndManager;

    public SoundManagerBridge bridge$getSoundManager() {
        return (SoundManagerBridge) this.sndManager;
    }
}
