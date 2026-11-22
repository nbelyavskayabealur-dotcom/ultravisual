package dev.visuals.module;

import net.minecraft.client.sound.SoundInstance;

public class SoundController extends Module {
    private int i = 0;
    private static final String[] N = {"No steps", "No ambient/weather", "No mobs"};

    public SoundController() { super("SoundController", false); }
    @Override public void cycle() { i = (i + 1) % N.length; }
    @Override public String info() { return N[i]; }

    public boolean shouldMute(SoundInstance s) {
        String p = s.getId().getPath();
        return switch (i) {
            case 0 -> p.contains("step");
            case 1 -> p.contains("ambient") || p.contains("weather");
            default -> p.startsWith("entity.") && !p.startsWith("entity.player") && !p.contains("experience");
        };
    }
}
