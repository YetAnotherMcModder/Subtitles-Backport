package piper74.subtitles.backport;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class SubtitlesMod implements ModInitializer {
	// Config code was borrowed from here:
	// https://github.com/PseudoDistant/fatal-blow/blob/4de23efec9e116b266f8078c69d24b30efaa85b9/src/main/java/im/hunnybon/mobsplosion/Mobsplosion.java
	public static final Logger LOGGER = LogManager.getLogger("Subtitles backport");
	public static SubtitlesModConfig config = new SubtitlesModConfig(true, false, true);;
	public Gson daData = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
	Path configPath = Paths.get("config/subtitles-backport.json");
	static SubtitlesMod subtitlesModStatic;
	public static boolean shouldUpdateWindow = false;

	public static void saveDaDatatoFileStatic()
	{
		subtitlesModStatic.saveDaDatatoFile();
	}

	public void saveDaDatatoFile() {
		if (config != null)
		{
		try {
			Files.write(configPath, Collections.singleton(daData.toJson(config)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	}

	public void saveDaData() {
		try{
			if (configPath.toFile().exists()) {
				config = daData.fromJson(new String(Files.readAllBytes(configPath)), SubtitlesModConfig.class);
			} else {
				Files.write(configPath, Collections.singleton(daData.toJson(config)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		subtitlesModStatic = this;
		saveDaData();
	}
}
