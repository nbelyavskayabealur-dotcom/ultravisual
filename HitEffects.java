package dev.visuals.module;

import dev.visuals.Theme;
import net.minecraft.entity.Entity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import org.joml.Vector3f;

public class HitEffects extends Module {
    private int style = 0; // 0 dust burst, 1 crits, 2 hearts
    private static final String[] N = {"Dust", "Crit", "Hearts"};

    public HitEffects() { super("HitEffects", true); }
    @Override public void cycle() { style = (style + 1) % N.length; }
    @Override public String info() { return N[style]; }

    @Override public void onAttack(Entity t) {
        if (mc.world == null) return;
        double x = t.getX(), y = t.getY() + t.getHeight() * 0.5, z = t.getZ();
        int c = ModuleManager.HITCOLOR.color();
        Vector3f col = new Vector3f(((c >> 16) & 255) / 255f, ((c >> 8) & 255) / 255f, (c & 255) / 255f);
        for (int i = 0; i < 24; i++) {
            double vx = (Math.random() - 0.5) * 0.5, vy = Math.random() * 0.4, vz = (Math.random() - 0.5) * 0.5;
            switch (style) {
                case 0 -> mc.world.addParticle(new DustParticleEffect(col, 1.2f), x, y, z, vx, vy, vz);
                case 1 -> mc.world.addParticle(ParticleTypes.CRIT, x, y, z, vx, vy, vz);
                default -> { if (i % 4 == 0) mc.world.addParticle(ParticleTypes.HEART, x + vx, y + vy, z + vz, 0, 0, 0); }
            }
        }
    }
}
