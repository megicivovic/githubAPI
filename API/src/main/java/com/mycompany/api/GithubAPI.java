/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.exit;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
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
    private static String accessToken = "";
    static Scanner scanner = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        GithubAPI reader = new GithubAPI();
        Thread t = new Thread(reader);
        t.start();

        while (true) {
            handleCommand();

            if (newCommand) {
                System.out.println("command: " + command);
                newCommand = false;
                command = command.trim();
                if (command.equals("ghtool")) {
                    System.out.println(getProperty("help.properties", "helpManual"));
                } else if (command.startsWith("ghtool list")) {
                    String[] parts = command.split(" ");
                    if (parts.length == 2) {
                        //default value                   
                        getLatestRepositories(Integer.parseInt(getProperty("access.properties", "defaultNumberOfRepos")));
                    } else if (parts.length == 3) {
                        //repositories by language                        
                        getRepositoriesByLanguage(Integer.parseInt(getProperty("access.properties", "defaultNumberOfReposByLanguage")), parts[2]);
                    } else if (parts.length == 4) {
                        getLatestRepositories(Integer.parseInt(parts[3]));
                    } else if (parts.length == 5) {
                        getRepositoriesByLanguage(Integer.parseInt(parts[3]), parts[2]);
                    }
                } else if (command != null && command.startsWith("ghtool desc")) {
                    String[] parts = command.split(" ");
                    if (parts.length == 2) {
                        System.out.println(getProperty("help.properties", "helpDesc"));
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
                } else {
                    System.out.println("Unknown command");
                }
                System.out.println("Type a command>");
            }
        }
    }

    public static String getProperty(String filename, String key) {
        InputStream stream = null;
        String value = "";
        try {
            Properties prop = new Properties();
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            stream = loader.getResourceAsStream(filename);
            prop.load(stream);

            // get the property value and print it out
            value = prop.getProperty("defaultNumberOfRepos");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    public static void getLatestRepositories(int numberOfRepositories) {
        String url = "https://api.github.com/search/repositories?access_token=" + accessToken + "&q=in&sort=updated&order=desc&per_page=100";
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
        String url = "https://api.github.com/search/repositories?&q=language:" + language + "&sort=updated&order=desc&access_token=" + accessToken;
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

    private static String getNewAuthorization(String clientID, String clientSecret, String username, String password, String note) {
        String url = "https://api.github.com/authorizations";
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(url);
            request.addHeader(BasicScheme.authenticate(
                    new UsernamePasswordCredentials(username, password),
                    "UTF-8", false));
            request.addHeader("content-type", "application/json");
            StringEntity params = new StringEntity("{ \n"
                    + "        \"note\": \"" + note + "\", \n"
                    + "        \"client_id\": \"" + clientID + "\",\n"
                    + "        \"client_secret\": \"" + clientSecret + "\",\n"
                    + "        \"scopes\": [\n"
                    + "    \"public_repo\"\n"
                    + "  ]\n"
                    + "      }");
            request.setEntity(params);
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");

            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            String access_token = jsonObject.get("token").toString();

            System.out.println(access_token);

        } catch (IOException ex) {
            System.out.println(ex.getStackTrace());
        }
        return accessToken;
    }

    private static void createNewRepo(String token) {
        String url = "https://api.github.com/user/repos";
        try {
            System.out.println("Name of the repo you are creating");
            String name = scanner.nextLine();

            System.out.println("Description of the repo you are creating");
            String description = scanner.nextLine();

            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(url);
            request.addHeader("Authorization", "token " + token);
            request.addHeader("content-type", "application/json");
            StringEntity params = new StringEntity("{ \n"
                    + "        \"name\": \"" + name + "\", \n"
                    + "        \"description\": \"" + description + "\"\n"
                    + "      }");

            request.setEntity(params);
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");

            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            String fullName = jsonObject.get("full_name").toString();
            String id = jsonObject.get("id").toString();
            System.out.println("Id of the created repository:" + id + ", full repository name:" + fullName);

        } catch (IOException ex) {
            System.out.println(ex.getStackTrace());
        }
    }

    private static void handleCommand() {
        try {
//            System.out.println("going to do some work");
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(GithubAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        scanner = new Scanner(System.in);
        System.out.println("Type a command>");
        while (true) {
            command = scanner.nextLine();
            System.out.println("Input: " + command);
            if (command != null && command.equals("ghtool repo")) {

                System.out.println("Client id:");
                String clientID = scanner.nextLine();

                System.out.println("Client secret:");
                String clientSecret = scanner.nextLine();

                System.out.println("Github username:");
                String username = scanner.nextLine();

                System.out.println("Github password:");
                String password = scanner.nextLine();

                System.out.println("Authorization creating note");
                String note = scanner.nextLine();

                String token = getNewAuthorization(clientID, clientSecret, username, password, note);

                createNewRepo(token);

            }
            newCommand = true;
        }
    }

}
