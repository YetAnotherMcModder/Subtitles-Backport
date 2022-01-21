// This file was based on:
// https://github.com/modmuss50/OptiFabric/blob/20e651113830e957e5b5c3beb3939ca82576a50c/src/main/java/me/modmuss50/optifabric/util/OptifineZipResourcePack.java

package piper74.subtitles.backport.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resource.DefaultResourcePack;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ZipResourcePack;
import net.minecraft.util.Identifier;
import piper74.subtitles.backport.SubtitlesMod;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.function.Supplier;
import java.util.zip.ZipFile;

@Environment(EnvType.CLIENT)
public class SubtitlesZipResourcePack extends ZipResourcePack {
    public SubtitlesZipResourcePack(File file) {
        super(file);
    }

    @Override
    public String getName() {
        return "Subtitles Lang Data";
    }

    @Override
    public BufferedImage getIcon() {
        // This is likely useless
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(DefaultResourcePack.class.getResourceAsStream("/" + (new Identifier("pack.png")).getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }

    public static Supplier<ResourcePack> getSupplier(File file) {
        return () -> new SubtitlesZipResourcePack(file);
    }
}