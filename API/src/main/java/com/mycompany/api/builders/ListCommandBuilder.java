/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api.builders;

import com.mycompany.api.actions.ActionCommand;
import com.mycompany.api.actions.ActionTypeList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Megi
 */
public class ListCommandBuilder implements ICommandBuilder {

    @Override
    public ActionCommand getCommand(String command) {
        String[] parts = command.split(" ");        
        return new ActionTypeList(parts);
    }

}