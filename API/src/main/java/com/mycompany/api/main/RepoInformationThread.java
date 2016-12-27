/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api.main;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mycompany.api.actions.ActionTypeList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RepoInformationThread implements Runnable {

    private String repoID;
    private String exceptionMessage="";

    public RepoInformationThread(String s) {
        this.repoID = s;
    }

    public void run() {
        try {
            processMessage();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            exceptionMessage=ex.getMessage();                    
        }
    }

    private void processMessage() throws Exception {
        Thread.sleep(2000);

        String[] id = repoID.split("/");
        if (id.length < 2) {
            throw new Exception("Wrong repo id format: " + id[0]);
        }
        String user = id[0];
        String repoName = id[1];

        String url = ActionTypeList.getProperty("access.properties", "urlRepoInfo") + repoName + "in:name+user:" + user;
        JsonArray jarr = ActionTypeList.getJsonArray(url);
        if (jarr == null) {
            throw new Exception("Repo with id: " + repoID + " doesn't exist");
        }

        JsonObject jo = (JsonObject) jarr.get(0);
        ActionTypeList.checkForJsonError(jo);
        String fullName = ActionTypeList.getKeyValueFromJsonObject(jo, "full_name");
        String date = ActionTypeList.getKeyValueFromJsonObject(jo, "created_at");
        System.out.println("repository full name:" + fullName + ", creation date:" + date);

    }

    public String hasException() {
        return exceptionMessage;
    }
}
