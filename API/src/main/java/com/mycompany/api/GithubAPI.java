/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import static java.lang.System.exit;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Megi
 */
public class GithubAPI implements Runnable {

    static String command;
    static boolean newCommand = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GithubAPI reader = new GithubAPI();
        Thread t = new Thread(reader);
        t.start();

        while (true) {
            handleCommand();

            if (newCommand) {
                int x, y = 0;
                System.out.println("command: " + command);
                newCommand = false;
                command = command.trim();
                if (command.equals("ghtool")) {
                    System.out.println("help");
                } else if (command.startsWith("ghtool list")) {
                    String[] parts = command.split(" ");
                    if (parts.length == 2) {
                        //default value
                        x = 600;
                        getLatestRepositories(x);
                    } else if (parts.length == 3) {
                        try {
                            x = Integer.parseInt(parts[2]);
                            getLatestRepositories(x);

                        } catch (ParseException e) {
                            //repositories by language 
                            y = 600;
                            getRepositoriesByLanguage(y, parts[2]);

                        }
                    } else if (parts.length == 4) {
                        y = Integer.parseInt(parts[3]);
                        getRepositoriesByLanguage(y, parts[2]);
                    }
                } else if (command != null && command.startsWith("ghtool desc")) {
                    String[] parts = command.split(" ");
                    if (parts.length == 2) {
                        //desc command description

                    } else if (parts.length > 2) {
                        ExecutorService executor = Executors.newFixedThreadPool(5);//creating a pool of 5 threads  
                        for (int i = 2; i < parts.length; i++) {
                            Runnable worker = new RepoInformationThread(parts[i]);
                            executor.execute(worker);//calling execute method of ExecutorService  
                        }
                        executor.shutdown();
                        while (!executor.isTerminated()) {
                        }

                        System.out.println("Finished all threads");
                    }

                } else if (command != null && command.equals("exit")) {
                    exit(0);
                }   
                System.out.println("Type a command>");
            }
        }
    }

    public static void getLatestRepositories(int numberOfRepositories) {
        String url = "https://api.github.com/search/repositories?access_token=69bf3fb25c745c9ed7ff899b165ee496fb6923b5&q=in&sort=updated&order=desc";
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            request.addHeader("content-type", "application/json");
            HttpResponse result = httpClient.execute(request);

            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            JsonArray jarr = jsonObject.getAsJsonArray("items");
            for (int i = 0; i < numberOfRepositories; i++) {
                JsonObject jo = (JsonObject) jarr.get(i);
                String fullName = jo.get("full_name").toString();
                fullName = fullName.substring(1, fullName.length() - 1);
                System.out.println(fullName);
            }

        } catch (IOException ex) {
            System.out.println(ex.getStackTrace());
        }
    }

    public static void getRepositoriesByLanguage(int numberOfRepositories, String language) {
        String url = "https://api.github.com/search/repositories?access_token=69bf3fb25c745c9ed7ff899b165ee496fb6923b5&q=language:" + language + "&sort=updated&order=desc";
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            request.addHeader("content-type", "application/json");
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");

            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            JsonArray jarr = jsonObject.getAsJsonArray("items");
            for (int i = 0; i < numberOfRepositories; i++) {
                JsonObject jo = (JsonObject) jarr.get(i);
                String fullName = jo.get("full_name").toString();
                fullName = fullName.substring(1, fullName.length() - 1);
                System.out.println(fullName);
            }

        } catch (IOException ex) {
            System.out.println(ex.getStackTrace());
        }
    }

    private static void handleCommand() {
        try {
//            System.out.println("going to do some work");
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(GithubAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type a command>");
        while (true) {
            
            command = scanner.nextLine();
            System.out.println("Input: " + command);
            newCommand = true;
        }
    }

}
