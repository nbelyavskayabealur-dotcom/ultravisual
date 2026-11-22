package dev.visuals.module;

import dev.visuals.Draw;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

/** Draws the predicted flight path of projectiles that are already in the air. */
public class Predictions extends Module {
    public Predictions() { super("Predictions", false); }

    @Override public void onWorld(WorldRenderContext ctx) {
        MatrixStack.Entry en = Draw.begin(ctx);
        var vc = Draw.lines(ctx);
        for (Entity e : mc.world.getEntities()) {
            double g;
            if (e instanceof PersistentProjectileEntity) g = e.hasNoGravity() ? 0 : 0.05;
            else if (e instanceof ThrownEntity) g = 0.03;
            else continue;
            Vec3d vel = e.getVelocity();
            if (vel.lengthSquared() < 0.01) continue;
            Vec3d pos = e.getPos();
            for (int i = 0; i < 120; i++) {
                Vec3d next = pos.add(vel);
                var hit = mc.world.raycast(new RaycastContext(pos, next, RaycastContext.ShapeType.COLLIDER,
                        RaycastContext.FluidHandling.NONE, e));
                Vec3d end = hit.getType() == HitResult.Type.MISS ? next : hit.getPos();
                Draw.line(en, vc, pos.x, pos.y, pos.z, end.x, end.y, end.z, 0xFFFFFF, 0.9f);
                if (hit.getType() != HitResult.Type.MISS) {
                    Draw.ring(en, vc, end.x, end.y + 0.02, end.z, 0.4, 16, 1f, true);
                    break;
                }
                pos = next;
                vel = vel.multiply(0.99).subtract(0, g, 0);
            }
        }
        Draw.end(ctx);
    }
}
