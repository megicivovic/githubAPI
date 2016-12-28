/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api.builders;

import com.mycompany.api.actions.ActionCommand;
import com.mycompany.api.actions.ActionTypeRepo;

/**
 *
 * @author Magdalina Civovic
 */
public class RepoCommandBuilder implements ICommandBuilder {

    @Override
    public ActionCommand getCommand(String command) {
        return new ActionTypeRepo(command);
    }

}
