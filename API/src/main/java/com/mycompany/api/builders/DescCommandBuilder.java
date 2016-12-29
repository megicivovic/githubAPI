package com.mycompany.api.builders;

import com.mycompany.api.actions.ActionCommand;
import com.mycompany.api.actions.ActionTypeDesc;

/**
 *
 * @author Magdalina Civovic
 *  Handles desc command branch
 */
public class DescCommandBuilder implements ICommandBuilder {

    @Override
    public ActionCommand getCommand(String command) {
        String[] parts = command.split(" ");
        return new ActionTypeDesc(parts);
    }

}
