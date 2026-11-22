package dev.visuals.module;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class Animations extends Module {
    private int style = 0;
    private static final String[] N = {"Slash", "Spin", "Small"};

    public Animations() { super("Animations", true); }
    @Override public void cycle() { style = (style + 1) % N.length; }
    @Override public String info() { return N[style]; }

    /** Called at the head of HeldItemRenderer.renderFirstPersonItem (main hand). */
    public void apply(MatrixStack m, float swing) {
        float s = MathHelper.sin(swing * (float) Math.PI);
        switch (style) {
            case 0 -> {
                m.translate(0.05, -0.05, 0);
                m.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-s * 35f));
                m.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(s * 15f));
            }
            case 1 -> {
                m.translate(0, -0.15, 0);
                m.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(swing * 360f));
            }
            default -> {
                m.translate(0.1, -0.1, -0.1);
                m.scale(0.75f, 0.75f, 0.75f);
            }
        }
    }
}
