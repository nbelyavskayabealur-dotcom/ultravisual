package dev.visuals.module;

/** Client-side weather. */
public class WorldCustomizer extends Module {
    private int i = 0;
    private static final String[] N = {"Clear", "Rain", "Thunder"};

    public WorldCustomizer() { super("WorldCustomizer", false); }
    @Override public void cycle() { i = (i + 1) % N.length; }
    @Override public String info() { return N[i]; }
    @Override public void onTick() {
        mc.world.setRainGradient(i == 0 ? 0f : 1f);
        mc.world.setThunderGradient(i == 2 ? 1f : 0f);
    }
}
