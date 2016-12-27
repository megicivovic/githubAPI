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
class ActionTypeDesc implements ActionCommand {

    String[] parts;

    public ActionTypeDesc(String[] parts) {
        this.parts = parts;
    }

    @Override
    public void execute() {
        if (parts.length == 0 || parts[0].equals("")) {
            try {
                displayHelp("description_command.txt");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } else if (parts.length > 0) {
            ExecutorService executor = Executors.newFixedThreadPool(5);
            for (int i = 0; i < parts.length; i++) {
                Runnable worker = new RepoInformationThread(parts[i]);
                executor.execute(worker);
            }
            executor.shutdown();
            while (!executor.isTerminated()) {
            }

            System.out.println("Finished all threads");
        }
    }

    static void displayHelp(String filename) throws Exception {
        BufferedReader br = null;
        FileReader fr = null;

        try {

            fr = new FileReader(filename);
            br = new BufferedReader(fr);

            String sCurrentLine;

            br = new BufferedReader(new FileReader(filename));
            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
            }

        } catch (IOException e) {
            throw new Exception(e.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                throw new Exception(ex.getMessage());
            }

        }
    }

}
