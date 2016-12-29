package com.mycompany.api.builders;

import com.mycompany.api.actions.ActionCommand;
import com.mycompany.api.actions.ActionTypeList;

/**
 *
 * @author Magdalina Civovic
 * Handles list command branch
 */
public class ListCommandBuilder implements ICommandBuilder {

    @Override
    public ActionCommand getCommand(String command) {
        String[] parts = command.split(" ");
        return new ActionTypeList(parts);
    }

}
