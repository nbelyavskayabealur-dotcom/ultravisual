package dev.visuals.module;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import java.util.Set;

/** Filters vanilla particles. Our own effect particles are always allowed. */
public class Particles extends Module {
    private int mode = 0;
    private static final String[] N = {"Less", "None"};
    private static final Set<ParticleType<?>> ALLOWED = Set.of(ParticleTypes.DUST, ParticleTypes.CRIT,
            ParticleTypes.END_ROD, ParticleTypes.HEART, ParticleTypes.SNOWFLAKE, ParticleTypes.WHITE_ASH, ParticleTypes.FLAME);

    public Particles() { super("Particles", false); }
    @Override public void cycle() { mode = (mode + 1) % N.length; }
    @Override public String info() { return N[mode]; }

    public boolean shouldCancel(ParticleEffect p) {
        if (ALLOWED.contains(p.getType())) return false;
        return mode == 1 || Math.random() < 0.66;
    }
}
