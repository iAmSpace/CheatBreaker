package com.cheatbreaker.client.mixin.client;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.bridge.client.MinecraftBridge;
import com.cheatbreaker.client.bridge.client.audio.SoundHandlerBridge;
import com.cheatbreaker.client.event.type.ClickEvent;
import com.cheatbreaker.client.event.type.KeyboardEvent;
import com.cheatbreaker.client.event.type.TickEvent;
import com.cheatbreaker.client.ui.mainmenu.LoadingScreen;
import com.cheatbreaker.client.ui.mainmenu.MainMenu;
import com.cheatbreaker.client.ui.overlay.OverlayGui;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
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
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.stream.IStream;
import net.minecraft.client.stream.NullStream;
import net.minecraft.client.stream.TwitchStream;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.profiler.Profiler;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.IStatStringFormat;
import net.minecraft.util.*;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.storage.ISaveFormat;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
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
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

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

    @Shadow @Final public Profiler mcProfiler;

    @Shadow @Final private Queue field_152351_aB;

    @Shadow private int rightClickDelayTimer;

    @Shadow private boolean isGamePaused;

    @Shadow public PlayerControllerMP playerController;

    @Shadow public GuiScreen currentScreen;

    @Shadow public EntityClientPlayerMP thePlayer;

    @Shadow private int leftClickCounter;

    @Shadow private long systemTime;

    @Shadow public static long getSystemTime() {
        return 0L;
    }

    @Shadow public boolean inGameHasFocus;

    @Shadow public abstract void setIngameFocus();

    @Shadow private long field_83002_am;

    @Shadow public abstract void func_152348_aa();

    @Shadow public abstract void displayInGameMenu();

    @Shadow public abstract void refreshResources();

    @Shadow protected abstract void updateDebugProfilerName(int p_71383_1_);

    @Shadow public abstract NetHandlerPlayClient getNetHandler();

    @Shadow protected abstract void func_147116_af();

    @Shadow protected abstract void func_147121_ag();

    @Shadow protected abstract void func_147112_ai();

    @Shadow protected abstract void func_147115_a(boolean p_147115_1_);

    @Shadow private int joinPlayerCounter;

    @Shadow private NetworkManager myNetworkManager;

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

    private LoadingScreen cbLoadingScreen;

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
        this.cbLoadingScreen = new LoadingScreen(8); // CB
        this.cbLoadingScreen.drawMenu(0.0f, 0.0f); // CB
        this.cbLoadingScreen.newMessage("Sound Handler"); // CB
 //       cpw.mods.fml.client.SplashProgress.drawVanillaScreen();
        this.mcSoundHandler = new SoundHandler(this.mcResourceManager, this.gameSettings);
        this.mcResourceManager.registerReloadListener(this.mcSoundHandler);
        this.mcMusicTicker = new MusicTicker(Minecraft.getMinecraft());
        this.cbLoadingScreen.newMessage("Font Renderers"); // CB
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
//        cpw.mods.fml.common.ProgressManager.ProgressBar bar= cpw.mods.fml.common.ProgressManager.push("Rendering Setup", 9, true);
//        bar.step("Loading Render Manager");
        this.cbLoadingScreen.newMessage("Items"); // CB
        RenderManager.instance.itemRenderer = new ItemRenderer(Minecraft.getMinecraft());
        this.cbLoadingScreen.newMessage("Entities"); // CB
//        bar.step("Loading Entity Renderer");
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
//        bar.step("Loading GL properties");
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
//        bar.step("Render Global instance");
        this.cbLoadingScreen.newMessage("World"); // CB
        this.renderGlobal = new RenderGlobal(Minecraft.getMinecraft());
        this.cbLoadingScreen.newMessage("Blocks"); // CB
//        bar.step("Building Blocks Texture");
        this.textureMapBlocks = new TextureMap(0, "textures/blocks", true);
//        bar.step("Anisotropy and Mipmaps");
        this.textureMapBlocks.setAnisotropicFiltering(this.gameSettings.anisotropicFiltering);
        this.textureMapBlocks.setMipmapLevels(this.gameSettings.mipmapLevels);
