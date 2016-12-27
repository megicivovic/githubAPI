/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api.actions;

/**
 *
 * @author Megi
 */
public class ActionTypeHelp implements ActionCommand {

    private String command;

    public ActionTypeHelp(String command) {
        this.command = command;
    }

    @Override
    public void execute() {

        try {
            if (!command.equals("")) {
                throw new Exception("Invalid exit command");
            }
            ActionTypeDesc.displayHelp("description_command.txt");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
