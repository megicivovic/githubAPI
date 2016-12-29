package com.mycompany.api.builders;

import com.mycompany.api.actions.ActionCommand;
import com.mycompany.api.actions.ActionTypeRepo;

/**
 *
 * @author Magdalina Civovic
 * Handles repo command branch
 */
public class RepoCommandBuilder implements ICommandBuilder {

    @Override
    public ActionCommand getCommand(String command) {
        return new ActionTypeRepo(command);
    }

}
