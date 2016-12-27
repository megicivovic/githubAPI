/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Megi
 */
class ActionTypeHelp implements ActionCommand {

    @Override
    public void execute() {

        try {
            ActionTypeDesc.displayHelp("description_command.txt");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

}
