/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api.actions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mycompany.api.main.GithubAPI;
import com.mycompany.api.main.RepoInformationThread;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.exit;
import java.util.LinkedList;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Magdalina Civovic
 */
public class GithubProvider implements IGithubProvider {

    private static GithubProvider instance;

    public static GithubProvider getInstance() {
        if (instance == null) {
            instance = new GithubProvider();
        }
        return instance;
    }

    @Override
    public String[] executeActionTypeDesc(String[] parts) {
        String[] list = null;
        try {
            if (parts.length == 0 || parts[0].equals("")) {
                try {
                    displayHelp("manual.txt");
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            } else if (parts.length > 0) {
                 list = new String[parts.length];
                ExecutorService executor = Executors.newCachedThreadPool();
                for (int i = 0; i < parts.length; i++) {
                    Runnable worker = new RepoInformationThread(parts[i], list, i);
                    executor.execute(worker);
                }
                executor.shutdown();
                while (!executor.isTerminated()) {
                }

                for (int i = 0; i < list.length; i++) {
                    System.out.println(list[i]);
                }
                System.out.println("Finished all threads");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    /**
     * Displays help file content
     *
     * @param filename
     * @throws Exception
     */
    public void displayHelp(String filename) throws Exception {
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

    @Override
    public String executeActionTypeExit(String command) {
        try {
            if (!command.equals("")) {
                throw new Exception("Invalid exit command");
            }
            exit(0);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return "";
    }

    @Override
    public String executeActionTypeHelp(String command) {

        try {
            if (!command.equals("")) {
                throw new Exception("Invalid exit command");
            }
            displayHelp("description_command.txt");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return "";
    }

    @Override
    public String executeActionTypeList(String[] parts) {
        String url = getProperty("access.properties", "urlAllRepositories");
        String language = "";
        int number = 0;

        try {
            if (parts.length == 1) {
                //repositories by language
                language = parts[0];
            } else if (parts.length == 2) {
                if (parts[0].equals("-n")) {
                    number = Integer.parseInt(parts[1]);
                } else {
                    throw new Exception("invalid list command");
                }
            } else if (parts.length == 3) {
                if (parts[1].equals("-n")) {
                    language = parts[0];
                    number = Integer.parseInt(parts[2]);
                } else {
                    throw new Exception("invalid list command");
                }
            }

            if (!language.equals("")) {
                url = getProperty("access.properties", "urlRepositoriesByLanguage").toString() + language + "&page=1";
            }
            getRepositories(number, url);

        } catch (NumberFormatException numberFormatException) {
            System.out.println("Incorrect number format");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return "";
    }

    public void getRepositories(int numberOfRepositories, String url) throws Exception {
        try {
            int pageNumber = 1;
            int defaultNumberOfRepositories = Integer.parseInt(getProperty("access.properties", "defaultNumberOfRepos"));

            if (numberOfRepositories == 0) {
                numberOfRepositories = defaultNumberOfRepositories;
            }

            while (numberOfRepositories > 30) {
                JsonArray jarr = getJsonArray(url);

                for (int i = 0; i < 30; i++) {
                    printFullName(jarr, i);
                }
                numberOfRepositories -= 30;
                pageNumber++;
                url = url.substring(0, url.lastIndexOf("=") + 1) + pageNumber;
            }
            JsonArray jarr = getJsonArray(url);
            for (int i = 0; i < numberOfRepositories; i++) {
                printFullName(jarr, i);
            }

        } catch (NumberFormatException | ParseException ex) {
            System.out.println("Invalid number of repos");
        } catch (IOException | JsonSyntaxException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public String executeActionTypeRepo(String command) {
        try {

            if (command.length() == 0 || command.equals("")) {

                System.out.println("Client id:");
                String clientID = GithubAPI.getInput();

                System.out.println("Client secret:");
                String clientSecret = GithubAPI.getInput();

                System.out.println("Github username:");
                String username = GithubAPI.getInput();

                System.out.println("Github password:");
                String password = GithubAPI.getInput();

                System.out.println("Authorization creating note");
                String note = GithubAPI.getInput();

                String token = getNewAuthorization(clientID, clientSecret, username, password, note);

                createNewRepo(token);
            } else {
                throw new Exception("Invalid repo command");
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return "";
    }

    private void printFullName(JsonArray jarr, int i) throws Exception {
        JsonObject jo = (JsonObject) jarr.get(i);
        checkForJsonError(jo);

        String fullName = getKeyValueFromJsonObject(jo, "full_name");
        System.out.println(fullName);
    }

    public String checkForJsonError(JsonObject jo) throws Exception {

        String message = null;
        try {
            message = getKeyValueFromJsonObject(jo, "message");
            throw new Exception(message);
        } catch (NullPointerException e) {
        }
        return message;

    }

    public String getKeyValueFromJsonObject(JsonObject jo, String key) {
        String keyValue = jo.get(key).toString();
        keyValue = keyValue.substring(1, keyValue.length() - 1);
        return keyValue;
    }

    public JsonArray getJsonArray(String url) throws IOException, JsonSyntaxException, ParseException, Exception {
        String json = getJsonString(url);
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        JsonArray jarr = jsonObject.getAsJsonArray("items");
        if (jarr == null) {
            throw new Exception("No repositories found");
        }
        return jarr;
    }

    public JsonArray getJsonRepoInformation(String url, String repoID) throws IOException, JsonSyntaxException, ParseException, Exception {
        String json = getJsonString(url);
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        JsonArray jarr = jsonObject.getAsJsonArray("items");
        if (jarr == null) {
            throw new Exception("Repository with id: " + repoID + " not found");
        }
        return jarr;
    }

    private static String getJsonString(String url1) throws ParseException, IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url1);
        request.addHeader("content-type", "application/json");
        HttpResponse result = httpClient.execute(request);
        String json = EntityUtils.toString(result.getEntity(), "UTF-8");
        return json;
    }

    public String getProperty(String filename, String key) {
        InputStream stream = null;
        String value = "";
        try {
            Properties prop = new Properties();
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            stream = loader.getResourceAsStream(filename);
            prop.load(stream);

            // get the property value and print it out
            value = prop.getProperty(key);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return value;
    }

    public String getNewAuthorization(String clientID, String clientSecret, String username, String password, String note) throws Exception {
        String url = getProperty("access.properties", "urlAuthorization");
        String access_token = "";

        Header header1 = BasicScheme.authenticate(
                new UsernamePasswordCredentials(username, password),
                "UTF-8", false);
        Header header2 = new BasicHeader("content-type", "application/json");
        StringEntity params = new StringEntity("{ \n"
                + "        \"note\": \"" + note + "\", \n"
                + "        \"client_id\": \"" + clientID + "\",\n"
                + "        \"client_secret\": \"" + clientSecret + "\",\n"
                + "        \"scopes\": [\n"
                + "    \"public_repo\"\n"
                + "  ]\n"
                + "      }");

        JsonObject jsonObject = getJsonObjectFromPostRequest(url, header1, header2, params);
        checkForJsonError(jsonObject);
        access_token = getKeyValueFromJsonObject(jsonObject, "token");

        if (access_token == null) {
            throw new Exception("Bad credentials");
        }
        System.out.println(access_token);

        return access_token;
    }

    public void createNewRepo(String token) throws Exception {
        String url = getProperty("access.properties", "urlRepo");

        System.out.println("Name of the repo you are creating");
        String name = GithubAPI.getInput();

        System.out.println("Description of the repo you are creating");
        String description = GithubAPI.getInput();

        Header header1 = new BasicHeader("Authorization", "token " + token);
        Header header2 = new BasicHeader("content-type", "application/json");
        StringEntity params = new StringEntity("{ \n"
                + "        \"name\": \"" + name + "\", \n"
                + "        \"description\": \"" + description + "\"\n"
                + "      }");

        JsonObject jsonObject = getJsonObjectFromPostRequest(url, header1, header2, params);
        checkForJsonError(jsonObject);
        String fullName = getKeyValueFromJsonObject(jsonObject, "full_name");

        String id = jsonObject.get("id").toString();
        System.out.println("Id of the created repository:" + id + ", full repository name:" + fullName);

    }

    private JsonObject getJsonObjectFromPostRequest(String url, Header header1, Header header2, StringEntity params) throws Exception {
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(url);
            request.addHeader(header1);
            request.addHeader(header2);

            request.setEntity(params);
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            return jsonObject;
        } catch (IOException | ParseException | JsonSyntaxException exception) {
            throw new Exception(exception.getMessage());
        }
    }

}
