/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api.actions;

/**
 *
 * @author Magdalina Civovic Implements the behavior of "ghtool repo" command
 */
public class ActionTypeRepo implements ActionCommand {

    private String command = "";

    public ActionTypeRepo(String command) {
        this.command = command;
    }

    @Override
    public void execute(IGithubProvider githubProvider) {
        if (githubProvider.executeActionTypeRepo(command) != null) {
            System.out.println(githubProvider.executeActionTypeRepo(command));
        }
    }
}
