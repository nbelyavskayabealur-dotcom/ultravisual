package dev.visuals.module;

import net.minecraft.client.gui.DrawContext;
import java.util.HashSet;
import java.util.Set;

/** Locked inventory slots cannot be dropped or moved (see mixins). Toggle with the Lock slot key. */
public class LockSlot extends Module {
    private final Set<Integer> locked = new HashSet<>();

    public LockSlot() { super("LockSlot", true); }

    public boolean isLocked(int invIndex) { return enabled && locked.contains(invIndex); }

    public void toggleSelected() {
        int s = mc.player.getInventory().selectedSlot;
        if (!locked.remove(s)) locked.add(s);
    }

    @Override public void onHud(DrawContext ctx) {
        int x0 = ctx.getScaledWindowWidth() / 2 - 90, y = ctx.getScaledWindowHeight() - 22;
        for (int s : locked) if (s < 9) {
            ctx.fill(x0 + s * 20 + 1, y + 18, x0 + s * 20 + 19, y + 20, 0xFFFF3030);
            ctx.drawTextWithShadow(mc.textRenderer, "L", x0 + s * 20 + 12, y + 8, 0xFFFF5050);
        }
    }
}
