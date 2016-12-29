package com.mycompany.api.actions;

/**
 *
 * @author Magdalina Civovic
 */
public interface ActionCommand {

    /**
     * executes the command
     */
    void execute(IGithubProvider githubProvider);
}
