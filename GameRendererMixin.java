package dev.visuals.mixin;

import dev.visuals.module.ModuleManager;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(method = "getBasicProjectionMatrix", at = @At("RETURN"))
    private void clientvisuals$aspect(double fov, CallbackInfoReturnable<Matrix4f> cir) {
        if (ModuleManager.ASPECT != null && ModuleManager.ASPECT.enabled)
            cir.getReturnValue().scale(1f / ModuleManager.ASPECT.factor(), 1f, 1f);
    }

    @Inject(method = "tiltViewWhenHurt", at = @At("HEAD"), cancellable = true)
    private void clientvisuals$hurtcam(MatrixStack m, float td, CallbackInfo ci) {
        if (ModuleManager.TWEAKS != null && ModuleManager.TWEAKS.enabled) ci.cancel();
    }
}
