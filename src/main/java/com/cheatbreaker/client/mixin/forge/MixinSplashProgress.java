package com.cheatbreaker.client.mixin.forge;

import cpw.mods.fml.client.SplashProgress;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = SplashProgress.class, remap = false)
public class MixinSplashProgress {
    /**
     * @author iAmSpace
     * @reason Special CB Loading screen
     */
    @Overwrite(remap = false)
    public static void start() {

    }
}
