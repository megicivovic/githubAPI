package com.mycompany.api.actions;

/**
 *
 * @author Magdalina Civovic
 * Implements the behavior of exit command
 */
public class ActionTypeExit implements ActionCommand {

    private String command;

    public ActionTypeExit(String command) {
        this.command = command;
    }

    @Override
    public void execute(IGithubProvider githubProvider) {
        String result = githubProvider.executeActionTypeExit(command);
        if (result != null) {
            System.out.println(result);
        }
    }
}
