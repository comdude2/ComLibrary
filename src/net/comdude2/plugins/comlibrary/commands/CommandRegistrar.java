package net.comdude2.plugins.comlibrary.commands;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created on 16/11/2015 by ThePadna.
 */

public class CommandRegistrar {

    private final HashMap<String[], Method> commands = new HashMap<String[], Method>();

    public boolean register(Class clazz) {

        Method[] methods = clazz.getMethods();

        for(Method m : methods) {
            CommandMethod cm = m.getAnnotation(CommandMethod.class);
            if(cm != null) commands.put(cm.cmdArgs(), m);
        }

        return false;
    }

    public boolean interceptCommand(CommandWrapper cw) {

        for(String[] s : commands.keySet()) {
            Method m = this.commands.get(s);
            CommandMethod cm = m.getAnnotation(CommandMethod.class);
            if(cm.type() == CommandType.STATIC) {
                if(Arrays.equals(s, cw.getArgs())) {
                    //run
                    return true;
                }
            } else if(cm.type() == CommandType.DYNAMIC) {

            }
        }
        return false;
    }

    public HashMap<String[], Method> getMethodMap() {
        return this.commands;
    }


}
