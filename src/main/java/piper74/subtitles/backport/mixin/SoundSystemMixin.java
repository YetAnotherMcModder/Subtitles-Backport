package piper74.subtitles.backport.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import piper74.subtitles.backport.SubtitlesMod;
import piper74.subtitles.backport.util.SubtitlesHud;

@Environment(EnvType.CLIENT)
@Mixin(SoundSystem.class)
public abstract class SoundSystemMixin {
    MinecraftClient client;
    public SubtitlesHud subtitlesHud = new SubtitlesHud(client);

    /***************************************************
     * @reason Send played sound names to subtitle HUD
     * @author piper74
     ***************************************************/

    @Inject(method = "play(Lnet/minecraft/client/sound/SoundInstance;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/sound/SoundSystem;field_5494:Lnet/minecraft/client/sound/SoundSystem$ThreadSafeSoundSystem;", ordinal = 4))
    public void subtitlesmod$play(SoundInstance soundInstance, CallbackInfo ci)
    {
        if (SubtitlesMod.config.enabled) {
            SubtitlesHud.onSoundPlayed(soundInstance);
        }
    }

}
