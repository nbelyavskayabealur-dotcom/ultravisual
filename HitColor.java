package dev.visuals.module;

import dev.visuals.Theme;

/** Color used by hit effects / bubble / jump circles. When disabled the theme color is used. */
public class HitColor extends Module {
    private int i = 0;
    private static final String[] N = {"Red", "Green", "White", "Gold"};
    private static final int[] C = {0xFF4040, 0x40FF60, 0xFFFFFF, 0xFFC832};

    public HitColor() { super("HitColor", false); }
    @Override public void cycle() { i = (i + 1) % N.length; }
    @Override public String info() { return N[i]; }
    public int color() { return enabled ? C[i] : Theme.color(0); }
}
