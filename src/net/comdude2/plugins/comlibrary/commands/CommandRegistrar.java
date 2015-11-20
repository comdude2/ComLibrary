/*
ComLibrary - A library plugin for Minecraft
Copyright (C) 2015  comdude2 (Matt Armer)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

Contact: admin@mcviral.net
*/

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
