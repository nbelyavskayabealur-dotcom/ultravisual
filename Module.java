package dev.visuals.module;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;

public abstract class Module {
    protected static final MinecraftClient mc = MinecraftClient.getInstance();
    public final String name;
    public boolean enabled;

    protected Module(String name, boolean enabled) { this.name = name; this.enabled = enabled; }

    public void toggle() { enabled = !enabled; }
    /** Right click in GUI: switch the module's style. */
    public void cycle() {}
    /** Short text with the current style, shown in the GUI. */
    public String info() { return ""; }

    public void onTick() {}
    public void onHud(DrawContext ctx) {}
    public void onWorld(WorldRenderContext ctx) {}
    public void onAttack(Entity target) {}
}
