package dev.visuals.module;

import dev.visuals.mixin.SimpleOptionAccessor;

public class FullBright extends Module {
    private Double prev;

    public FullBright() { super("FullBright", false); }

    @Override public void toggle() {
        super.toggle();
        if (enabled) prev = mc.options.getGamma().getValue(); else restore();
    }
    @Override public void onTick() {
        ((SimpleOptionAccessor) (Object) mc.options.getGamma()).clientvisuals$setValue(16.0);
    }
    public void restore() {
        if (prev != null) {
            ((SimpleOptionAccessor) (Object) mc.options.getGamma()).clientvisuals$setValue(prev);
            prev = null;
        }
    }
}
