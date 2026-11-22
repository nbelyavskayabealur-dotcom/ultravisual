package dev.visuals.module;

import net.minecraft.client.option.Perspective;
import net.minecraft.util.math.MathHelper;

/** Hold the key: camera looks around independently of where the player faces. */
public class FreeLook extends Module {
    public boolean active;
    public float yaw, pitch;
    private Perspective before;

    public FreeLook() { super("FreeLook", true); }

    public void update(boolean held) {
        if (held && enabled && !active && mc.player != null) {
            active = true;
            yaw = mc.player.getYaw();
            pitch = mc.player.getPitch();
            before = mc.options.getPerspective();
            mc.options.setPerspective(Perspective.THIRD_PERSON_BACK);
        } else if ((!held || !enabled) && active) {
            active = false;
            if (before != null) mc.options.setPerspective(before);
        }
    }

    public void rotate(double dx, double dy) {
        yaw += (float) dx * 0.15f;
        pitch = MathHelper.clamp(pitch + (float) dy * 0.15f, -90f, 90f);
    }
}
