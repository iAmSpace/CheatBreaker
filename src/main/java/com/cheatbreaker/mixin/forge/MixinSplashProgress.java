package com.cheatbreaker.mixin.forge;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.mainmenu.LoadingScreen;
import cpw.mods.fml.client.SplashProgress;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
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

    /**
     * @author iAmSpace
     * @reason Special CB Loading screen
     */
    @Overwrite(remap = false)
    public static void drawVanillaScreen() {
        CheatBreaker.cbLoadingScreen = new LoadingScreen(8);
        CheatBreaker.cbLoadingScreen.drawMenu(0.0f, 0.0f);
        CheatBreaker.cbLoadingScreen.newMessage("Sound Handler");
    }

    /**
     * @author iAmSpace
     * @reason Bypass if (!enabled) check
     */
    @Overwrite(remap = false)
    public static void clearVanillaResources(TextureManager renderEngine, ResourceLocation mojangLogo) {
        renderEngine.deleteTexture(mojangLogo);
    }
}
