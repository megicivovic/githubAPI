package com.mycompany.api.actions;

/**
 *
 * @author Magdalina Civovic 
 * Implements the behavior of "ghtool" command
 */
public class ActionTypeHelp implements ActionCommand {

    private String command;

    public ActionTypeHelp(String command) {
        this.command = command;
    }

    @Override
    public void execute(IGithubProvider githubProvider) {
        String result = githubProvider.executeActionTypeHelp(command);
        if (result != null) {
            System.out.println(result);
        }
    }

}