//        bar.step("Loading Blocks Texture");
        this.renderEngine.loadTextureMap(TextureMap.locationBlocksTexture, this.textureMapBlocks);
//        bar.step("Loading Items Texture");
        this.renderEngine.loadTextureMap(TextureMap.locationItemsTexture, new TextureMap(1, "textures/items", true));
//        bar.step("Viewport");
        GL11.glViewport(0, 0, this.displayWidth, this.displayHeight);
        this.cbLoadingScreen.newMessage("Effects"); // CB
        this.effectRenderer = new EffectRenderer(this.theWorld, this.renderEngine);
//        cpw.mods.fml.common.ProgressManager.pop(bar);
        FMLClientHandler.instance().finishMinecraftLoading();
        this.checkGLError("Post startup");
        this.ingameGUI = new net.minecraftforge.client.GuiIngameForge(Minecraft.getMinecraft());
        this.cbLoadingScreen.newMessage("Mods");
        (CheatBreaker.getInstance() == null ? new CheatBreaker() : CheatBreaker.getInstance()).initialize();
        if (this.serverName != null)
        {
            FMLClientHandler.instance().connectToServerAtStartup(this.serverName, this.serverPort);
        }
        else
        {
            this.displayGuiScreen(new GuiMainMenu());
        }

        this.renderEngine.deleteTexture(this.field_152354_ay);
