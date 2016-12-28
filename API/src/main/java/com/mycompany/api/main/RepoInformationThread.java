package com.mycompany.api.main;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mycompany.api.actions.GithubProvider;
/**
 * Thread for handling repo information command
 * @author Magdalina Civovic
 */
public class RepoInformationThread implements Runnable {

    private String repoID;
    String[] outputList;
    int index;

    public RepoInformationThread(String parts, String[] list, int i) {
        this.repoID = parts;
        this.outputList = list;
        this.index = i;
    }

    public void run() {
        try {
            processMessage();
        } catch (Exception ex) {
            synchronized (outputList) {
                outputList[index] = ex.getMessage();
            }
        }
    }
/**
 * Processes the command
 * @throws Exception 
 */
    private void processMessage() throws Exception {
        Thread.sleep(2000);

        String[] id = repoID.split("/");
        if (id.length < 2) {
            synchronized (outputList) {
                outputList[index] = "Wrong repo id format: " + id[0];
            }
        } else {
            String user = id[0];
            String repoName = id[1];

            String url = GithubProvider.getInstance().getProperty("access.properties", "urlRepoInfo") + repoName + "in:name+user:" + user;
            JsonArray jarr = GithubProvider.getInstance().getJsonRepoInformation(url, repoID);
            if (jarr == null) {
                synchronized (outputList) {
                    outputList[index] = "Repo with id: " + repoID + " doesn't exist";
                }
            }
            
            JsonObject jo = (JsonObject) jarr.get(0);
            GithubProvider.getInstance().checkForJsonError(jo);
            String fullName = GithubProvider.getInstance().getKeyValueFromJsonObject(jo, "full_name");
            String date = GithubProvider.getInstance().getKeyValueFromJsonObject(jo, "created_at");

            synchronized (outputList) {
                outputList[index] = "repository full name:" + fullName + ", creation date:" + date;
            }
        }
    }
}
