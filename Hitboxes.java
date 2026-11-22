package dev.visuals.module;

import dev.visuals.Draw;
import dev.visuals.Theme;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

/** Hitbox customizer: shape (box / ring / both) and filter (all living / players only). */
public class Hitboxes extends Module {
    private int i = 0;
    private static final String[] N = {"Box", "Ring", "Box+Ring", "Box (players)", "Ring (players)", "Both (players)"};

    public Hitboxes() { super("Hitboxes", false); }
    @Override public void cycle() { i = (i + 1) % N.length; }
    @Override public String info() { return N[i]; }

    @Override public void onWorld(WorldRenderContext ctx) {
        int shape = i % 3;
        boolean playersOnly = i >= 3;
        MatrixStack.Entry en = Draw.begin(ctx);
        var vc = Draw.lines(ctx);
        float[] c = Draw.rgb(Theme.color(0));
        for (Entity e : mc.world.getEntities()) {
            if (!(e instanceof LivingEntity) || e == mc.player) continue;
            if (playersOnly && !(e instanceof PlayerEntity)) continue;
            Vec3d p = Draw.pos(e, ctx.tickDelta());
            Box box = e.getBoundingBox().offset(p.x - e.getX(), p.y - e.getY(), p.z - e.getZ());
            if (shape != 1) WorldRenderer.drawBox(ctx.matrixStack(), vc, box, c[0], c[1], c[2], 1f);
            if (shape >= 1) Draw.ring(en, vc, p.x, p.y + 0.02, p.z, e.getWidth() * 0.75, 32, 1f, false);
        }
        Draw.end(ctx);
    }
}
