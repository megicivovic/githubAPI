package com.mycompany.api.builders;

import com.mycompany.api.actions.ActionCommand;
import java.util.HashMap;

/**
 *
 * @author Magdalina Civovic
 * Builds command tree
 */
public class CompositeCommandBuilder implements ICommandBuilder {

    private static HashMap<String, ICommandBuilder> commandMap = new HashMap<String, ICommandBuilder>();

    @Override
    public ActionCommand getCommand(String command) {
        try {
            if (command.contains(" ")) {
                int indexOfSpace = command.indexOf(" ");
                return commandMap.get(command.substring(0, indexOfSpace)).getCommand(command.substring(indexOfSpace + 1, command.length()));
            }
            return commandMap.get(command).getCommand("");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void registerBuilder(String name, ICommandBuilder builder) {
        commandMap.put(name, builder);
    }

}
