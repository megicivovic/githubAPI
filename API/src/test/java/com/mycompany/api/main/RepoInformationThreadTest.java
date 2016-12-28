/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api.main;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mycompany.api.actions.ActionTypeList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Magdalina Civovic
 */
public class RepoInformationThreadTest {

    public RepoInformationThreadTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of run method, of class RepoInformationThread.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        RepoInformationThread instance = null;
        instance.run();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test processMessage
     */
    @Test
    public void testProcessMessageExistingRepository() throws Exception {
        try {
            String user = "megicivovic";
            String repoName = "githubAPI ";

            String url = ActionTypeList.getProperty("access.properties", "urlRepoInfo") + repoName + "in:name+user:" + user;
            JsonArray jarr = ActionTypeList.getJsonArray(url);

            JsonObject jo = (JsonObject) jarr.get(0);
            String message = ActionTypeList.checkForJsonError(jo);
            fail("Message key in json message wasn't empty");
        } catch (Exception exception) {
        }

    }

    /**
     * Test processMessage
     */
    @Test
    public void testProcessMessageInvalidID() {
        String message = null;
        try {
            String user = "megicivovi";
            String repoName = "githubAPI";

            String url = ActionTypeList.getProperty("access.properties", "urlRepoInfo") + repoName + "in:name+user:" + user;
            JsonArray jarr = ActionTypeList.getJsonArray(url);         
          
        } catch (Exception exception) {
            assertEquals("No repositories found", exception.getMessage());
        }

    }

}
