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
public class DescCommandBuilder implements ICommandBuilder {

    @Override
    public ActionCommand getCommand(String command) {
        String[] parts = command.split(" ");
        return new ActionTypeDesc(parts);
    }

}
