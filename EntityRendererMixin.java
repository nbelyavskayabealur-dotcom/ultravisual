package dev.visuals.mixin;

import dev.visuals.module.ModuleManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {
    @ModifyVariable(method = "renderLabelIfPresent", at = @At("HEAD"), argsOnly = true)
    private Text clientvisuals$recolor(Text text) {
        return (ModuleManager.NICK != null && ModuleManager.NICK.enabled) ? ModuleManager.NICK.apply(text) : text;
    }
}
