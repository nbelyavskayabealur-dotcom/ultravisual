package dev.visuals.mixin;

import dev.visuals.module.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(method = "changeLookDirection", at = @At("HEAD"), cancellable = true)
    private void clientvisuals$freelook(double dx, double dy, CallbackInfo ci) {
        if (ModuleManager.FREELOOK != null && ModuleManager.FREELOOK.active
                && (Object) this == MinecraftClient.getInstance().player) {
            ModuleManager.FREELOOK.rotate(dx, dy);
            ci.cancel();
        }
    }
}
