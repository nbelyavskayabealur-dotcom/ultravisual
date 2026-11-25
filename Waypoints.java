package dev.visuals.module;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import java.util.ArrayList;
import java.util.List;

/** /beam <x> <z> shows a huge light beam at that place. /beam clear removes them. */
public class Waypoints extends Module {
    private final List<int[]> points = new ArrayList<>();

    public Waypoints() { super("Waypoints", true); }

    public void add(int x, int z) { points.add(new int[]{x, z}); }
    public void clear() { points.clear(); }
    @Override public void cycle() { clear(); }
    @Override public String info() { return points.size() + " (RMB clear)"; }

    @Override public void onWorld(WorldRenderContext ctx) {
        if (points.isEmpty()) return;
        Vec3d cam = ctx.camera().getPos();
        MatrixStack m = ctx.matrixStack();
        for (int[] p : points) {
            m.push();
            m.translate(p[0] + 0.5 - cam.x, -cam.y, p[1] + 0.5 - cam.z);
            m.scale(6f, 1f, 6f);          // fat beam
            m.translate(-0.5, 0, -0.5);
                        BeaconBlockEntityRenderer.renderBeam(m, ctx.consumers(), BeaconBlockEntityRenderer.BEAM_TEXTURE,
                    ctx.tickDelta(), 1.0f, mc.world.getTime(), -64, 520, new float[]{0.7f, 0.4f, 1f}, 0.2f, 0.25f);
            m.pop();
        }
    }

    @Override public void onHud(DrawContext ctx) {
        int y = 40;
        for (int[] p : points) {
            double d = Math.sqrt(Math.pow(p[0] + 0.5 - mc.player.getX(), 2) + Math.pow(p[1] + 0.5 - mc.player.getZ(), 2));
            String s = "Beam " + p[0] + " " + p[1] + "  -  " + (int) d + "m";
            ctx.drawCenteredTextWithShadow(mc.textRenderer, s, ctx.getScaledWindowWidth() / 2, y, 0xFFC9A0FF);
            y += 11;
        }
    }
}
