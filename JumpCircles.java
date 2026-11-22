package dev.visuals.module;

import dev.visuals.Draw;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.util.math.MatrixStack;
import java.util.ArrayList;
import java.util.List;

public class JumpCircles extends Module {
    private final List<double[]> circles = new ArrayList<>();
    private boolean wasGround;
    private static final long LIFE = 900;

    public JumpCircles() { super("JumpCircles", false); }

    @Override public void onTick() {
        boolean g = mc.player.isOnGround();
        if (wasGround && !g && mc.player.getVelocity().y > 0.1)
            circles.add(new double[]{mc.player.getX(), mc.player.getY() + 0.02, mc.player.getZ(), System.currentTimeMillis()});
        wasGround = g;
    }

    @Override public void onWorld(WorldRenderContext ctx) {
        long now = System.currentTimeMillis();
        circles.removeIf(c -> now - c[3] > LIFE);
        if (circles.isEmpty()) return;
        MatrixStack.Entry e = Draw.begin(ctx);
        var vc = Draw.lines(ctx);
        for (double[] c : circles) {
            float p = (now - (long) c[3]) / (float) LIFE;
            Draw.ring(e, vc, c[0], c[1], c[2], 0.2 + p * 1.5, 40, 1f - p, true);
            Draw.ring(e, vc, c[0], c[1], c[2], 0.1 + p * 1.0, 32, (1f - p) * 0.6f, true);
        }
        Draw.end(ctx);
    }
}
