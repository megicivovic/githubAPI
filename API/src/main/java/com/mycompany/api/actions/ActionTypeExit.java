/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api.actions;

import static java.lang.System.exit;

/**
 *
 * @author Megi
 */
public class ActionTypeExit implements ActionCommand {

    private String command;

    public ActionTypeExit(String command) {
        this.command = command;
    }

    @Override
    public void execute() {

        try {
            if (!command.equals("")) {
                throw new Exception("Invalid exit command");
            }
            exit(0);
        } catch (Exception ex) {
              System.out.println(ex.getMessage());
        }
    }
}
