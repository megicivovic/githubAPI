/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api.actions;

/**
 *
 * @author Magdalina Civovic Implements the behavior of "ghtool desc" command
 */
public class ActionTypeDesc implements ActionCommand {

    /**
     * parts of command
     */
    String[] parts;

    public ActionTypeDesc(String[] parts) {
        this.parts = parts;
    }

    @Override
    public void execute(IGithubProvider githubProvider) {
        String[] list = githubProvider.executeActionTypeDesc(parts);
        if (list!=null){
        for (int i = 0; i < list.length; i++) {
            if(list[i]!=null)
            System.out.println(list[i]);
        }
        }
    }

}
