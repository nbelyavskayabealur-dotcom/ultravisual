package dev.visuals.module;

import java.util.ArrayList;
import java.util.List;

public final class ModuleManager {
    public static final List<Module> MODULES = new ArrayList<>();
    public static NickColor NICK;
    public static Animations ANIM;
    public static HandView VIEW;
    public static AspectRatio ASPECT;
    public static Crosshair CROSS;
    public static BlockOutline BLOCK;
    public static HitColor HITCOLOR;
    public static LockSlot LOCK;
    public static TotemTracker TOTEMS;
    public static NoFluid NOFLUID;
    public static RenderTweaks TWEAKS;
    public static Particles PARTICLES;
    public static SoundController SOUND;
    public static FreeLook FREELOOK;
    public static FullBright BRIGHT;
    public static Waypoints WAY;
    public static FastSwap FASTSWAP;
    public static TapeMouse TAPE;

    public static void init() {
        // HUD
        add(new Watermark());
        add(new ArrayListHud());
        add(new Coordinates());
        add(new Keystrokes());
        add(new ArmorHud());
        add(new PotionHud());
        add(new TargetHud());
        add(new Cooldowns());
        add(new HealInfo());
        add(new PickupLogger());
        TOTEMS = add(new TotemTracker());
        // combat visuals
        add(new HitEffects());
        HITCOLOR = add(new HitColor());
        add(new HitSound());
        add(new HitBubble());
        add(new TargetEsp());
        add(new Hitboxes());
        add(new Predictions());
        NICK = add(new NickColor());
        // player cosmetics
        add(new Trails());
        add(new ChinaHat());
        add(new Nimb());
        add(new JumpCircles());
        ANIM = add(new Animations());
        VIEW = add(new HandView());
        // view
        CROSS = add(new Crosshair());
        BLOCK = add(new BlockOutline());
        ASPECT = add(new AspectRatio());
        add(new ItemHighlighter());
        FREELOOK = add(new FreeLook());
        BRIGHT = add(new FullBright());
        NOFLUID = add(new NoFluid());
        TWEAKS = add(new RenderTweaks());
        // world
        PARTICLES = add(new Particles());
        add(new WorldParticles());
        add(new TimeChanger());
        add(new WorldCustomizer());
        WAY = add(new Waypoints());
        // misc
        LOCK = add(new LockSlot());
        SOUND = add(new SoundController());
        add(new AutoRespawn());
        FASTSWAP = add(new FastSwap());
        add(new AutoEat());
        add(new MouseRepeat());
        TAPE = add(new TapeMouse());
    }

    private static <T extends Module> T add(T m) { MODULES.add(m); return m; }
}
