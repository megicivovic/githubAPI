/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api.builders;

import com.mycompany.api.actions.ActionCommand;

/**
 *
 * @author Megi
 */
public interface ICommandBuilder {

    public ActionCommand getCommand(String command);
}