package com.imjustdoom.vanilla.commands;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
import net.minestom.server.command.builder.arguments.relative.ArgumentRelativeVec3;
import net.minestom.server.command.builder.condition.Conditions;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.location.RelativeVec;
import org.jetbrains.annotations.NotNull;

public class TeleportCommand extends Command {

    public TeleportCommand() {
        super("teleport");

        setCondition(Conditions::playerOnly);

        ArgumentEntity tpTo = ArgumentType.Entity("to").onlyPlayers(true).singleEntity(true);
        ArgumentEntity tpTarget = ArgumentType.Entity("target").onlyPlayers(true).singleEntity(true);
        ArgumentRelativeVec3 pos = ArgumentType.RelativeVec3("pos");

        addSyntax(this::executeSelf, tpTo);
        addSyntax(this::executeOther, tpTarget, tpTo);

        addSyntax(this::executeSelfPos, pos);
    }

    private void executeSelfPos(@NotNull CommandSender sender, @NotNull CommandContext context) {
        final RelativeVec pos = context.get("pos");
        final Pos position = pos.from((Entity) sender).asPosition();
        if(!(sender instanceof Player)) {
            sender.sendMessage("A player is required to run this command here");
            return;
        }

        final Player player = sender.asPlayer();

        player.teleport(position);
        player.sendMessage("Teleported to " + player.getUsername());
    }

    private void executeSelf(@NotNull CommandSender sender, @NotNull CommandContext context) {
        final Player player = context.get("to");
        if(!(sender instanceof Player)) {
            sender.sendMessage("A player is required to run this command here");
            return;
        }

        sender.asPlayer().setInstance(player.getInstance());
        sender.asPlayer().teleport(player.getPosition());
        sender.sendMessage("Teleported to " + player.getUsername());
    }

    private void executeOther(@NotNull CommandSender sender, @NotNull CommandContext context) {
        final Player player = context.get("to");
        final Player playerTarget = context.get("target");
        if(!(sender instanceof Player)) {
            sender.sendMessage("A player is required to run this command here");
            return;
        }

        playerTarget.setInstance(player.getInstance());
        playerTarget.teleport(player.getPosition());

        sender.sendMessage("Teleported " + playerTarget.getUsername() + " to " + player.getUsername());
    }
}