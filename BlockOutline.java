package dev.visuals.module;

import dev.visuals.Theme;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;

public class BlockOutline extends Module {
    private boolean pulse = true;

    public BlockOutline() { super("BlockOutline", true); }
    @Override public void cycle() { pulse = !pulse; }
    @Override public String info() { return pulse ? "Pulse" : "Solid"; }

    /** Returns false to cancel the vanilla outline. */
    public boolean render(WorldRenderContext ctx, WorldRenderContext.BlockOutlineContext oc) {
        if (mc.world == null) return true;
        VoxelShape shape = oc.blockState().getOutlineShape(mc.world, oc.blockPos(), ShapeContext.of(oc.entity()));
        VertexConsumer vc = oc.vertexConsumer();
        MatrixStack.Entry e = ctx.matrixStack().peek();
        double ox = oc.blockPos().getX() - oc.cameraX();
        double oy = oc.blockPos().getY() - oc.cameraY();
        double oz = oc.blockPos().getZ() - oc.cameraZ();
        int c = Theme.color(0);
        float r = ((c >> 16) & 255) / 255f, g = ((c >> 8) & 255) / 255f, b = (c & 255) / 255f;
        float a = pulse ? 0.65f + 0.35f * MathHelper.sin(System.currentTimeMillis() / 250f) : 1f;
        shape.forEachEdge((x1, y1, z1, x2, y2, z2) -> {
            float dx = (float) (x2 - x1), dy = (float) (y2 - y1), dz = (float) (z2 - z1);
            float len = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
            dx /= len; dy /= len; dz /= len;
            vc.vertex(e.getPositionMatrix(), (float) (x1 + ox), (float) (y1 + oy), (float) (z1 + oz))
                    .color(r, g, b, a).normal(e.getNormalMatrix(), dx, dy, dz).next();
            vc.vertex(e.getPositionMatrix(), (float) (x2 + ox), (float) (y2 + oy), (float) (z2 + oz))
                    .color(r, g, b, a).normal(e.getNormalMatrix(), dx, dy, dz).next();
        });
        return false;
    }
}
