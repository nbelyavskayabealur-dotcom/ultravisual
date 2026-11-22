package dev.visuals.mixin;

import dev.visuals.module.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
    // TAIL: runs only on the main thread (the method re-schedules itself first)
    @Inject(method = "onEntityStatus", at = @At("TAIL"))
    private void clientvisuals$totem(EntityStatusS2CPacket packet, CallbackInfo ci) {
        if (ModuleManager.TOTEMS == null || !ModuleManager.TOTEMS.enabled || packet.getStatus() != 35) return;
        ClientWorld w = MinecraftClient.getInstance().world;
        if (w != null) ModuleManager.TOTEMS.pop(packet.getEntity(w));
    }
}
