package dev.visuals.module;

public class TimeChanger extends Module {
    private int i = 0;
    private static final String[] N = {"Day", "Noon", "Sunset", "Night", "Midnight"};
    private static final long[] T = {1000, 6000, 12500, 18000, 20000};

    public TimeChanger() { super("TimeChanger", false); }
    @Override public void cycle() { i = (i + 1) % N.length; }
    @Override public String info() { return N[i]; }
    @Override public void onTick() { mc.world.setTimeOfDay(T[i]); }
}
