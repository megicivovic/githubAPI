/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api.actions;

/**
 *
 * @author Magdalina Civovic
 */
public interface ActionCommand {

    /**
     * executes the command
     */
    void execute(IGithubProvider githubProvider);
}
