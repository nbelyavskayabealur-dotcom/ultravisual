package dev.visuals.mixin;

import dev.visuals.module.ModuleManager;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
    @Inject(method = "dropSelectedItem", at = @At("HEAD"), cancellable = true)
    private void clientvisuals$lockdrop(boolean entireStack, CallbackInfoReturnable<Boolean> cir) {
        ClientPlayerEntity p = (ClientPlayerEntity) (Object) this;
        if (ModuleManager.LOCK != null && ModuleManager.LOCK.isLocked(p.getInventory().selectedSlot)) cir.setReturnValue(false);
    }
}
