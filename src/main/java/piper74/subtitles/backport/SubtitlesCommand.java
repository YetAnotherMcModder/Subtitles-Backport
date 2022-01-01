package piper74.subtitles.backport;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.legacyfabric.fabric.api.command.CommandSide;
import net.legacyfabric.fabric.api.registry.CommandRegistry;
import net.legacyfabric.fabric.impl.command.CommandRegistryImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.command.AbstractCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class SubtitlesCommand extends AbstractCommand {
    CommandRegistry commandRegistry = CommandRegistryImpl.INSTANCE;
    //public void register() { commandRegistry.register(new SubtitlesCommand(), CommandSide.COMMON); }

    public String getCommandName() {
        return "subtitles";
    }

    public String getUsageTranslationKey(CommandSource source) {
        return "commands.subtitles.usage";
    }

    public int getPermissionLevel() {
        return 0;
    }

    public void execute(CommandSource source, String[] args) throws CommandException {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        //SubtitlesMod subtitlesMod;
        //SubtitlesMod.LOGGER.info("Subtitles command executed!");
        SubtitlesMod.config.enabled = !SubtitlesMod.config.enabled;
        SubtitlesClientMod.shouldUpdateWindow = true;
        // Save data here!
        source.sendMessage(new TranslatableText(SubtitlesMod.config.enabled ? "commands.subtitles.on" : "commands.subtitles.off"));
    }


    }