package com.mycompany.api.actions;

/**
 *
 * @author Magdalina Civovic 
 * Implements the behavior of "ghtool list" command
 */
public class ActionTypeList implements ActionCommand {

    String[] parts;

    public ActionTypeList(String[] parts) {
        this.parts = parts;
    }

    @Override
    public void execute(IGithubProvider githubProvider) {
        String[] list = githubProvider.executeActionTypeList(parts);
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                if (list[i] != null) {
                    System.out.println(list[i]);
                }
            }
        }

    }

}
