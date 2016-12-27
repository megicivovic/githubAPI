/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

class RepoInformationThread implements Runnable {

    private String repoID;

    RepoInformationThread(String s) {
        this.repoID = s;
    }

    public void run() {
        processMessage();
    }

    private void processMessage() {
        try {
            Thread.sleep(2000);

            String[] id = repoID.split("/");
            String user = id[0];
            String repoName = id[1];

            String url = "https://api.github.com/search/repositories?q="+repoName+"in:name+user:" + user;
            try {
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                HttpGet request = new HttpGet(url);
                request.addHeader("content-type", "application/json");
                HttpResponse result = httpClient.execute(request);
                String json = EntityUtils.toString(result.getEntity(), "UTF-8");

                JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                JsonArray jarr = jsonObject.getAsJsonArray("items");

                JsonObject jo = (JsonObject) jarr.get(0);
                String fullName = jo.get("full_name").toString();
                fullName = fullName.substring(1, fullName.length() - 1);
                String date = jo.get("created_at").toString();
                System.out.println("repository full name:"+fullName+", creation date:"+date);

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        } catch (InterruptedException e) {
             System.out.println(e.getMessage());
        }
    }
}
