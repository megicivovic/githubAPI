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
 * @author Magdalina Civovic
 * Implements the behavior of "ghtool repo" command
 */
public class ActionTypeRepo implements ActionCommand {

    private String command = "";

    public ActionTypeRepo(String command) {
        this.command = command;
    }

      
     @Override
    public void execute(IGithubProvider githubProvider) {
        githubProvider.executeActionTypeRepo(command);
    }
}
