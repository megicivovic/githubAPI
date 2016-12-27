/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api;

/**
 *
 * @author Megi
 */
public class ListCommandBuilder implements ICommandBuilder {

    @Override
    public ActionCommand getCommand(String command) {
        String[] parts = command.split(" ");
        String language = "";
        int number = 0;

        if (parts.length == 0) {

        } else if (parts.length == 1) {
            //repositories by language
            language = parts[0];
        } else if (parts.length == 2) {
            number = Integer.parseInt(parts[1]);
        } else if (parts.length == 3) {
            language = parts[0];
            number = Integer.parseInt(parts[2]);
        }
        return new ActionTypeList(language, number);
    }

}
