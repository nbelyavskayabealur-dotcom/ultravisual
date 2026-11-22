package dev.visuals.module;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import java.util.LinkedHashMap;
import java.util.Map;

public class TotemTracker extends Module {
    private final Map<String, Integer> pops = new LinkedHashMap<>();

    public TotemTracker() { super("TotemTracker", false); }
    @Override public void cycle() { pops.clear(); }
    @Override public String info() { return "RMB: reset"; }

    /** Called from ClientPlayNetworkHandlerMixin on a totem pop status. */
    public void pop(Entity e) {
        if (e instanceof PlayerEntity p) pops.merge(p.getName().getString(), 1, Integer::sum);
    }

    @Override public void onHud(DrawContext ctx) {
        int y = ctx.getScaledWindowHeight() / 2 - 20;
        for (var en : pops.entrySet()) {
            String s = en.getKey() + "  " + en.getValue() + " pops";
            ctx.drawTextWithShadow(mc.textRenderer, s, ctx.getScaledWindowWidth() - 8 - mc.textRenderer.getWidth(s), y, 0xFFFFD35A);
            y += 11;
        }
    }
}
