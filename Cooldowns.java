package dev.visuals.module;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import java.util.HashMap;
import java.util.Map;

/** Shows remaining cooldown seconds over hotbar slots. */
public class Cooldowns extends Module {
    private final Map<Item, Float> last = new HashMap<>();
    private final Map<Item, Float> ticksLeft = new HashMap<>();

    public Cooldowns() { super("Cooldowns", true); }

    @Override public void onTick() {
        for (int i = 0; i < 9; i++) {
            Item it = mc.player.getInventory().getStack(i).getItem();
            float p = mc.player.getItemCooldownManager().getCooldownProgress(it, 0f);
            Float prev = last.get(it);
            if (p > 0 && prev != null && p < prev) ticksLeft.put(it, p / (prev - p)); // progress falls linearly
            else if (p <= 0) ticksLeft.remove(it);
            last.put(it, p);
        }
    }

    @Override public void onHud(DrawContext ctx) {
        int sw = ctx.getScaledWindowWidth(), sh = ctx.getScaledWindowHeight();
        for (int i = 0; i < 9; i++) {
            ItemStack s = mc.player.getInventory().getStack(i);
            Float t = ticksLeft.get(s.getItem());
            if (s.isEmpty() || t == null) continue;
            int x = sw / 2 - 90 + i * 20 + 10, y = sh - 16 - 3 + 4;
            ctx.drawCenteredTextWithShadow(mc.textRenderer, String.format("%.1f", t / 20f), x, y, 0xFFFFFF55);
        }
    }
}
