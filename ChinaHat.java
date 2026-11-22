package dev.visuals.module;

import dev.visuals.Draw;
import dev.visuals.Theme;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;

public class ChinaHat extends Module {
    public ChinaHat() { super("ChinaHat", false); }

    @Override public void onWorld(WorldRenderContext ctx) {
        if (mc.options.getPerspective().isFirstPerson()) return;
        Vec3d p = Draw.pos(mc.player, ctx.tickDelta());
        double base = p.y + mc.player.getHeight() + 0.02, apex = base + 0.32, rad = 0.65;
        MatrixStack.Entry e = Draw.begin(ctx);
        var vc = Draw.lines(ctx);
        Draw.ring(e, vc, p.x, base, p.z, rad, 40, 1f, true);
        Draw.ring(e, vc, p.x, base + 0.1, p.z, rad * 0.67, 32, 0.7f, true);
        Draw.ring(e, vc, p.x, base + 0.2, p.z, rad * 0.33, 24, 0.7f, true);
        for (int i = 0; i < 20; i++) {
            double a = Math.PI * 2 * i / 20;
            Draw.line(e, vc, p.x, apex, p.z, p.x + Math.cos(a) * rad, base, p.z + Math.sin(a) * rad, Theme.color(i * 18), 1f);
        }
        Draw.end(ctx);
    }
}
