package piper74.subtitles.backport.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.level.storage.LevelStorageAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import piper74.subtitles.backport.SubtitlesMod;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "startGame", at = @At("HEAD"))
    public void subtitlesmod$startGame(String worldName, String string, LevelStorageAccess levelInfo, CallbackInfo ci) {
        {
            SubtitlesMod.shouldUpdateWindow = true;
        }
    }
}
