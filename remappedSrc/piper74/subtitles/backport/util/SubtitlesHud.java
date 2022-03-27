package piper74.subtitles.backport.util;

import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.Window;
import net.minecraft.entity.attribute.AttributeModifier;
import net.minecraft.screen.EnchantingScreenHandler;
import net.minecraft.util.math.Vec3d;
import piper74.subtitles.backport.SubtitlesMod;

import java.util.Iterator;
import java.util.List;

@Environment(EnvType.CLIENT)
public class SubtitlesHud extends DrawableHelper {
    private final MinecraftClient client;
    private final List<SubtitlesHud.SubtitleEntry> entries = Lists.newArrayList();
    private static SubtitlesHud subtitlesHudStatic;
    private boolean enabled;

    public SubtitlesHud(MinecraftClient client) {
         this.client = client;
         subtitlesHudStatic = this;
    }

    public void render(Window window) {
        if(SubtitlesMod.config.enabled && !this.enabled)
            this.enabled = true;
        else if (!SubtitlesMod.config.enabled && this.enabled)
            this.enabled = false;

        if (this.enabled && !entries.isEmpty()) {
            EnchantingScreenHandler.pushMatrix();
            EnchantingScreenHandler.enableBlend();
            EnchantingScreenHandler.blendFuncSeparate(770, 771, 1, 0);
            Vec3d vec3d = new Vec3d(this.client.player.x, this.client.player.y + (double)this.client.player.getEyeHeight(), this.client.player.z);
            Vec3d vec3d2 = (new Vec3d(0.0D, 0.0D, -1.0D)).rotateX(-this.client.player.pitch * 0.017453292F).rotateY(-this.client.player.yaw * 0.017453292F);
            Vec3d vec3d3 = (new Vec3d(0.0D, 1.0D, 0.0D)).rotateX(-this.client.player.pitch * 0.017453292F).rotateY(-this.client.player.yaw * 0.017453292F);
            Vec3d vec3d4 = vec3d2.crossProduct(vec3d3);
            int i = 0;
            int j = 0;
            Iterator iterator = entries.iterator();

            SubtitlesHud.SubtitleEntry subtitleEntry2;
            while(iterator.hasNext()) {
                subtitleEntry2 = (SubtitlesHud.SubtitleEntry)iterator.next();
                if (subtitleEntry2.getTime() + 3000L <= MinecraftClient.getTime()) {
                    iterator.remove();
                } else {
                    j = Math.max(j, this.client.textRenderer.getStringWidth(subtitleEntry2.getText()));
                }
            }

            j += this.client.textRenderer.getStringWidth("<") + this.client.textRenderer.getStringWidth(" ") + this.client.textRenderer.getStringWidth(">") + this.client.textRenderer.getStringWidth(" ");

            for(iterator = entries.iterator(); iterator.hasNext(); ++i) {
                subtitleEntry2 = (SubtitlesHud.SubtitleEntry)iterator.next();
                //int k = 255;
                String string = subtitleEntry2.getText();
                Vec3d vec3d5 = subtitleEntry2.getPosition().subtract(vec3d).normalize();
                double d = -vec3d4.dotProduct(vec3d5);
                double e = -vec3d2.dotProduct(vec3d5);
                boolean bl = e > 0.5D;
                int l = j / 2;
                int m = 9; // used to be 1
                int n = m / 2;
                float f = 1.0F;
                int o = this.client.textRenderer.getStringWidth(string);
                int p = AttributeModifier.floor(AttributeModifier.clampedLerp(255.0D, 75.0D, (double)((float)(MinecraftClient.getTime() - subtitleEntry2.getTime()) / 3000.0F)));
                int q = p << 16 | p << 8 | p;
                EnchantingScreenHandler.pushMatrix();
                EnchantingScreenHandler.translatef((float)window.getScaledWidth() - (float)l * 1.0F - 2.0F, (float)(window.getScaledHeight() - 30) - (float)(i * (m + 1)) * 1.0F, 0.0F);
                EnchantingScreenHandler.scalef(1.0F, 1.0F, 1.0F);

                if(!SubtitlesMod.config.transparentBackground)
                fill(-l - 1, -n - 1, l + 1, n + 1, -872415232);

                EnchantingScreenHandler.enableBlend();
                if (!bl) {
                    if (d > 0.0D) {
                        this.client.textRenderer.draw(">", l - this.client.textRenderer.getStringWidth(">"), -n, q + -16777216);
                    } else if (d < 0.0D) {
                        this.client.textRenderer.draw("<", -l, -n, q + -16777216);
                    }
                }

                this.client.textRenderer.draw(string, -o / 2, -n, q + -16777216);
                EnchantingScreenHandler.popMatrix();
            }

            EnchantingScreenHandler.disableBlend();
            EnchantingScreenHandler.popMatrix();
        }
    }


    public static void onSoundPlayed(SkullBlockEntity sound) {
        subtitlesHudStatic.soundPlayed(sound);
    }

    public void soundPlayed(SkullBlockEntity sound) {
        String subtitle2 = I18n.translate(sound.getIdentifier().toString() + ".subtitle");

        // Hide unknown subtitles
        if (SubtitlesMod.config.hideUnknown && subtitle2.equals(sound.getIdentifier().toString() + ".subtitle")) return;


        if (subtitle2 != null) {
            if (!this.entries.isEmpty()) {
                Iterator var4 = this.entries.iterator();

                while (var4.hasNext()) {
                    SubtitlesHud.SubtitleEntry subtitleEntry = (SubtitlesHud.SubtitleEntry) var4.next();
                    if (subtitleEntry.getText().equals(subtitle2)) {
                        subtitleEntry.reset(new Vec3d((double) sound.getX(), (double) sound.getY(), (double) sound.getZ()));
                        return;
                    }
                }
            }
            //     SubtitlesMod.LOGGER.info(subtitle2);
            this.entries.add(new SubtitlesHud.SubtitleEntry(subtitle2, new Vec3d((double) sound.getX(), (double) sound.getY(), (double) sound.getZ())));
        }
    }
    @Environment(EnvType.CLIENT)
    public class SubtitleEntry {
        private final String text;
        private long time;
        private Vec3d pos;

        public SubtitleEntry(String text, Vec3d pos) {
            this.text = text;
            this.pos = pos;
            this.time = MinecraftClient.getTime();
        }

        public String getText() {
            return this.text;
        }

        public long getTime() {
            return this.time;
        }

        public Vec3d getPosition() {
            return this.pos;
        }

        public void reset(Vec3d pos) {
            this.pos = pos;
            this.time = MinecraftClient.getTime();
        }
    }
}