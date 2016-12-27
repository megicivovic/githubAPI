/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api.actions;

import com.mycompany.api.main.RepoInformationThread;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Megi
 */
public class ActionTypeDesc implements ActionCommand {

    String[] parts;

    public ActionTypeDesc(String[] parts) {
        this.parts = parts;
    }

    @Override
    public void execute() {
        try {
            if (parts.length == 0 || parts[0].equals("")) {
                try {
                    displayHelp("description_command.txt");
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            } else if (parts.length > 0) {
                ExecutorService executor = Executors.newCachedThreadPool();
                for (int i = 0; i < parts.length; i++) {
                    Runnable worker = new RepoInformationThread(parts[i]);
                    executor.execute(worker);
                    if (((RepoInformationThread)worker) .hasException().length()>0){
                        throw new Exception(((RepoInformationThread)worker) .hasException());
                    }
                }
                executor.shutdown();
                while (!executor.isTerminated()) {
                }
                
                System.out.println("Finished all threads");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void displayHelp(String filename) throws Exception {
        BufferedReader br = null;
        FileReader fr = null;

        try {

            fr = new FileReader(filename);
            br = new BufferedReader(fr);

            String currentLine;

            br = new BufferedReader(new FileReader(filename));
            while ((currentLine = br.readLine()) != null) {
                System.out.println(currentLine);
            }

        } catch (FileNotFoundException e) {
            throw new Exception("Help file not found");
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
