package dev.visuals.module;

import dev.visuals.Theme;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import org.joml.Vector3f;

public class Trails extends Module {
    private int style = 0; // 0 dust, 1 end rod, 2 flame
    private static final String[] N = {"Dust", "EndRod", "Flame"};
    private int tick;

    public Trails() { super("Trails", false); }
    @Override public void cycle() { style = (style + 1) % N.length; }
    @Override public String info() { return N[style]; }

    @Override public void onTick() {
        if (mc.player == null || mc.world == null) return;
        double dx = mc.player.getX() - mc.player.prevX, dz = mc.player.getZ() - mc.player.prevZ;
        if (dx * dx + dz * dz < 1e-4 && mc.player.isOnGround()) return;
        tick++;
        int c = Theme.color(tick * 6);
        Vector3f col = new Vector3f(((c >> 16) & 255) / 255f, ((c >> 8) & 255) / 255f, (c & 255) / 255f);
        double x = mc.player.getX(), y = mc.player.getY() + 0.1, z = mc.player.getZ();
        for (int i = 0; i < 2; i++) {
            double ox = (Math.random() - 0.5) * 0.3, oz = (Math.random() - 0.5) * 0.3;
            switch (style) {
                case 0 -> mc.world.addParticle(new DustParticleEffect(col, 1.0f), x + ox, y, z + oz, 0, 0, 0);
                case 1 -> mc.world.addParticle(ParticleTypes.END_ROD, x + ox, y, z + oz, 0, 0.01, 0);
                default -> mc.world.addParticle(ParticleTypes.FLAME, x + ox, y, z + oz, 0, 0.01, 0);
            }
        }
    }
}
