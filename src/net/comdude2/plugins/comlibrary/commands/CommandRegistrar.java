package net.comdude2.plugins.comlibrary.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created on 16/11/2015 by ThePadna.
 */

public class CommandRegistrar {

    private final HashSet<ComCommand> commands = new HashSet<ComCommand>();
    private final SimpleCommandMap commandMap;

    public CommandRegistrar() {
        commandMap = this.getCommandMap();
    }

    public boolean register(Class clazz) {

        Method[] methods = clazz.getMethods();

        for(Method m : methods) {
            CommandMethod cm = m.getAnnotation(CommandMethod.class);
            if(cm != null) {
                commands.add(new ComCommand(m, cm));
                /* force it to use specified name, even if it is already registered and therefore needs to be redefined */
                this.commandMap.register(cm.cmdArgs()[0], new ComCommand(m, cm));
            }
        }

        return false;
    }

    /*
    ComCommand - Defined
    CommandWrapper - Incoming
     */
    public boolean interceptCommand(CommandWrapper cw) {

        for(ComCommand cc : this.commands) {
            if(cc.getCommandMethod().type() == CommandType.STATIC) {
                String[] args = cc.getCommandMethod().cmdArgs();
                if(Arrays.equals(args, cw.getArgs())) {
                    cc.execute(cw.getSender(), cw.getLabel(), cw.getArgs());
                    return true;
                }
                if(cc.getCommandMethod().aliases().length > 0) {
                    for(String s : cc.getCommandMethod().aliases()) {
                        args[0] = s;
                        if(Arrays.equals(args, cw.getArgs())) {
                            cc.execute(cw.getSender(), cw.getLabel(), cw.getArgs());
                            return true;
                        }
                    }
                }
            } else if(cc.getCommandMethod().type() == CommandType.DYNAMIC) {

            }
        }
        return false;
    }

    public SimpleCommandMap getCommandMap() {
        SimplePluginManager spm = (SimplePluginManager) Bukkit.getServer().getPluginManager();
        try {
            Field f = spm.getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            return (SimpleCommandMap) f.get(spm);
        } catch(NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HashSet<ComCommand> getDefinedCommands() {
        return this.commands;
    }


}