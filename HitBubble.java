package dev.visuals.module;

import dev.visuals.Draw;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import java.util.ArrayList;
import java.util.List;

public class HitBubble extends Module {
    private final List<double[]> bubbles = new ArrayList<>(); // x,y,z,startMs
    private static final long LIFE = 600;

    public HitBubble() { super("HitBubble", false); }

    @Override public void onAttack(Entity t) {
        bubbles.add(new double[]{t.getX(), t.getY() + t.getHeight() / 2, t.getZ(), System.currentTimeMillis()});
    }

    @Override public void onWorld(WorldRenderContext ctx) {
        long now = System.currentTimeMillis();
        bubbles.removeIf(b -> now - b[3] > LIFE);
        if (bubbles.isEmpty()) return;
        MatrixStack.Entry e = Draw.begin(ctx);
        var vc = Draw.lines(ctx);
        for (double[] b : bubbles) {
            float p = (now - (long) b[3]) / (float) LIFE;
            Draw.sphere(e, vc, b[0], b[1], b[2], 0.3 + p * 1.1, ModuleManager.HITCOLOR.color(), 1f - p);
        }
        Draw.end(ctx);
    }
}
