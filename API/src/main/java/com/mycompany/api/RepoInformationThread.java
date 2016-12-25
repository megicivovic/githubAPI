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
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

class RepoInformationThread implements Runnable {

    private String repoID;

    public RepoInformationThread(String s) {
        this.repoID = s;
    }

    public void run() {
//        System.out.println(Thread.currentThread().getName() + " (Start) message = " + repoID);
        processmessage();//call processmessage method that sleeps the thread for 2 seconds  
//        System.out.println(Thread.currentThread().getName() + " (End)");//prints thread name  
    }

    private void processmessage() {
        try {
            Thread.sleep(2000);

            String[] id = repoID.split("/");
            String user = id[0];
            String repoName = id[1];

            String url = "https://api.github.com/search/repositories?access_token=69bf3fb25c745c9ed7ff899b165ee496fb6923b5&q="+repoName+"in:name+user:" + user;
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
//                SimpleDateFormat sdf = new SimpleDateFormat(date);
                System.out.println("repository full name:"+fullName+", creation date:"+date);

            } catch (IOException ex) {
                System.out.println(ex.getStackTrace());
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
