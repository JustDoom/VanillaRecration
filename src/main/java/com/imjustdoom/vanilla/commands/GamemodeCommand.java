package com.imjustdoom.vanilla.commands;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentEnum;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
import net.minestom.server.command.builder.condition.Conditions;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.entity.EntityFinder;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class GamemodeCommand extends Command {

    public GamemodeCommand(){
        super("gamemode");

        setCondition(Conditions::playerOnly);

        ArgumentEnum gamemode = ArgumentType.Enum("gamemode", GameMode.class).setFormat(ArgumentEnum.Format.LOWER_CASED);
        ArgumentEntity other = ArgumentType.Entity("player").onlyPlayers(true).singleEntity(true);

        addSyntax(this::executeSelf, gamemode);
        addSyntax(this::executeOther, gamemode, other);
    }

    private void executeSelf(@NotNull CommandSender sender, @NotNull CommandContext context){
        final GameMode gamemode = context.get("gamemode");
        assert gamemode != null;

        if(!(sender instanceof Player)){
            sender.sendMessage("A player is required to run this command");
            return;
        }

        ((Player) sender).setGameMode(gamemode);
        sender.sendMessage("Set own gamemode to " + gamemode.name().toLowerCase());
    }

    private void executeOther(@NotNull CommandSender sender, @NotNull CommandContext context){
        final GameMode gamemode = context.get("gamemode");
        final EntityFinder targetFinder = context.get("player");
        final Player player = targetFinder.findFirstPlayer(sender);

        player.setGameMode(gamemode);
        sender.sendMessage("Set " + player.getUsername() + "'s gamemode to " + gamemode.name().toLowerCase());
    }
}
