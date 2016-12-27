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
import java.util.Properties;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Megi
 */
class ActionTypeList implements ActionCommand {

    String language;
    int number;
    String url = "https://api.github.com/search/repositories?q=in&sort=updated&order=desc&page=1";

    public ActionTypeList(String language, int number) {
        this.language = language;
        this.number = number;
    }

    @Override
    public void execute() {
        if (!language.equals("")) {
            url = "https://api.github.com/search/repositories?&q=language:" + language + "&sort=updated&order=desc&page=1";
        }
        getRepositories(number, url);
    }

    private static void getRepositories(int numberOfRepositories, String url) {
        try {
            int pageNumber=1;
            int defaultNumberOfRepositories = Integer.parseInt(getProperty("access.properties", "defaultNumberOfRepos"));

            if (numberOfRepositories == 0) {
                numberOfRepositories = defaultNumberOfRepositories;
            }

            while (numberOfRepositories > 100) {
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                HttpGet request = new HttpGet(url);
                request.addHeader("content-type", "application/json");
                HttpResponse result = httpClient.execute(request);
                String json = EntityUtils.toString(result.getEntity(), "UTF-8");

//// TODO: 26-Dec-16  put in one method
                JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                JsonArray jarr = jsonObject.getAsJsonArray("items");

                for (int i = 0; i < numberOfRepositories; i++) {
                    JsonObject jo = (JsonObject) jarr.get(i);
                    String fullName = jo.get("full_name").toString();
                    fullName = fullName.substring(1, fullName.length() - 1);
                    System.out.println(fullName);
                    
                    numberOfRepositories-=100;
                    pageNumber++;
                    url=url.substring(0,url.lastIndexOf("="))+pageNumber;
                }

            }
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            request.addHeader("content-type", "application/json");
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");

//// TODO: 26-Dec-16  put in one method
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            JsonArray jarr = jsonObject.getAsJsonArray("items");

            for (int i = 0; i < numberOfRepositories; i++) {
                JsonObject jo = (JsonObject) jarr.get(i);
                String fullName = jo.get("full_name").toString();
                fullName = fullName.substring(1, fullName.length() - 1);
                System.out.println(fullName);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static String getProperty(String filename, String key) {
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

}
