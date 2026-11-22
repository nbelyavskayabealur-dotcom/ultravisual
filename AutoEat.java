package dev.visuals.module;

import net.minecraft.item.ItemStack;

/** Eats plain food from the hotbar when hunger drops below the threshold, then returns to your slot. */
public class AutoEat extends Module {
    private int t = 1;
    private static final int[] TH = {16, 14, 10, 6};
    private boolean eating;
    private int prevSlot;

    public AutoEat() { super("AutoEat", false); }
    @Override public void cycle() { t = (t + 1) % TH.length; }
    @Override public String info() { return "<= " + TH[t] / 2f + " bars"; }

    @Override public void toggle() { super.toggle(); if (!enabled) stop(); }

    private boolean isPlainFood(ItemStack s) {
        // skips golden apples, rotten flesh, stew, etc. (foods with effects)
        return s.getItem().isFood() && s.getItem().getFoodComponent().getStatusEffects().isEmpty();
    }

    private int findFood() {
        for (int i = 0; i < 9; i++) if (isPlainFood(mc.player.getInventory().getStack(i))) return i;
        return -1;
    }

    private void stop() {
        if (eating) {
            mc.options.useKey.setPressed(false);
            mc.player.getInventory().selectedSlot = prevSlot;
        }
        eating = false;
    }

    @Override public void onTick() {
        if (mc.currentScreen != null) { stop(); return; }
        int food = mc.player.getHungerManager().getFoodLevel();
        if (!eating) {
            if (food <= TH[t]) {
                int s = findFood();
                if (s >= 0) {
                    prevSlot = mc.player.getInventory().selectedSlot;
                    mc.player.getInventory().selectedSlot = s;
                    eating = true;
                }
            }
            return;
        }
        if (food >= 20 || !isPlainFood(mc.player.getInventory().getMainHandStack())) { stop(); return; }
        mc.options.useKey.setPressed(true);
    }
}
