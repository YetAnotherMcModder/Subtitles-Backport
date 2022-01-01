package piper74.subtitles.backport;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.legacyfabric.fabric.api.command.CommandSide;
import net.legacyfabric.fabric.api.registry.CommandRegistry;
import net.legacyfabric.fabric.impl.command.CommandRegistryImpl;

@Environment(EnvType.CLIENT)
public class SubtitlesClientMod implements ClientModInitializer {
    public static boolean shouldUpdateWindow = false;
    CommandRegistry commandRegistry = CommandRegistryImpl.INSTANCE;
    //SubtitlesCommand subtitlesCommand = new SubtitlesCommand();

    @Override
    public void onInitializeClient() {
        commandRegistry.register(new SubtitlesCommand(), CommandSide.INTEGRATED);
        //subtitlesCommand.register();
    }
}
