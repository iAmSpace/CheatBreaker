package com.cheatbreaker.client.mixin.forge;

import com.cheatbreaker.client.ui.mainmenu.MainMenu;
import cpw.mods.fml.client.ExtendedServerListData;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.GuiAccessDenied;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.OldServerPinger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Mixin(value = FMLClientHandler.class, remap = false)
public abstract class MixinFMLClientHandler {
    @Shadow private Map<ServerData, ExtendedServerListData> serverDataTag;
    @Shadow public abstract void showGuiScreen(Object clientGuiElement);
    @Shadow private CountDownLatch playClientBlock;
    @Shadow private Minecraft client;

    @Shadow public abstract void setupServerList();

    @Shadow @Final private static CountDownLatch startupConnectionData;

    /**
     * @author iAmSpace
     * @reason CheatBreaker Main Menu override
     */
    @Overwrite
    public void connectToServer(GuiScreen screenIn, ServerData serverEntry) {
        GuiScreen screen = screenIn instanceof GuiMainMenu ? new MainMenu() : screenIn;

        ExtendedServerListData extendedData = this.serverDataTag.get(serverEntry);
        if (extendedData != null && extendedData.isBlocked)
            this.showGuiScreen(new GuiAccessDenied(screen, serverEntry));
        else
            this.showGuiScreen(new GuiConnecting(screen, this.client, serverEntry));

        this.playClientBlock = new CountDownLatch(1);
    }

    /**
     * @author iAmSpace
     * @reason CheatBreaker Main Menu override
     */
    @Overwrite
    public void connectToServerAtStartup(String host, int port)
    {
        this.setupServerList();
        OldServerPinger osp = new OldServerPinger();
        ServerData serverData = new ServerData("Command Line", host + ":" + port);
        try {
            osp.func_147224_a(serverData);
            startupConnectionData.await(30, TimeUnit.SECONDS);
        }
        catch (Exception e) {
            this.showGuiScreen(new GuiConnecting(new MainMenu(), this.client, host, port));
            return;
        }
        this.connectToServer(new MainMenu(), serverData);
    }
}
