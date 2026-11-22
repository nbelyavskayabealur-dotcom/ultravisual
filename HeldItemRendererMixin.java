package dev.visuals.mixin;

import dev.visuals.module.ModuleManager;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {
    @Inject(method = "renderFirstPersonItem", at = @At("HEAD"))
    private void clientvisuals$anim(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand,
                                    float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices,
                                    VertexConsumerProvider vcp, int light, CallbackInfo ci) {
        if (hand != Hand.MAIN_HAND || item.isEmpty()) return;
        if (ModuleManager.VIEW != null && ModuleManager.VIEW.enabled) ModuleManager.VIEW.apply(matrices);
        if (ModuleManager.ANIM != null && ModuleManager.ANIM.enabled) ModuleManager.ANIM.apply(matrices, swingProgress);
    }
}
