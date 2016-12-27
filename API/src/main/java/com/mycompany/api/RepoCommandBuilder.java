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
class RepoCommandBuilder implements ICommandBuilder {

    public RepoCommandBuilder() {
    }

    @Override
    public ActionCommand getCommand(String command) {
        if (command.equals("")) {
            return new ActionTypeRepo();
        }
        return null;
    }

}
