package piper74.subtitles.backport.util;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GLX;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.util.Window;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import piper74.subtitles.backport.SubtitlesMod;
import net.minecraft.client.network.ClientPlayerEntity;

import java.util.Iterator;
import java.util.List;

@Environment(EnvType.CLIENT)
public class SubtitlesHud extends DrawableHelper {
    private final MinecraftClient client;
    private final List<SubtitlesHud.SubtitleEntry> entries = Lists.newArrayList();
    private static SubtitlesHud subtitlesHudStatic;
    private boolean enabled;
    private PlayerEntity player = null;


    public SubtitlesHud(MinecraftClient client) {
        this.client = client;
        //this.player = client.world.getPlayerByName(client.getInstance().getSession().getUsername());
        subtitlesHudStatic = this;
    }

    public void render(Window window) {
        if(SubtitlesMod.config.enabled && !this.enabled)
            this.enabled = true;
        else if (!SubtitlesMod.config.enabled && this.enabled)
            this.enabled = false;

        // This line causes a crash
        //this.player = client.world.getPlayerByName(player.getTranslationKey());

        //this.player = client.getSession().
        //this.player = this.client.world.getPlayerByName(client.getInstance().getSession().getUsername());
        if(this.player == null)
        this.player = client.world.getPlayerByName(client.getInstance().getSession().getUsername());

        //Vec3d vec2 = Vec3d.of(0.0D, 0.0D, 0.0D);
        //entries.add(new SubtitleEntry("asd", vec2.add(0.0D, 0.0D, 0.0D)));

        if (this.enabled && !entries.isEmpty()) {
            //GlStateManager.pushMatrix();
            //GlStateManager.enableBlend();
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            //GLX.
            //GlStateManager.blendFuncSeparate(770, 771, 1, 0);
            GLX.glBlendFuncSeparate(770, 771, 1, 0);
            //Vec3d vec3d = Vec3d.of((double)0.0, (double)0.0, (double)0.0);
            Vec3d vec3d = Vec3d.of(this.player.x, (double)this.player.y + (double)this.player.getEyeHeight(), (double)this.player.z);
            //Vec3d vec3d = Vec3d.of(0.0D, 1.0D, 0.0D);

            Vec3d vec3d2 = Vec3d.of(0.0D, 0.0D, -1.0D); //.vec3d2.method_605(-this.player.pitch * 0.017453292F).vec3d2.method_609(-this.player.yaw * 0.017453292F);
            vec3d2.method_605(-this.player.pitch * 0.017453292F);
            vec3d2.method_609(-this.player.yaw * 0.017453292F);

            Vec3d vec3d3 = Vec3d.of(0.0D, 1.0D, 0.0D); //.method_605(-this.player.pitch * 0.017453292F).method_609(-this.player.yaw * 0.017453292F);
            vec3d3.method_605(-this.player.pitch * 0.017453292F);
            vec3d3.method_609(-this.player.yaw * 0.017453292F);
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
                Vec3d vec3d5 = subtitleEntry2.getPosition().add(-vec3d.x, -vec3d.y, -vec3d.z).normalize();
                double d = -vec3d4.dotProduct(vec3d5);
                double e = -vec3d2.dotProduct(vec3d5);
                boolean bl = e > 0.5D;
                int l = j / 2;
                int m = 9; // used to be 1
                int n = m / 2;
                float f = 1.0F;
                int o = this.client.textRenderer.getStringWidth(string);
                int p = MathHelper.floor(MathHelper.clampedLerp(255.0D, 75.0D, (double)((float)(MinecraftClient.getTime() - subtitleEntry2.getTime()) / 3000.0F)));
                int q = p << 16 | p << 8 | p;
                //GlStateManager.pushMatrix();
                GL11.glPushMatrix();
                //GlStateManager.translatef((float)window.getScaledWidth() - (float)l * 1.0F - 2.0F, (float)(window.getScaledHeight() - 30) - (float)(i * (m + 1)) * 1.0F, 0.0F);
                GL11.glTranslatef((float)window.getScaledWidth() - (float)l * 1.0F - 2.0F, (float)(window.getScaledHeight() - 30) - (float)(i * (m + 1)) * 1.0F, 0.0F);
                //GlStateManager.scalef(1.0F, 1.0F, 1.0F);
                GL11.glScalef(1.0F, 1.0F, 1.0F);

                if(!SubtitlesMod.config.transparentBackground)
                this.fill(-l - 1, -n - 1, l + 1, n + 1, -872415232);


                GL11.glEnable(3042);
                if (!bl) {
                    if (d > 0.0D) {
                        this.client.textRenderer.draw(">", l - this.client.textRenderer.getStringWidth(">"), -n, q + -16777216);
                    } else if (d < 0.0D) {
                        this.client.textRenderer.draw("<", -l, -n, q + -16777216);
                    }
                }

                this.client.textRenderer.draw(string, -o / 2, -n, q + -16777216);
                //GlStateManager.popMatrix();
                GL11.glPopMatrix();
            }

            //GlStateManager.disableBlend();
            GL11.glDisable(3042);
            //GlStateManager.popMatrix();
            GL11.glPopMatrix();
        }
    }


    public static void onSoundPlayed(SoundInstance sound) {
        subtitlesHudStatic.soundPlayed(sound);
    }

    public void soundPlayed(SoundInstance sound) {
        String subtitle2 = I18n.translate(sound.getIdentifier().toString() + ".subtitle");

        // Hide unknown subtitles
        if (SubtitlesMod.config.hideUnknown && subtitle2.equals(sound.getIdentifier().toString() + ".subtitle")) return;


        if (subtitle2 != null) {
            if (!this.entries.isEmpty()) {
                Iterator var4 = this.entries.iterator();

                while (var4.hasNext()) {
                    SubtitleEntry subtitleEntry = (SubtitleEntry) var4.next();
                    if (subtitleEntry.getText().equals(subtitle2)) {
                        subtitleEntry.reset( Vec3d.of((double) sound.getX(), (double) sound.getY(), (double) sound.getZ()));
                        return;
                    }
                }
            }
            //     SubtitlesMod.LOGGER.info(subtitle2);
            //Vec3d vec2= null;
            entries.add(new SubtitleEntry(subtitle2, Vec3d.of((double) sound.getX(), (double) sound.getY(), (double) sound.getZ())));
        }
    }

    public static void setPlayerStatic(PlayerEntity player) {
        subtitlesHudStatic.setPlayer(player);
    }
    public void setPlayer(PlayerEntity player) {
        this.player = player;
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