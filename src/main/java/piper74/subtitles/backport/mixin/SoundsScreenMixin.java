package piper74.subtitles.backport.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SoundsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import piper74.subtitles.backport.SubtitlesMod;
import piper74.subtitles.backport.SubtitlesModConfig;

@Environment(EnvType.CLIENT)
@Mixin(SoundsScreen.class)
public class SoundsScreenMixin extends Screen {

    @Final
    @Mutable
    @Shadow
    private Screen parent;

    SubtitlesModConfig config;

    int i=9; //11


    @Inject(method = "init", at = @At(value = "TAIL"))
    public void subtitlesmod$init(CallbackInfo ci) {
        int var10004 = this.width / 2 - 75;
        int var10005 = this.height / 6 - 12;
        ++i;
        this.buttons.add(new ButtonWidget(201, var10004, var10005 + 24 * (i >> 1), 150, 20, ("Show Subtitles: " + (SubtitlesMod.config.enabled ? "ON" : "OFF"))));
    }

    // Recalculate window size twice
    // to avoid NullPointerException

    @Inject(method = "buttonClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;openScreen(Lnet/minecraft/client/gui/screen/Screen;)V"))
    protected void subtitlesmod$saveConfig(ButtonWidget button, CallbackInfo ci) {
        SubtitlesMod.shouldUpdateWindow = true;
        SubtitlesMod.saveDaDatatoFileStatic();
        SubtitlesMod.shouldUpdateWindow = true;
    }


        @Inject(method = "buttonClicked", at = @At(value = "TAIL"))
    protected void subtitlesmod$buttonClicked(ButtonWidget button, CallbackInfo ci) {
        if (button.active) {
            if (button.id == 201) {
                SubtitlesMod.config.enabled = !SubtitlesMod.config.enabled;
                SubtitlesMod.shouldUpdateWindow = true;
                button.message = ("Show Subtitles: " + (SubtitlesMod.config.enabled ? "ON" : "OFF"));
                //SubtitlesMod.saveDaDataStatic();
            }
        }
    }
}
