package dev.visuals.mixin;

import dev.visuals.module.ModuleManager;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundSystem.class)
public abstract class SoundSystemMixin {
    @Inject(method = "play(Lnet/minecraft/client/sound/SoundInstance;)V", at = @At("HEAD"), cancellable = true)
    private void clientvisuals$mute(SoundInstance s, CallbackInfo ci) {
        if (ModuleManager.SOUND != null && ModuleManager.SOUND.enabled && ModuleManager.SOUND.shouldMute(s)) ci.cancel();
    }
}
