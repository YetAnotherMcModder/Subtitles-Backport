package piper74.subtitles.backport.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.world.level.LevelInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import piper74.subtitles.backport.SubtitlesClientMod;
import piper74.subtitles.backport.SubtitlesMod;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "startGame", at = @At("HEAD"))
    public void subtitlesmod$startGame(String worldName, String string, LevelInfo levelInfo, CallbackInfo ci) {
        {
            SubtitlesClientMod.shouldUpdateWindow = true;
        }
    }
}
