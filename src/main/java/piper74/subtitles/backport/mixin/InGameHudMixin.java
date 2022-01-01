package piper74.subtitles.backport.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import piper74.subtitles.backport.SubtitlesClientMod;
import piper74.subtitles.backport.util.SubtitlesHud;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.client.util.Window;
import piper74.subtitles.backport.SubtitlesMod;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Mutable
    @Final
    @Shadow
    private MinecraftClient client;

    private SubtitlesHud subtitlesHud;

    Window window = new Window(client.getInstance());

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void subtitlesmod$InGameHud(MinecraftClient client, CallbackInfo ci)
    {
        if (SubtitlesMod.config.enabled) {
            subtitlesHud = new SubtitlesHud(client);
            //SubtitlesMod.LOGGER.info("InGameHud Initialized!");
        }
    }

    @Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/hud/InGameHud;titleTotalTicks:I", ordinal = 0))
    public void subtitlesmod$render(float tickDelta, CallbackInfo ci)
    {
        if (SubtitlesMod.config.enabled) {
            if(SubtitlesClientMod.shouldUpdateWindow)
            {
                window = new Window(client.getInstance());
                SubtitlesClientMod.shouldUpdateWindow = false;
            }
            subtitlesHud.render(window);
        }
    }
}
