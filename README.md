# ClientVisuals (Fabric 1.20.1, client-only, cosmetic)

Build: copy gradle wrapper (gradlew, gradle/) from fabric-example-mod, then `./gradlew build`.
Keys: Right Shift - GUI | L - lock selected slot | Left Alt (hold) - free look | G - fast swap item to offhand | H - TapeMouse: record / loop / stop.
GUI: LMB toggle, RMB change style.
Command: /beam <x> <z> shows a huge beam at the coordinates, /beam clear removes it.

## Build without installing anything
Push this folder to a GitHub repository. The workflow in .github/workflows/build.yml builds the mod;
open the repo -> Actions -> latest run -> Artifacts -> clientvisuals-jar. Put the jar into .minecraft/mods
together with Fabric API 0.92.2+1.20.1.
