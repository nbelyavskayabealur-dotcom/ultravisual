package dev.visuals;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/** Line-drawing helpers. Call begin(ctx) ... end(ctx) and draw in world coordinates. */
public final class Draw {
    public static MatrixStack.Entry begin(WorldRenderContext c) {
        MatrixStack m = c.matrixStack();
        Vec3d cam = c.camera().getPos();
        m.push();
        m.translate(-cam.x, -cam.y, -cam.z);
        return m.peek();
    }
    public static void end(WorldRenderContext c) {
        c.matrixStack().pop();
        if (c.consumers() instanceof VertexConsumerProvider.Immediate i) i.draw(RenderLayer.getLines());
    }
    public static VertexConsumer lines(WorldRenderContext c) { return c.consumers().getBuffer(RenderLayer.getLines()); }

    public static float[] rgb(int c) {
        return new float[]{((c >> 16) & 255) / 255f, ((c >> 8) & 255) / 255f, (c & 255) / 255f};
    }

    public static Vec3d pos(Entity e, float td) {
        return new Vec3d(MathHelper.lerp(td, e.prevX, e.getX()), MathHelper.lerp(td, e.prevY, e.getY()),
                MathHelper.lerp(td, e.prevZ, e.getZ()));
    }

    public static void line(MatrixStack.Entry e, VertexConsumer vc, double x1, double y1, double z1,
                            double x2, double y2, double z2, int rgb, float a) {
        float dx = (float) (x2 - x1), dy = (float) (y2 - y1), dz = (float) (z2 - z1);
        float len = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
        if (len < 1e-6f) return;
        dx /= len; dy /= len; dz /= len;
        float[] c = rgb(rgb);
        vc.vertex(e.getPositionMatrix(), (float) x1, (float) y1, (float) z1).color(c[0], c[1], c[2], a).normal(e.getNormalMatrix(), dx, dy, dz).next();
        vc.vertex(e.getPositionMatrix(), (float) x2, (float) y2, (float) z2).color(c[0], c[1], c[2], a).normal(e.getNormalMatrix(), dx, dy, dz).next();
    }

    /** Horizontal ring. rainbow: color shifts along the ring. */
    public static void ring(MatrixStack.Entry e, VertexConsumer vc, double cx, double cy, double cz, double rad,
                            int seg, float a, boolean rainbow) {
        for (int i = 0; i < seg; i++) {
            double a1 = Math.PI * 2 * i / seg, a2 = Math.PI * 2 * (i + 1) / seg;
            int col = Theme.color(rainbow ? i * 360.0 / seg * 2 : 0);
            line(e, vc, cx + Math.cos(a1) * rad, cy, cz + Math.sin(a1) * rad,
                    cx + Math.cos(a2) * rad, cy, cz + Math.sin(a2) * rad, col, a);
        }
    }

    public static void sphere(MatrixStack.Entry e, VertexConsumer vc, double cx, double cy, double cz, double rad,
                              int color, float a) {
        int seg = 24;
        for (int l = -2; l <= 2; l++) {
            double lat = l * Math.PI / 6;
            double rr = rad * Math.cos(lat), yy = rad * Math.sin(lat);
            for (int i = 0; i < seg; i++) {
                double a1 = Math.PI * 2 * i / seg, a2 = Math.PI * 2 * (i + 1) / seg;
                line(e, vc, cx + Math.cos(a1) * rr, cy + yy, cz + Math.sin(a1) * rr,
                        cx + Math.cos(a2) * rr, cy + yy, cz + Math.sin(a2) * rr, color, a);
            }
        }
        for (int k = 0; k < 6; k++) {
            double phi = k * Math.PI / 6;
            for (int i = 0; i < seg; i++) {
                double t1 = Math.PI * 2 * i / seg, t2 = Math.PI * 2 * (i + 1) / seg;
                line(e, vc, cx + Math.cos(t1) * Math.cos(phi) * rad, cy + Math.sin(t1) * rad, cz + Math.cos(t1) * Math.sin(phi) * rad,
                        cx + Math.cos(t2) * Math.cos(phi) * rad, cy + Math.sin(t2) * rad, cz + Math.cos(t2) * Math.sin(phi) * rad, color, a);
            }
        }
    }
}