//        cpw.mods.fml.client.SplashProgress.clearVanillaResources(renderEngine, field_152354_ay);
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

    /**
     * @author iAmSpace
     * @reason Event handling
     */
    @Overwrite
    public void runTick()
    {
        this.mcProfiler.startSection("scheduledExecutables");
        Queue queue = this.field_152351_aB;

        synchronized (this.field_152351_aB)
        {
            while (!this.field_152351_aB.isEmpty())
            {
                ((FutureTask)this.field_152351_aB.poll()).run();
            }
        }

        this.mcProfiler.endSection();

        if (this.rightClickDelayTimer > 0)
        {
            --this.rightClickDelayTimer;
        }

        FMLCommonHandler.instance().onPreClientTick();

        this.mcProfiler.startSection("gui");

        if (!this.isGamePaused)
        {
            this.ingameGUI.updateTick();
        }

        this.mcProfiler.endStartSection("pick");
        this.entityRenderer.getMouseOver(1.0F);
        this.mcProfiler.endStartSection("gameMode");

        if (!this.isGamePaused && this.theWorld != null)
        {
            this.playerController.updateController();
        }

        this.mcProfiler.endStartSection("textures");

        if (!this.isGamePaused)
        {
            this.renderEngine.tick();
        }

        if (this.currentScreen == null && this.thePlayer != null)
        {
            if (this.thePlayer.getHealth() <= 0.0F)
            {
                this.displayGuiScreen((GuiScreen)null);
            }
            else if (this.thePlayer.isPlayerSleeping() && this.theWorld != null)
            {
                this.displayGuiScreen(new GuiSleepMP());
            }
        }
        else if (this.currentScreen != null && this.currentScreen instanceof GuiSleepMP && !this.thePlayer.isPlayerSleeping())
        {
            this.displayGuiScreen((GuiScreen)null);
        }

        if (this.currentScreen != null)
        {
            this.leftClickCounter = 10000;
        }

        CheatBreaker.getInstance().getEventBus().callEvent(new TickEvent());

        CrashReport crashreport;
        CrashReportCategory crashreportcategory;

        if (this.currentScreen != null)
        {
            try
            {
                this.currentScreen.handleInput();
                OverlayGui.getInstance().pollNotifications();
            }
            catch (Throwable throwable1)
            {
                crashreport = CrashReport.makeCrashReport(throwable1, "Updating screen events");
                crashreportcategory = crashreport.makeCategory("Affected screen");
                crashreportcategory.addCrashSectionCallable("Screen name", new Callable()
                {
                    private static final String __OBFID = "CL_00000640";
                    public String call()
                    {
                        return Minecraft.getMinecraft().currentScreen.getClass().getCanonicalName();
                    }
                });
                throw new ReportedException(crashreport);
            }

            if (this.currentScreen != null)
            {
                try
                {
                    this.currentScreen.updateScreen();
                }
                catch (Throwable throwable)
                {
                    crashreport = CrashReport.makeCrashReport(throwable, "Ticking screen");
                    crashreportcategory = crashreport.makeCategory("Affected screen");
                    crashreportcategory.addCrashSectionCallable("Screen name", new Callable()
                    {
                        private static final String __OBFID = "CL_00000642";
                        public String call()
                        {
                            return Minecraft.getMinecraft().currentScreen.getClass().getCanonicalName();
                        }
                    });
                    throw new ReportedException(crashreport);
                }
            }
        }

        if (this.currentScreen == null || this.currentScreen.allowUserInput)
        {
            OverlayGui.getInstance().pollNotifications();
            this.mcProfiler.endStartSection("mouse");
            int j;

            while (Mouse.next())
            {
                if (net.minecraftforge.client.ForgeHooksClient.postMouseEvent()) continue;

                j = Mouse.getEventButton();
                KeyBinding.setKeyBindState(j - 100, Mouse.getEventButtonState());

                if (Mouse.getEventButtonState()) {
                    KeyBinding.onTick(j - 100);
                } else {
                    CheatBreaker.getInstance().getEventBus().callEvent(new ClickEvent(j));
                }

                long k = getSystemTime() - this.systemTime;

                if (k <= 200L)
                {
                    int i = Mouse.getEventDWheel();

                    if (i != 0)
                    {
                        this.thePlayer.inventory.changeCurrentItem(i);

                        if (this.gameSettings.noclip)
                        {
                            if (i > 0)
                            {
                                i = 1;
                            }

                            if (i < 0)
                            {
                                i = -1;
                            }

                            this.gameSettings.noclipRate += (float)i * 0.25F;
                        }
                    }

                    if (this.currentScreen == null)
                    {
                        if (!this.inGameHasFocus && Mouse.getEventButtonState())
                        {
                            this.setIngameFocus();
                        }
                    }
                    else if (this.currentScreen != null)
                    {
                        this.currentScreen.handleMouseInput();
                    }
                }
                FMLCommonHandler.instance().fireMouseInput();
            }

            if (this.leftClickCounter > 0)
            {
                --this.leftClickCounter;
            }

            this.mcProfiler.endStartSection("keyboard");
            boolean flag;

            while (Keyboard.next())
            {
                KeyBinding.setKeyBindState(Keyboard.getEventKey(), Keyboard.getEventKeyState());

                if (Keyboard.getEventKeyState())
                {
                    KeyBinding.onTick(Keyboard.getEventKey());
                }

                if (this.field_83002_am > 0L)
                {
                    if (getSystemTime() - this.field_83002_am >= 6000L)
                    {
                        throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
                    }

                    if (!Keyboard.isKeyDown(46) || !Keyboard.isKeyDown(61))
                    {
                        this.field_83002_am = -1L;
                    }
                }
                else if (Keyboard.isKeyDown(46) && Keyboard.isKeyDown(61))
                {
                    this.field_83002_am = getSystemTime();
                }

                this.func_152348_aa();

                if (Keyboard.getEventKeyState())
                {
                    CheatBreaker.getInstance().getEventBus().callEvent(new KeyboardEvent(Keyboard.getEventKey()));

                    if (Keyboard.isKeyDown(42) && Keyboard.getEventKey() == 15) {
                        this.displayGuiScreen(OverlayGui.createInstance(this.currentScreen));
                    }

                    if (Keyboard.getEventKey() == 62 && this.entityRenderer != null)
                    {
                        this.entityRenderer.deactivateShader();
                    }

                    if (this.currentScreen != null)
                    {
                        this.currentScreen.handleKeyboardInput();
                    }
                    else
                    {
                        if (Keyboard.getEventKey() == 1)
                        {
                            this.displayInGameMenu();
                        }

                        if (Keyboard.getEventKey() == 31 && Keyboard.isKeyDown(61))
                        {
                            this.refreshResources();
                        }

                        if (Keyboard.getEventKey() == 20 && Keyboard.isKeyDown(61))
                        {
                            this.refreshResources();
                        }

                        if (Keyboard.getEventKey() == 33 && Keyboard.isKeyDown(61))
                        {
                            flag = Keyboard.isKeyDown(42) | Keyboard.isKeyDown(54);
                            this.gameSettings.setOptionValue(GameSettings.Options.RENDER_DISTANCE, flag ? -1 : 1);
                        }

                        if (Keyboard.getEventKey() == 30 && Keyboard.isKeyDown(61))
                        {
                            this.renderGlobal.loadRenderers();
                        }

                        if (Keyboard.getEventKey() == 35 && Keyboard.isKeyDown(61))
                        {
                            this.gameSettings.advancedItemTooltips = !this.gameSettings.advancedItemTooltips;
                            this.gameSettings.saveOptions();
                        }

                        if (Keyboard.getEventKey() == 48 && Keyboard.isKeyDown(61))
                        {
                            RenderManager.debugBoundingBox = !RenderManager.debugBoundingBox;
                        }

                        if (Keyboard.getEventKey() == 25 && Keyboard.isKeyDown(61))
                        {
                            this.gameSettings.pauseOnLostFocus = !this.gameSettings.pauseOnLostFocus;
                            this.gameSettings.saveOptions();
                        }

                        if (Keyboard.getEventKey() == 59)
                        {
                            this.gameSettings.hideGUI = !this.gameSettings.hideGUI;
                        }

                        if (Keyboard.getEventKey() == 61)
                        {
                            this.gameSettings.showDebugInfo = !this.gameSettings.showDebugInfo;
                            this.gameSettings.showDebugProfilerChart = GuiScreen.isShiftKeyDown();
                        }

                        if (this.gameSettings.keyBindTogglePerspective.isPressed())
                        {
                            ++this.gameSettings.thirdPersonView;

                            if (this.gameSettings.thirdPersonView > 2)
                            {
                                this.gameSettings.thirdPersonView = 0;
                            }
                        }

                        if (this.gameSettings.keyBindSmoothCamera.isPressed())
                        {
                            this.gameSettings.smoothCamera = !this.gameSettings.smoothCamera;
                        }
                    }

                    if (this.gameSettings.showDebugInfo && this.gameSettings.showDebugProfilerChart)
                    {
                        if (Keyboard.getEventKey() == 11)
                        {
                            this.updateDebugProfilerName(0);
                        }

                        for (j = 0; j < 9; ++j)
                        {
                            if (Keyboard.getEventKey() == 2 + j)
                            {
                                this.updateDebugProfilerName(j + 1);
                            }
                        }
                    }
                }
                FMLCommonHandler.instance().fireKeyInput();
            }

            for (j = 0; j < 9; ++j)
            {
                if (this.gameSettings.keyBindsHotbar[j].isPressed())
                {
                    this.thePlayer.inventory.currentItem = j;
                }
            }

            flag = this.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN;

            while (this.gameSettings.keyBindInventory.isPressed())
            {
                if (this.playerController.func_110738_j())
                {
                    this.thePlayer.func_110322_i();
                }
                else
                {
                    this.getNetHandler().addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                    this.displayGuiScreen(new GuiInventory(this.thePlayer));
                }
            }

            while (this.gameSettings.keyBindDrop.isPressed())
            {
                this.thePlayer.dropOneItem(GuiScreen.isCtrlKeyDown());
            }

            while (this.gameSettings.keyBindChat.isPressed() && flag)
            {
                this.displayGuiScreen(new GuiChat());
            }

            if (this.currentScreen == null && this.gameSettings.keyBindCommand.isPressed() && flag)
            {
                this.displayGuiScreen(new GuiChat("/"));
            }

            if (this.thePlayer.isUsingItem())
            {
                if (!this.gameSettings.keyBindUseItem.getIsKeyPressed())
                {
                    this.playerController.onStoppedUsingItem(this.thePlayer);
                }

                label391:

                while (true)
                {
                    if (!this.gameSettings.keyBindAttack.isPressed())
                    {
                        while (this.gameSettings.keyBindUseItem.isPressed())
                        {
                            ;
                        }

                        while (true)
                        {
                            if (this.gameSettings.keyBindPickBlock.isPressed())
                            {
                                continue;
                            }

                            break label391;
                        }
                    }
                }
            }
            else
            {
                while (this.gameSettings.keyBindAttack.isPressed())
                {
                    this.func_147116_af();
                }

                while (this.gameSettings.keyBindUseItem.isPressed())
                {
                    this.func_147121_ag();
                }

                while (this.gameSettings.keyBindPickBlock.isPressed())
                {
                    this.func_147112_ai();
                }
            }

            if (this.gameSettings.keyBindUseItem.getIsKeyPressed() && this.rightClickDelayTimer == 0 && !this.thePlayer.isUsingItem())
            {
                this.func_147121_ag();
            }

            this.func_147115_a(this.currentScreen == null && this.gameSettings.keyBindAttack.getIsKeyPressed() && this.inGameHasFocus);
        }

        if (this.theWorld != null)
        {
            if (this.thePlayer != null)
            {
                ++this.joinPlayerCounter;

                if (this.joinPlayerCounter == 30)
                {
                    this.joinPlayerCounter = 0;
                    this.theWorld.joinEntityInSurroundings(this.thePlayer);
                }
            }

            this.mcProfiler.endStartSection("gameRenderer");

            if (!this.isGamePaused)
            {
                this.entityRenderer.updateRenderer();
            }

            this.mcProfiler.endStartSection("levelRenderer");

            if (!this.isGamePaused)
            {
                this.renderGlobal.updateClouds();
            }

            this.mcProfiler.endStartSection("level");

            if (!this.isGamePaused)
            {
                if (this.theWorld.lastLightningBolt > 0)
                {
                    --this.theWorld.lastLightningBolt;
                }

                this.theWorld.updateEntities();
            }
        }

        if (!this.isGamePaused)
        {
            this.mcMusicTicker.update();
            this.mcSoundHandler.update();
        }

        if (this.theWorld != null)
        {
            if (!this.isGamePaused)
            {
                this.theWorld.setAllowedSpawnTypes(this.theWorld.difficultySetting != EnumDifficulty.PEACEFUL, true);

                try
                {
                    this.theWorld.tick();
                }
                catch (Throwable throwable2)
                {
                    crashreport = CrashReport.makeCrashReport(throwable2, "Exception in world tick");

                    if (this.theWorld == null)
                    {
                        crashreportcategory = crashreport.makeCategory("Affected level");
                        crashreportcategory.addCrashSection("Problem", "Level is null!");
                    }
                    else
                    {
                        this.theWorld.addWorldInfoToCrashReport(crashreport);
                    }

                    throw new ReportedException(crashreport);
                }
            }

            this.mcProfiler.endStartSection("animateTick");

            if (!this.isGamePaused && this.theWorld != null)
            {
                this.theWorld.doVoidFogParticles(MathHelper.floor_double(this.thePlayer.posX), MathHelper.floor_double(this.thePlayer.posY), MathHelper.floor_double(this.thePlayer.posZ));
            }

            this.mcProfiler.endStartSection("particles");

            if (!this.isGamePaused)
            {
                this.effectRenderer.updateEffects();
            }
        }
        else if (this.myNetworkManager != null)
        {
            this.mcProfiler.endStartSection("pendingConnection");
            this.myNetworkManager.processReceivedPackets();
        }

        FMLCommonHandler.instance().onPostClientTick();

        this.mcProfiler.endSection();
        this.systemTime = getSystemTime();
    }
}
