package dev.visuals.mixin;

import dev.visuals.module.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameOverlayRenderer.class)
public abstract class InGameOverlayRendererMixin {
    @Inject(method = "renderUnderwaterOverlay", at = @At("HEAD"), cancellable = true)
    private static void clientvisuals$water(MinecraftClient c, MatrixStack m, CallbackInfo ci) {
        if (ModuleManager.NOFLUID != null && ModuleManager.NOFLUID.enabled) ci.cancel();
    }

    @Inject(method = "renderFireOverlay", at = @At("HEAD"), cancellable = true)
    private static void clientvisuals$fire(MinecraftClient c, MatrixStack m, CallbackInfo ci) {
        if (ModuleManager.TWEAKS != null && ModuleManager.TWEAKS.enabled) ci.cancel();
    }
}
