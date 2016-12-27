/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
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
class ActionTypeRepo implements ActionCommand {

    @Override
    public void execute() {
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
    }

    private static String getNewAuthorization(String clientID, String clientSecret, String username, String password, String note) {
        String url = "https://api.github.com/authorizations";
        String access_token = "";
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
            access_token = jsonObject.get("token").toString();

            System.out.println(access_token);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return access_token;
    }

    private static void createNewRepo(String token) {
        String url = "https://api.github.com/user/repos";
        try {
            System.out.println("Name of the repo you are creating");
            String name = GithubAPI.getInput();

            System.out.println("Description of the repo you are creating");
            String description = GithubAPI.getInput();

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
            System.out.println(ex.getMessage());
        }
    }

}
