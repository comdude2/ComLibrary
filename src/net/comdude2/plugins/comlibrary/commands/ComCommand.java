package net.comdude2.plugins.comlibrary.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created on 19/11/2015 by ThePadna.
 */
public class ComCommand extends Command {
    /*
    might ASWELL follow the naming convention ^_+
     */

    private final Method method;
    private CommandMethod commandMethod = null;
    private final CommandRegistrar crInstance;

    public ComCommand(Method m, CommandMethod cm, CommandRegistrar cr) {
        super(cm.cmdArgs()[0], cm.description(), cm.usage(), Arrays.asList(cm.aliases()));
        this.crInstance = cr;
        this.method = m;
        this.commandMethod = cm;
    }

    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(!this.commandMethod.console()) {
            if(sender instanceof ConsoleCommandSender) return false;
        }
        if(!sender.hasPermission(this.commandMethod.permission())) return false;

        if(this.crInstance.getCommandsOb() == null) {
            System.out.println("Commands class not registered; use CommandRegistrar#register(instance)");
            return false;
        }

        boolean b = true;
        try {
            method.invoke(this.crInstance.getCommandsOb(), new CommandWrapper(sender, this, commandLabel, args));
        } catch(IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            b = false;
        }

        return b;
    }

    public Method getMethod() {
        return this.method;
    }

    public CommandMethod getCommandMethod() {
        return this.commandMethod;
    }

}
