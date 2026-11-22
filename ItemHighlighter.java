package dev.visuals.module;

import dev.visuals.Draw;
import dev.visuals.Theme;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class ItemHighlighter extends Module {
    public ItemHighlighter() { super("ItemHighlighter", false); }

    @Override public void onWorld(WorldRenderContext ctx) {
        Draw.begin(ctx);
        var vc = Draw.lines(ctx);
        int n = 0;
        for (Entity e : mc.world.getEntities()) {
            if (!(e instanceof ItemEntity) || e.squaredDistanceTo(mc.player) > 32 * 32) continue;
            Vec3d p = Draw.pos(e, ctx.tickDelta());
            Box b = e.getBoundingBox().offset(p.x - e.getX(), p.y - e.getY(), p.z - e.getZ()).expand(0.08);
            float[] c = Draw.rgb(Theme.color(n++ * 25));
            WorldRenderer.drawBox(ctx.matrixStack(), vc, b, c[0], c[1], c[2], 1f);
        }
        Draw.end(ctx);
    }
}
