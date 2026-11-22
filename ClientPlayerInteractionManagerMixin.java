package dev.visuals.mixin;

import dev.visuals.module.LockSlot;
import dev.visuals.module.ModuleManager;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {
    @Inject(method = "clickSlot", at = @At("HEAD"), cancellable = true)
    private void clientvisuals$lock(int syncId, int slotId, int button, SlotActionType type, PlayerEntity player, CallbackInfo ci) {
        LockSlot l = ModuleManager.LOCK;
        if (l == null || !l.enabled) return;
        if (type == SlotActionType.SWAP && button >= 0 && button < 9 && l.isLocked(button)) { ci.cancel(); return; }
        ScreenHandler h = player.currentScreenHandler;
        if (slotId < 0 || slotId >= h.slots.size()) return;
        Slot s = h.getSlot(slotId);
        if (s.inventory == player.getInventory() && l.isLocked(s.getIndex())) ci.cancel();
    }
}
