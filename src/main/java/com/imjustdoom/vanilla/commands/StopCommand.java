package com.imjustdoom.vanilla.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StopCommand extends Command {

    public StopCommand() {
        super("stop");

        addSyntax(this::execute);
    }

    private void execute(@NotNull CommandSender sender, @NotNull CommandContext context){
        MinecraftServer.stopCleanly();
    }
}