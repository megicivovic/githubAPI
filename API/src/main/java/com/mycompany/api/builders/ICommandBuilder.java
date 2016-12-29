package com.mycompany.api.builders;

import com.mycompany.api.actions.ActionCommand;

/**
 *
 * @author Magdalina Civovic
 * abstract command buider
 */
public interface ICommandBuilder {

    public ActionCommand getCommand(String command);
}
