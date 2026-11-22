package dev.visuals.module;

import dev.visuals.Theme;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class NickColor extends Module {
    public NickColor() { super("NickColor", true); }

    /** Called from EntityRendererMixin: gradient-colors the nametag. */
    public Text apply(Text original) {
        String s = original.getString();
        MutableText out = Text.empty();
        for (int i = 0; i < s.length(); i++) {
            out.append(Text.literal(String.valueOf(s.charAt(i)))
                    .setStyle(Style.EMPTY.withColor(Theme.color(i * 14))));
        }
        return out;
    }
}
