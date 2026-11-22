package dev.visuals.gui;

import dev.visuals.Theme;
import dev.visuals.module.Module;
import dev.visuals.module.ModuleManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import java.util.List;

public class ClickGuiScreen extends Screen {
    private static final int W = 140, ROW = 15, HEAD = 18, PER_COL = 14, GAP = 6;

    public ClickGuiScreen() { super(Text.literal("ClientVisuals")); }
    @Override public boolean shouldPause() { return false; }

    private int cols() { return (ModuleManager.MODULES.size() + PER_COL - 1) / PER_COL; }
    private int x0() { return width / 2 - (cols() * (W + GAP) - GAP) / 2; }
    private int y0() { return height / 2 - (HEAD + ROW * PER_COL) / 2; }

    @Override public void render(DrawContext ctx, int mx, int my, float delta) {
        List<Module> all = ModuleManager.MODULES;
        for (int c = 0; c < cols(); c++) {
            int x = x0() + c * (W + GAP), y = y0();
            int n = Math.min(PER_COL, all.size() - c * PER_COL);
            ctx.fill(x, y, x + W, y + HEAD + ROW * n, 0xD0101014);
            ctx.fill(x, y, x + W, y + HEAD, Theme.argb(c * 40));
            ctx.drawTextWithShadow(textRenderer, c == 0 ? "ClientVisuals [" + Theme.NAMES[Theme.mode] + "]" : "", x + 5, y + 5, 0xFF101014);
            for (int r = 0; r < n; r++) {
                Module m = all.get(c * PER_COL + r);
                int ry = y + HEAD + r * ROW;
                if (mx >= x && mx <= x + W && my >= ry && my < ry + ROW) ctx.fill(x, ry, x + W, ry + ROW, 0x30FFFFFF);
                ctx.drawTextWithShadow(textRenderer, m.name, x + 5, ry + 4, m.enabled ? Theme.argb(ry) : 0xFF888888);
                String info = m.info();
                if (!info.isEmpty())
                    ctx.drawTextWithShadow(textRenderer, info, x + W - textRenderer.getWidth(info) - 4, ry + 4, 0xFF666666);
            }
        }
        ctx.drawCenteredTextWithShadow(textRenderer, "LMB toggle | RMB style | RMB on first header = theme",
                width / 2, y0() + HEAD + ROW * PER_COL + 6, 0xFF888888);
    }

    @Override public boolean mouseClicked(double mx, double my, int btn) {
        int c = (int) ((mx - x0()) / (W + GAP));
        if (mx < x0() || c < 0 || c >= cols() || (mx - x0()) - c * (W + GAP) > W) return false;
        int y = y0();
        if (my >= y && my < y + HEAD) { if (btn == 1 && c == 0) Theme.next(); return true; }
        int r = (int) ((my - y - HEAD) / ROW), idx = c * PER_COL + r;
        if (my >= y + HEAD && r >= 0 && r < PER_COL && idx < ModuleManager.MODULES.size()) {
            Module m = ModuleManager.MODULES.get(idx);
            if (btn == 0) m.toggle(); else if (btn == 1) m.cycle();
            return true;
        }
        return false;
    }
}
