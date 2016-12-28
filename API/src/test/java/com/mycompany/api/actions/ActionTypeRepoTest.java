/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api.actions;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Magdalina Civovic
 */
public class ActionTypeRepoTest {

    MockedGithubProvider provider = new MockedGithubProvider();

    public ActionTypeRepoTest() {

    }

    /**
     * Test of getNewAuthorization method, of class ActionTypeRepo.
     */
    @Test
    public void testGetNewAuthorization() throws Exception {

        String clientID = "2134";
        String clientSecret = "2feb00";
        String username = "megicivovic";
        String password = "*****";
        String note = "dd";
        String result = provider.getNewAuthorization(clientID, clientSecret, username, password, note);

        assertEquals("fdkj943y2hfk", result);

    }

    /**
     * Test of getNewAuthorization method, of class ActionTypeRepo.
     */
    @Test
    public void testGetNewAuthorizationBadCredentials() throws Exception {

        String clientID = "213";
        String clientSecret = "2feb";
        String username = "megicivovic,";
        String password = "*****";
        String note = "dd";
        String result = provider.getNewAuthorization(clientID, clientSecret, username, password, note);

        assertEquals("Bad credentials", result);

    }

    /**
     * Test of getNewAuthorization method, of class ActionTypeRepo.
     */
    @Test
    public void testGetNewAuthorizationInvalidCLientSecretOrId() throws Exception {

        String clientID = "2134937";
        String clientSecret = "2feb006b815111da9d9e65280e39aafbec2bdf41";
        String username = "megicivovic";
        String password = "*****";
        String note = "dd";
        String result = provider.getNewAuthorization(clientID, clientSecret, username, password, note);

        assertEquals("Invalid OAuth application client_id or secret.", result);

    }

    /**
     * Test of getNewAuthorization method, of class ActionTypeRepo.
     */
    @Test
    public void testGetNewAuthorizationNullUsername() throws Exception {

        String clientID = "2134937";
        String clientSecret = "2feb006b815111da9d9e65280e39aafbec2bdf41";
        String username = null;
        String password = "*****";
        String note = "dd";
        try {
            String result = provider.getNewAuthorization(clientID, clientSecret, username, password, note);
            fail("Username passed as null!");
        } catch (Exception e) {

        }

    }

    /**
     * Test of getNewAuthorization method, of class ActionTypeRepo.
     */
    @Test
    public void testCreateNewRepo() throws Exception {

        String token = "fsgs";
        String result = provider.createNewRepo(token);

        assertEquals("megicivovic/myCoolRepo", result);

        token = "fsgsgdfgd";
        result = provider.createNewRepo(token);

        assertEquals("Bad credentials", result);

    }

}
