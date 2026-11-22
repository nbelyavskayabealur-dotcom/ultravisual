package dev.visuals.module;

import dev.visuals.Theme;
import net.minecraft.client.gui.DrawContext;

public class Crosshair extends Module {
    private int style = 0; // 0 cross, 1 dot, 2 cross+dot
    private static final String[] N = {"Cross", "Dot", "Cross+Dot"};

    public Crosshair() { super("Crosshair", true); }
    @Override public void cycle() { style = (style + 1) % N.length; }
    @Override public String info() { return N[style]; }

    @Override public void onHud(DrawContext ctx) {
        if (mc.currentScreen != null || !mc.options.getPerspective().isFirstPerson()) return;
        int cx = ctx.getScaledWindowWidth() / 2, cy = ctx.getScaledWindowHeight() / 2;
        int col = Theme.argb(0), gap = 3, len = 5;
        if (style != 1) {
            line(ctx, cx - gap - len, cy, cx - gap, cy + 1, col);
            line(ctx, cx + gap, cy, cx + gap + len, cy + 1, col);
            line(ctx, cx, cy - gap - len, cx + 1, cy - gap, col);
            line(ctx, cx, cy + gap, cx + 1, cy + gap + len, col);
        }
        if (style != 0) line(ctx, cx - 1, cy - 1, cx + 1, cy + 1, col);
    }

    private void line(DrawContext c, int x1, int y1, int x2, int y2, int col) {
        c.fill(x1 - 1, y1 - 1, x2 + 1, y2 + 1, 0xFF000000); // outline
        c.fill(x1, y1, x2, y2, col);
    }
}
