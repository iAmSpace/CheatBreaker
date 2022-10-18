package com.cheatbreaker.client.mixin.client.audio;

import com.cheatbreaker.client.bridge.client.audio.SoundManagerBridge;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import paulscode.sound.SoundSystem;

import java.net.URL;
import java.util.UUID;

@Mixin(SoundManager.class)
public class MixinSoundManager implements SoundManagerBridge {
//    @Shadow private SoundManager.SoundSystemStarterThread sndSystem;
    @Shadow private boolean loaded;

    @Shadow
    private static URL getURLForSoundResource(ResourceLocation p_148612_0_) {
        return null;
    }

    public void playSound(String string, float f) {
        if (this.loaded) {
            ResourceLocation resourceLocation = new ResourceLocation("client/sound/" + string + ".ogg");
            String string2 = UUID.randomUUID().toString();
//            this.sndSystem.newStreamingSource(false, string2, getURLForSoundResource(resourceLocation), resourceLocation.toString(), false, 0.0f, 0.0f, 0.0f, 0, SoundSystemConfig.getDefaultRolloff());
//            this.sndSystem.setPitch(string2, 1.0f);
//            this.sndSystem.setVolume(string2, f);
//            this.sndSystem.play(string2);
        }
    }

    public SoundSystem bridge$getSoundSystem() {
        SoundSystem returnable = null;
/*        try {
            SoundHandler soundHandler = Minecraft.getMinecraft().getSoundHandler();
            Class<?> oclass = Class.forName("net.minecraft.client.audio.SoundManager");
            Object object = oclass.getField("sndManager").get(soundHandler);
            returnable = (SoundSystem) oclass.getField("sndSystem").get(object);
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }*/
        return returnable;
    }
}
