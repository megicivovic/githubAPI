/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api;

import static java.lang.System.exit;

/**
 *
 * @author Megi
 */
class ActionTypeExit implements ActionCommand {

    @Override
    public void execute() {

        exit(0);

    }

}
