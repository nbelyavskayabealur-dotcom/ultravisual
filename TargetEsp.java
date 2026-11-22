package dev.visuals.module;

import dev.visuals.Draw;
import dev.visuals.TargetTracker;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

public class TargetEsp extends Module {
    public TargetEsp() { super("TargetEsp", true); }

    @Override public void onWorld(WorldRenderContext ctx) {
        LivingEntity t = TargetTracker.get();
        if (t == null) return;
        Vec3d p = Draw.pos(t, ctx.tickDelta());
        MatrixStack.Entry e = Draw.begin(ctx);
        var vc = Draw.lines(ctx);
        double time = System.currentTimeMillis() / 900.0;
        for (int k = 0; k < 3; k++) {
            double ph = (time + k / 3.0) % 1.0;
            double h = t.getHeight() * (0.5 + 0.5 * Math.sin(ph * Math.PI * 2));
            Draw.ring(e, vc, p.x, p.y + h, p.z, t.getWidth() * 0.9 + 0.1, 32, 1f, true);
        }
        Draw.end(ctx);
    }
}
