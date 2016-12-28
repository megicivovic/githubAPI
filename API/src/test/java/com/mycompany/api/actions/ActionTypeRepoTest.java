/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api.actions;

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
public class ActionTypeRepoTest {
      MockedProvider mockedProvider; 
    public ActionTypeRepoTest() {
        
    }
    
    @Before
       void initializeProvider(){
        mockedProvider = new MockedProvider();
       }
     /**
     * Test of getNewAuthorization method, of class ActionTypeRepo.
     */
    @Test
    public void testGetNewAuthorization() throws Exception {
        try {
            String clientID = "21349375ffd36124f919";
            String clientSecret = "2feb006b815111da9d9e65280e39aafbec2bdf41";
            String username = "megicivovic";
            String password = "Megica92*";
            String note = "dd";            
            String result = ActionTypeRepo.getNewAuthorization(clientID, clientSecret, username, password, note);
        } catch (Exception exception) {
              fail("Correct input failed");
        }
        
    }
     /**
     * Test of getNewAuthorization method, of class ActionTypeRepo.
     */
    @Test
    public void testGetNewAuthorizationBadCredentials() throws Exception {
        try {
            String clientID = "21349375ffd36124f919";
            String clientSecret = "2feb006b815111da9d9e65280e39aafbec2bdf41";
            String username = "megicivovic,";
            String password = "Megica92*";
            String note = "dd";            
            String result = ActionTypeRepo.getNewAuthorization(clientID, clientSecret, username, password, note);            
        } catch (Exception exception) {
              assertEquals("Bad credentials",exception.getMessage());
        }
        
    }
     /**
     * Test of getNewAuthorization method, of class ActionTypeRepo.
     */
    @Test
    public void testGetNewAuthorizationInvalidCLientSecretOrId() throws Exception {
        try {
            String clientID = "21349375ffd36124f919ff";
            String clientSecret = "2feb006b815111da9d9e65280e39aafbec2bdf41";
            String username = "megicivovic";
            String password = "Megica92*";
            String note = "dd";            
            String result = ActionTypeRepo.getNewAuthorization(clientID, clientSecret, username, password, note);            
        } catch (Exception exception) {
              assertEquals("Invalid OAuth application client_id or secret.",exception.getMessage());
        }
        
    }
    
    /**
     * Test of getNewAuthorization method, of class ActionTypeRepo.
     */
    @Test
    public void testGetNewAuthorizationNullUsername() throws Exception {
        try {
            String clientID = null;
            String clientSecret = null;
            String username = null;
            String password = null;
            String note = null;            
            
            new MockedProvider().
            String result = mockedProvider.getNewAuthorization(clientID, clientSecret, username, password, note);            
        } catch (Exception exception) {
              assertEquals("Username may not be nul",exception.getMessage());
        }
        
    }
    /**
     * Test of getNewAuthorization method, of class ActionTypeRepo.
     */
    @Test
    public void testCreateNewRepo() throws Exception {
        try {
            String clientID = null;
            String clientSecret = null;
            String username = null;
            String password = null;
            String note = null;            
           String token = getNewAuthorization(clientID, clientSecret, username, password, note);

                createNewRepo(token);      
        } catch (Exception exception) {
              assertEquals("Username may not be nul",exception.getMessage());
        }
        
    }
    
    
    
}
