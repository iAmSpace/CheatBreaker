package com.cheatbreaker.client.mixin.client;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.bridge.client.MinecraftBridge;
import com.cheatbreaker.client.bridge.client.audio.SoundHandlerBridge;
import com.cheatbreaker.client.ui.mainmenu.MainMenu;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.*;
import net.minecraft.client.resources.data.*;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.stream.IStream;
import net.minecraft.client.stream.NullStream;
import net.minecraft.client.stream.TwitchStream;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.IStatStringFormat;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.storage.ISaveFormat;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.OpenGLException;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements MinecraftBridge {
    @Shadow public abstract SoundHandler getSoundHandler();
    @Shadow private static int debugFPS;

    @Shadow public abstract void displayGuiScreen(GuiScreen guiScreenIn);

    @Shadow @Final private static Logger logger;

    @Shadow public int displayHeight;

    @Shadow public int displayWidth;

    @Shadow private boolean fullscreen;

    @Shadow public GameSettings gameSettings;

    @Shadow @Final public File mcDataDir;

    @Shadow protected abstract void updateDisplayMode() throws LWJGLException;

    @Shadow private IStream field_152353_at;

    @Shadow @Final private Multimap field_152356_J;

    @Shadow private Framebuffer framebufferMc;

    @Shadow public GuiAchievement guiAchievement;

    @Shadow @Final private IMetadataSerializer metadataSerializer_;

    @Shadow private ISaveFormat saveLoader;

    @Shadow private ResourcePackRepository mcResourcePackRepository;

    @Shadow private IReloadableResourceManager mcResourceManager;

    @Shadow private LanguageManager mcLanguageManager;

    @Shadow @Final private File fileResourcepacks;

    @Shadow public DefaultResourcePack mcDefaultResourcePack;

    @Shadow @Final private MinecraftSessionService field_152355_az;

    @Shadow private List defaultResourcePacks;

    @Shadow public TextureManager renderEngine;

    @Shadow private SkinManager field_152350_aA;

    @Shadow private SoundHandler mcSoundHandler;

    @Shadow private MusicTicker mcMusicTicker;

    @Shadow public FontRenderer fontRenderer;

    @Shadow public abstract boolean func_152349_b();

    @Shadow public FontRenderer standardGalacticFontRenderer;

    @Shadow public EntityRenderer entityRenderer;

    @Shadow public MouseHelper mouseHelper;

    @Shadow protected abstract void checkGLError(String p_71361_1_);

    @Shadow public RenderGlobal renderGlobal;

    @Shadow private TextureMap textureMapBlocks;

    @Shadow public EffectRenderer effectRenderer;

    @Shadow public WorldClient theWorld;

    @Shadow public GuiIngame ingameGUI;

    @Shadow private String serverName;

    @Shadow private int serverPort;

    @Shadow private ResourceLocation field_152354_ay;

    @Shadow public LoadingScreenRenderer loadingScreen;

    @Shadow public abstract void toggleFullscreen();

    @Shadow protected abstract ByteBuffer func_152340_a(InputStream p_152340_1_) throws IOException;

    @Shadow @Final private File fileAssets;

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

    /**
     * @author iAmSpace
     * @reason CheatBreaker init
     */
    @Overwrite
    public void startGame() throws LWJGLException {
        this.gameSettings = new GameSettings(Minecraft.getMinecraft(), this.mcDataDir);

        if (this.gameSettings.overrideHeight > 0 && this.gameSettings.overrideWidth > 0)
        {
            this.displayWidth = this.gameSettings.overrideWidth;
            this.displayHeight = this.gameSettings.overrideHeight;
        }

        if (this.fullscreen)
        {
            Display.setFullscreen(true);
            this.displayWidth = Display.getDisplayMode().getWidth();
            this.displayHeight = Display.getDisplayMode().getHeight();

            if (this.displayWidth <= 0)
            {
                this.displayWidth = 1;
            }

            if (this.displayHeight <= 0)
            {
                this.displayHeight = 1;
            }
        }
        else
        {
            Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight));
        }

        Display.setResizable(true);
        Display.setTitle("CheatBreaker 1.7.10");
        logger.info("LWJGL Version: " + Sys.getVersion());
        Util.EnumOS enumos = Util.getOSType();

        if (enumos != Util.EnumOS.OSX)
        {
            try
            {
                InputStream inputstream = this.mcDefaultResourcePack.func_152780_c(new ResourceLocation("icons/icon_16x16.png"));
                InputStream inputstream1 = this.mcDefaultResourcePack.func_152780_c(new ResourceLocation("icons/icon_32x32.png"));

                if (inputstream != null && inputstream1 != null)
                {
                    Display.setIcon(new ByteBuffer[] {
                            this.func_152340_a(inputstream), this.func_152340_a(inputstream1)
                    });
                }
            }
            catch (IOException ioexception)
            {
                logger.error("Couldn\'t set icon", ioexception);
            }
        }

        try
        {
            net.minecraftforge.client.ForgeHooksClient.createDisplay();
        }
        catch (LWJGLException lwjglexception)
        {
            logger.error("Couldn\'t set pixel format", lwjglexception);

            try
            {
                Thread.sleep(1000L);
            }
            catch (InterruptedException interruptedexception)
            {
                ;
            }

            if (this.fullscreen)
            {
                this.updateDisplayMode();
            }

            Display.create();
        }

        OpenGlHelper.initializeTextures();

        try
        {
            this.field_152353_at = new TwitchStream(Minecraft.getMinecraft(), (String) Iterables.getFirst(this.field_152356_J.get("twitch_access_token"), (Object)null));
        }
        catch (Throwable throwable)
        {
            this.field_152353_at = new NullStream(throwable);
            logger.error("Couldn\'t initialize twitch stream");
        }

        this.framebufferMc = new Framebuffer(this.displayWidth, this.displayHeight, true);
        this.framebufferMc.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
        this.guiAchievement = new GuiAchievement(Minecraft.getMinecraft());
        this.metadataSerializer_.registerMetadataSectionType(new TextureMetadataSectionSerializer(), TextureMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new FontMetadataSectionSerializer(), FontMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new PackMetadataSectionSerializer(), PackMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class);
        this.saveLoader = new AnvilSaveConverter(new File(this.mcDataDir, "saves"));
        this.mcResourcePackRepository = new ResourcePackRepository(this.fileResourcepacks, new File(this.mcDataDir, "server-resource-packs"), this.mcDefaultResourcePack, this.metadataSerializer_, this.gameSettings);
        this.mcResourceManager = new SimpleReloadableResourceManager(this.metadataSerializer_);
        this.mcLanguageManager = new LanguageManager(this.metadataSerializer_, this.gameSettings.language);
        this.mcResourceManager.registerReloadListener(this.mcLanguageManager);
        FMLClientHandler.instance().beginMinecraftLoading(Minecraft.getMinecraft(), this.defaultResourcePacks, this.mcResourceManager);
        this.renderEngine = new TextureManager(this.mcResourceManager);
        this.mcResourceManager.registerReloadListener(this.renderEngine);
        this.field_152350_aA = new SkinManager(this.renderEngine, new File(this.fileAssets, "skins"), this.field_152355_az);
        cpw.mods.fml.client.SplashProgress.drawVanillaScreen();
        this.mcSoundHandler = new SoundHandler(this.mcResourceManager, this.gameSettings);
        this.mcResourceManager.registerReloadListener(this.mcSoundHandler);
        this.mcMusicTicker = new MusicTicker(Minecraft.getMinecraft());
        this.fontRenderer = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii.png"), this.renderEngine, false);

        if (this.gameSettings.language != null)
        {
            this.fontRenderer.setUnicodeFlag(this.func_152349_b());
            this.fontRenderer.setBidiFlag(this.mcLanguageManager.isCurrentLanguageBidirectional());
        }

        this.standardGalacticFontRenderer = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii_sga.png"), this.renderEngine, false);
        this.mcResourceManager.registerReloadListener(this.fontRenderer);
        this.mcResourceManager.registerReloadListener(this.standardGalacticFontRenderer);
        this.mcResourceManager.registerReloadListener(new GrassColorReloadListener());
        this.mcResourceManager.registerReloadListener(new FoliageColorReloadListener());
        cpw.mods.fml.common.ProgressManager.ProgressBar bar= cpw.mods.fml.common.ProgressManager.push("Rendering Setup", 9, true);
        bar.step("Loading Render Manager");
        RenderManager.instance.itemRenderer = new ItemRenderer(Minecraft.getMinecraft());
        bar.step("Loading Entity Renderer");
        this.entityRenderer = new EntityRenderer(Minecraft.getMinecraft(), this.mcResourceManager);
        this.mcResourceManager.registerReloadListener(this.entityRenderer);
        AchievementList.openInventory.setStatStringFormatter(new IStatStringFormat()
        {
            private static final String __OBFID = "CL_00000639";
            /**
             * Formats the strings based on 'IStatStringFormat' interface.
             */
            public String formatString(String p_74535_1_)
            {
                try
                {
                    return String.format(p_74535_1_, GameSettings.getKeyDisplayString(Minecraft.getMinecraft().gameSettings.keyBindInventory.getKeyCode()));
                }
                catch (Exception exception)
                {
                    return "Error: " + exception.getLocalizedMessage();
                }
            }
        });
        bar.step("Loading GL properties");
        this.mouseHelper = new MouseHelper();
        this.checkGLError("Pre startup");
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glClearDepth(1.0D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
        GL11.glCullFace(GL11.GL_BACK);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        this.checkGLError("Startup");
        bar.step("Render Global instance");
        this.renderGlobal = new RenderGlobal(Minecraft.getMinecraft());
        bar.step("Building Blocks Texture");
        this.textureMapBlocks = new TextureMap(0, "textures/blocks", true);
        bar.step("Anisotropy and Mipmaps");
        this.textureMapBlocks.setAnisotropicFiltering(this.gameSettings.anisotropicFiltering);
        this.textureMapBlocks.setMipmapLevels(this.gameSettings.mipmapLevels);
        bar.step("Loading Blocks Texture");
        this.renderEngine.loadTextureMap(TextureMap.locationBlocksTexture, this.textureMapBlocks);
        bar.step("Loading Items Texture");
        this.renderEngine.loadTextureMap(TextureMap.locationItemsTexture, new TextureMap(1, "textures/items", true));
        bar.step("Viewport");
        GL11.glViewport(0, 0, this.displayWidth, this.displayHeight);
        this.effectRenderer = new EffectRenderer(this.theWorld, this.renderEngine);
        cpw.mods.fml.common.ProgressManager.pop(bar);
        FMLClientHandler.instance().finishMinecraftLoading();
        this.checkGLError("Post startup");
        this.ingameGUI = new net.minecraftforge.client.GuiIngameForge(Minecraft.getMinecraft());
        (CheatBreaker.getInstance() == null ? new CheatBreaker() : CheatBreaker.getInstance()).initialize();
        if (this.serverName != null)
        {
            FMLClientHandler.instance().connectToServerAtStartup(this.serverName, this.serverPort);
        }
        else
        {
            this.displayGuiScreen(new GuiMainMenu());
        }

        cpw.mods.fml.client.SplashProgress.clearVanillaResources(renderEngine, field_152354_ay);
        this.field_152354_ay = null;
        this.loadingScreen = new LoadingScreenRenderer(Minecraft.getMinecraft());

        FMLClientHandler.instance().onInitializationComplete();
        if (this.gameSettings.fullScreen && !this.fullscreen)
        {
            this.toggleFullscreen();
        }

        try
        {
            Display.setVSyncEnabled(this.gameSettings.enableVsync);
        }
        catch (OpenGLException openglexception)
        {
            this.gameSettings.enableVsync = false;
            this.gameSettings.saveOptions();
        }
    }
}
