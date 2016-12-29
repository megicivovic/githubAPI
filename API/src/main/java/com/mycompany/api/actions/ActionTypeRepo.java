package com.mycompany.api.actions;

/**
 *
 * @author Magdalina Civovic 
 * Implements the behavior of "ghtool repo" command
 */
public class ActionTypeRepo implements ActionCommand {

    private String command = "";

    public ActionTypeRepo(String command) {
        this.command = command;
    }

    @Override
    public void execute(IGithubProvider githubProvider) {
        String result = githubProvider.executeActionTypeRepo(command);
        if ( result!= null) {
            System.out.println(result);
        }
    }
}
