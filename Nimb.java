package dev.visuals.module;

import dev.visuals.Draw;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;

public class Nimb extends Module {
    public Nimb() { super("Nimb", false); }

    @Override public void onWorld(WorldRenderContext ctx) {
        if (mc.options.getPerspective().isFirstPerson()) return;
        Vec3d p = Draw.pos(mc.player, ctx.tickDelta());
        double bob = Math.sin(System.currentTimeMillis() / 400.0) * 0.03;
        double y = p.y + mc.player.getHeight() + 0.3 + bob;
        MatrixStack.Entry e = Draw.begin(ctx);
        var vc = Draw.lines(ctx);
        Draw.ring(e, vc, p.x, y, p.z, 0.32, 36, 1f, true);
        Draw.ring(e, vc, p.x, y + 0.02, p.z, 0.30, 36, 0.7f, true);
        Draw.end(ctx);
    }
}
