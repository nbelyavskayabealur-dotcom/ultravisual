package dev.visuals;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import dev.visuals.gui.ClickGuiScreen;
import dev.visuals.module.Module;
import dev.visuals.module.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import org.lwjgl.glfw.GLFW;

public class VisualsClient implements ClientModInitializer {
    private static KeyBinding guiKey, lockKey, freeLookKey, swapKey, tapeKey;

    private static KeyBinding key(String id, int code) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding("key.clientvisuals." + id,
                InputUtil.Type.KEYSYM, code, "category.clientvisuals"));
    }

    @Override public void onInitializeClient() {
        MinecraftClient mc = MinecraftClient.getInstance();
        ModuleManager.init();

        guiKey = key("gui", GLFW.GLFW_KEY_RIGHT_SHIFT);
        lockKey = key("lock", GLFW.GLFW_KEY_L);
        freeLookKey = key("freelook", GLFW.GLFW_KEY_LEFT_ALT);
        swapKey = key("swap", GLFW.GLFW_KEY_G);
        tapeKey = key("tape", GLFW.GLFW_KEY_H);

        ClientTickEvents.END_CLIENT_TICK.register(c -> {
            while (guiKey.wasPressed()) c.setScreen(new ClickGuiScreen());
            if (c.player == null || c.world == null) { ModuleManager.FREELOOK.update(false); return; }
            while (lockKey.wasPressed()) ModuleManager.LOCK.toggleSelected();
            while (swapKey.wasPressed()) ModuleManager.FASTSWAP.swap();
            while (tapeKey.wasPressed()) ModuleManager.TAPE.pressKey();
            ModuleManager.FREELOOK.update(freeLookKey.isPressed() && c.currentScreen == null);
            for (Module m : ModuleManager.MODULES) if (m.enabled) m.onTick();
        });

        HudRenderCallback.EVENT.register((ctx, td) -> {
            if (mc.player == null || mc.options.hudHidden) return;
            for (Module m : ModuleManager.MODULES) if (m.enabled) m.onHud(ctx);
        });

        WorldRenderEvents.AFTER_ENTITIES.register(ctx -> {
            if (mc.world == null || mc.player == null) return;
            for (Module m : ModuleManager.MODULES) if (m.enabled) m.onWorld(ctx);
        });

        WorldRenderEvents.BLOCK_OUTLINE.register((wrc, oc) ->
                ModuleManager.BLOCK.enabled ? ModuleManager.BLOCK.render(wrc, oc) : true);

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hit) -> {
            if (world.isClient) {
                TargetTracker.set(entity);
                for (Module m : ModuleManager.MODULES) if (m.enabled) m.onAttack(entity);
            }
            return ActionResult.PASS;
        });

        // /beam <x> <z>   and   /beam clear
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registry) -> dispatcher.register(
                ClientCommandManager.literal("beam")
                        .then(ClientCommandManager.literal("clear").executes(c -> {
                            ModuleManager.WAY.clear();
                            c.getSource().sendFeedback(Text.literal("Beams cleared"));
                            return 1;
                        }))
                        .then(ClientCommandManager.argument("x", IntegerArgumentType.integer())
                                .then(ClientCommandManager.argument("z", IntegerArgumentType.integer())
                                        .executes(c -> {
                                            int x = IntegerArgumentType.getInteger(c, "x");
                                            int z = IntegerArgumentType.getInteger(c, "z");
                                            ModuleManager.WAY.add(x, z);
                                            ModuleManager.WAY.enabled = true;
                                            c.getSource().sendFeedback(Text.literal("Beam set at " + x + " " + z));
                                            return 1;
                                        })))));

        ClientLifecycleEvents.CLIENT_STOPPING.register(c -> ModuleManager.BRIGHT.restore());
    }
}
