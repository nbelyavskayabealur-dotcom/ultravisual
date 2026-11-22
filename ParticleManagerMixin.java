package dev.visuals.mixin;

import dev.visuals.module.ModuleManager;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParticleManager.class)
public abstract class ParticleManagerMixin {
    @Inject(method = "addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)Lnet/minecraft/client/particle/Particle;",
            at = @At("HEAD"), cancellable = true)
    private void clientvisuals$filter(ParticleEffect p, double x, double y, double z, double vx, double vy, double vz,
                                      CallbackInfoReturnable<Particle> cir) {
        if (ModuleManager.PARTICLES != null && ModuleManager.PARTICLES.enabled && ModuleManager.PARTICLES.shouldCancel(p))
            cir.setReturnValue(null);
    }
}
