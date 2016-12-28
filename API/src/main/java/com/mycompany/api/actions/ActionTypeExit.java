/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api.actions;

/**
 *
 * @author Magdalina Civovic Implements the behavior of exit command
 */
public class ActionTypeExit implements ActionCommand {

    private String command;

    public ActionTypeExit(String command) {
        this.command = command;
    }

    @Override
    public void execute(IGithubProvider githubProvider) {
        if (githubProvider.executeActionTypeExit(command) != null) {
            System.out.println(githubProvider.executeActionTypeExit(command));
        }
    }
}
