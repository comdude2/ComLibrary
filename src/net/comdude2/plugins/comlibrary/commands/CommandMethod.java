package net.comdude2.plugins.comlibrary.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 17/11/2015 by ThePadna.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)

public @interface CommandMethod {

    public String[] cmdArgs(); // arguments in String array form just like onCommand
    public CommandType type(); // type of command enum CommandType.class
    public boolean console(); // work for console?
}
