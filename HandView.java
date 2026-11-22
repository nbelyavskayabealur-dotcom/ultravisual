package dev.visuals.module;

import net.minecraft.client.util.math.MatrixStack;

/** First-person hand/item position and size presets. */
public class HandView extends Module {
    private int i = 0;
    private static final String[] N = {"Low", "Far right", "Tiny", "High"};
    private static final float[][] P = { // x, y, z, scale
            {0f, -0.12f, 0f, 1f}, {0.2f, -0.05f, -0.1f, 1f}, {0.1f, -0.1f, -0.1f, 0.6f}, {0f, 0.1f, 0f, 1f}};

    public HandView() { super("HandView", false); }
    @Override public void cycle() { i = (i + 1) % N.length; }
    @Override public String info() { return N[i]; }

    public void apply(MatrixStack m) {
        float[] p = P[i];
        m.translate(p[0], p[1], p[2]);
        m.scale(p[3], p[3], p[3]);
    }
}
