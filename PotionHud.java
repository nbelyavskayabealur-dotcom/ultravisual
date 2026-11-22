package dev.visuals.module;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffectInstance;

public class PotionHud extends Module {
    public PotionHud() { super("PotionHud", true); }

    @Override public void onHud(DrawContext ctx) {
        int y = 26;
        for (StatusEffectInstance e : mc.player.getStatusEffects()) {
            int secs = e.getDuration() / 20;
            String time = e.getDuration() < 0 ? "inf" : String.format("%d:%02d", secs / 60, secs % 60);
            String s = e.getEffectType().getName().getString() + " " + (e.getAmplifier() + 1) + "  " + time;
            ctx.fill(6, y - 1, 6 + mc.textRenderer.getWidth(s) + 6, y + 10, 0x80000000);
            ctx.drawTextWithShadow(mc.textRenderer, s, 9, y + 1, 0xFF000000 | e.getEffectType().getColor());
            y += 12;
        }
    }
}
