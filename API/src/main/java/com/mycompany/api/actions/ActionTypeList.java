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
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
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
public class ActionTypeList implements ActionCommand {

    String[] parts;
    String url = getProperty("access.properties", "urlAllRepositories");

    public ActionTypeList(String[] parts) {
        this.parts = parts;
    }

    @Override
    public void execute() {
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
                String url = getProperty("access.properties", "urlRepositoriesByLanguage") + language + "&page=1";
            }
            getRepositories(number, url);

        } catch (NumberFormatException numberFormatException) {
            System.out.println("Incorrect number format");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void getRepositories(int numberOfRepositories, String url) throws Exception {
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
                url = url.substring(0, url.lastIndexOf("=")+1) + pageNumber;
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

    private static void printFullName(JsonArray jarr, int i) throws Exception {
        JsonObject jo = (JsonObject) jarr.get(i);
        checkForJsonError(jo);

        String fullName = getKeyValueFromJsonObject(jo, "full_name");
        System.out.println(fullName);
    }

    public static void checkForJsonError(JsonObject jo) throws Exception {

        String message = null;
        try {
            message = getKeyValueFromJsonObject(jo, "message");
            throw new Exception(message);
        } catch (Exception e) {
        }

    }

    public static String getKeyValueFromJsonObject(JsonObject jo, String key) {
        String keyValue = jo.get(key).toString();
        keyValue = keyValue.substring(1, keyValue.length() - 1);
        return keyValue;
    }

    public static JsonArray getJsonArray(String url1) throws IOException, JsonSyntaxException, ParseException, Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url1);
        request.addHeader("content-type", "application/json");
        HttpResponse result = httpClient.execute(request);
        String json = EntityUtils.toString(result.getEntity(), "UTF-8");

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        JsonArray jarr = jsonObject.getAsJsonArray("items");
        if (jarr == null) {
            throw new Exception("No repositories found ");
        }
        return jarr;
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

}
