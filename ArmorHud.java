package dev.visuals.module;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;

public class ArmorHud extends Module {
    public ArmorHud() { super("ArmorHud", true); }

    @Override public void onHud(DrawContext ctx) {
        int x = ctx.getScaledWindowWidth() / 2 - 40, y = ctx.getScaledWindowHeight() - 88;
        for (int i = 3; i >= 0; i--) {
            ItemStack s = mc.player.getInventory().armor.get(i);
            if (!s.isEmpty()) {
                ctx.drawItem(s, x, y);
                ctx.drawItemInSlot(mc.textRenderer, s, x, y);
                if (s.isDamageable()) {
                    int pct = (s.getMaxDamage() - s.getDamage()) * 100 / s.getMaxDamage();
                    ctx.drawCenteredTextWithShadow(mc.textRenderer, pct + "%", x + 8, y - 9, 0xFFFFFFFF);
                }
            }
            x += 20;
        }
    }
}
