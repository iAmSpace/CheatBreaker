package com.cheatbreaker.mixin.client.network;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.event.type.DisconnectEvent;
import com.cheatbreaker.client.event.type.PluginMessageEvent;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {
    @Inject(method = "handleCustomPayload", at = @At("HEAD"))
    public void impl$handleCustomPayload(S3FPacketCustomPayload payload, CallbackInfo callbackInfo) {
        CheatBreaker.getInstance().getEventBus().callEvent(new PluginMessageEvent(payload.func_149169_c(), payload.func_149168_d()));
    }

    @Inject(method = "onDisconnect", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/Minecraft;loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;)V"))
    public void impl$onDisconnect(IChatComponent chatComponent, CallbackInfo callbackInfo) {
        CheatBreaker.getInstance().getEventBus().callEvent(new DisconnectEvent());
    }
}
