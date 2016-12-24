/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import static java.lang.System.exit;
import java.util.Scanner;
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
                int x,y=0;
//                System.out.println("command: " + command);
                newCommand = false;
                command = command.trim();
                if (command.startsWith("ghtool")) {                   
                    String[] parts = command.split(" ");
                    if (parts.length == 1) {
                        System.out.println("help");
                    } else if (parts.length == 2) {
                        x = 600;
                    } else if (parts.length == 3) {
                        try {
                            x = Integer.parseInt(parts[2]);
                            for (int i = 0; i < x;) {
                                getEach(i);
                                i = i + 200;
                            }
                        } catch (ParseException e) {
                           //repositories by language 
                            y=600;
                            getRepositoriesByLanguage(y,parts[2]);

                        } 

                    } 
                    else if (parts.length == 4) {
                        try {
                            y = Integer.parseInt(parts[3]);
                            for (int i = 0; i < y;) {
                                getRepositoriesByLanguage(i,parts[2]);
                                i = i + 200;
                            }
                        } catch (ParseException e) {
                           //repositories by language
                            

                        } 

                    }
                }

            } else if (command.startsWith("ghtool desc")) {
                String[] parts = command.split(" ");
            } else if (command.equals("exit")) {
                exit(0);
            }

        }
    }

    public static void getEach(int since) {
//        String url = "https://api.github.com/repositories?since=" + since + "&access_token=you-own-token";
        String url = "https://api.github.com/repositories?since=" + since;
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            request.addHeader("content-type", "application/json");
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");

            System.out.println(json);

            JsonElement jelement = new JsonParser().parse(json);
            JsonArray jarr = jelement.getAsJsonArray();
            for (int i = 0; i < jarr.size(); i++) {
                JsonObject jo = (JsonObject) jarr.get(i);
                String fullName = jo.get("full_name").toString();
                fullName = fullName.substring(1, fullName.length() - 1);
                System.out.println(fullName);
            }

        } catch (IOException ex) {
            System.out.println(ex.getStackTrace());
        }
    }
    public static void getRepositoriesByLanguage(int since, String language) {
//        String url = "https://api.github.com/repositories?since=" + since + "&access_token=you-own-token";
          String url = "https://api.github.com/search/repositories?q=language:"+language+"&order=desc&since=" + since;
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            request.addHeader("content-type", "application/json");
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");

            System.out.println(json);

            JsonElement jelement = new JsonParser().parse(json);
            JsonArray jarr = jelement.getAsJsonArray();
            for (int i = 0; i < jarr.size(); i++) {
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

        while (true) {
            command = scanner.nextLine();
//            System.out.println("Input: " + command);
            newCommand = true;
        }
    }

}
