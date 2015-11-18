package net.comdude2.plugins.comlibrary.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created on 18/11/2015 by ThePadna.
 */
public class CommandWrapper {

    private final CommandSender sender;
    private final Command cmd;
    private final String label;
    private final String[] args;

    public CommandWrapper(CommandSender sender, Command cmd, String label, String[] args) {
        this.sender = sender;
        this.cmd = cmd;
        this.label = label;
        this.args = args;
    }

    public Command getCmd() {
        return this.cmd;
    }

    public CommandSender getSender() {
        return this.sender;
    }

    public String getLabel() {
        return this.label;
    }

    public String[] getArgs() {
        return this.args;
    }
}
