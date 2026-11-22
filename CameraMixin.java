package dev.visuals.mixin;

import dev.visuals.module.ModuleManager;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Camera.class)
public abstract class CameraMixin {
    @ModifyArgs(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;setRotation(FF)V"))
    private void clientvisuals$rot(Args args) {
        if (ModuleManager.FREELOOK != null && ModuleManager.FREELOOK.active) {
            args.set(0, ModuleManager.FREELOOK.yaw);
            args.set(1, ModuleManager.FREELOOK.pitch);
        }
    }
}
