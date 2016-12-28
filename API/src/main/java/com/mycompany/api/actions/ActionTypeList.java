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
 * @author Magdalina Civovic Implements the behavior of "ghtool list" command
 */
public class ActionTypeList implements ActionCommand {

    String[] parts;

    public ActionTypeList(String[] parts) {
        this.parts = parts;
    }

    @Override
    public void execute(IGithubProvider githubProvider) {
        githubProvider.executeActionTypeList(parts);
    }

}
