package net.comdude2.plugins.comlibrary.commands.example;

import net.comdude2.plugins.comlibrary.commands.CommandMethod;
import net.comdude2.plugins.comlibrary.commands.CommandType;
import net.comdude2.plugins.comlibrary.commands.CommandWrapper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created on 20/11/2015 by ThePadna.
 */
public class Commands {
    /*
    Example of how to use commands in ComLib
     */

    /*
    <Var> will exit the matching process without being checked.
    Meaning you can get an unchanged string and do with it as you wish.

    Annotation variable TYPE:
    .DYNAMIC = VARIABLE COMMAND
    .STATIC = DRY CHECK, NO <VAR>
     */
    @CommandMethod(cmdArgs = {"set", "health", "<var>"}, console = true, usage = "/set health <player>", aliases = {}, description = "Set's players health to full.", type = CommandType.DYNAMIC, permission = "comlibrary.sethealth")
    public void setHealth(CommandWrapper cw) {
        Player p = Bukkit.getPlayer(cw.getVars()[0]); /* <VAR>s will stack into this array IN ORDER. In this command, we have one; [1] would be null. */
        if(p != null) {
            p.setHealth(20D);
            //...
        }
    }
}