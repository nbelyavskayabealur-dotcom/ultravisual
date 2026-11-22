package dev.visuals.module;

/** Horizontal stretch of the 3D view, switched on the fly. */
public class AspectRatio extends Module {
    private int i = 1;
    private static final float[] F = {0.75f, 1f, 1.25f, 1.5f};
    private static final String[] N = {"Narrow", "Normal", "Wide", "Wider"};

    public AspectRatio() { super("AspectRatio", false); }
    @Override public void cycle() { i = (i + 1) % F.length; }
    @Override public String info() { return N[i]; }
    public float factor() { return F[i]; }
}
