/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api.actions;

import com.mycompany.api.main.GithubAPI;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Megi
 */
public class ActionTypeRepo implements ActionCommand {

    private String command = "";

    public ActionTypeRepo(String command) {
        this.command = command;
    }

    @Override
    public void execute() {
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
    }

    private static String getNewAuthorization(String clientID, String clientSecret, String username, String password, String note) throws Exception {
        String url = ActionTypeList.getProperty("access.properties", "urlAuthorization");
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
            ActionTypeList.checkForJsonError(jsonObject);
            access_token = ActionTypeList.getKeyValueFromJsonObject(jsonObject, "token");

            if (access_token == null) {
                throw new Exception("Bad credentials");
            }
            System.out.println(access_token);
        
        return access_token;
    }

    private static void createNewRepo(String token) throws Exception {
        String url = ActionTypeList.getProperty("access.properties", "urlRepo");
       
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
            ActionTypeList.checkForJsonError(jsonObject);
            String fullName = ActionTypeList.getKeyValueFromJsonObject(jsonObject, "full_name");

            String id = jsonObject.get("id").toString();
            System.out.println("Id of the created repository:" + id + ", full repository name:" + fullName);
        
    }

    private static JsonObject getJsonObjectFromPostRequest(String url, Header header1, Header header2, StringEntity params) throws Exception {
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
