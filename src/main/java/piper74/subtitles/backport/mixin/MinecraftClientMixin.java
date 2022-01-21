package piper74.subtitles.backport.mixin;

import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.level.LevelInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import piper74.subtitles.backport.SubtitlesMod;
import piper74.subtitles.backport.util.SubtitlesZipResourcePack;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Shadow
    private List resourcePacks = Lists.newArrayList();

    @Inject(method = "startGame", at = @At("HEAD"))
    public void subtitlesmod$startGame(String worldName, String string, LevelInfo levelInfo, CallbackInfo ci) {
        {
            SubtitlesMod.shouldUpdateWindow = true;
        }
    }

    /*************************
     * @reason very hacky solution to get custom lang file loaded
     * @author piper74
    **************************/

    @Inject(method = "method_5574", at = @At("TAIL"))
    private void method_5574(CallbackInfo ci) {
        SubtitlesZipResourcePack subtitlesZipResourcePack = null;
        try {
            String string = SubtitlesMod.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            subtitlesZipResourcePack = new SubtitlesZipResourcePack(new File(string));
            //SubtitlesMod.LOGGER.info("Subtitles mod jar directory is: " + string ); // used for testing
            this.resourcePacks.add(subtitlesZipResourcePack);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
