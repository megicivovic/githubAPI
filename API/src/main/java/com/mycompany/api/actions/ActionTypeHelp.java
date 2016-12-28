/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        
         if (githubProvider.executeActionTypeHelp(command) != null) {
            System.out.println(githubProvider.executeActionTypeHelp(command));
        }
        
    }

}
