/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api.actions;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Magdalina Civovic
 */
public class ActionTypeDescTest {

    MockedGithubProvider provider = new MockedGithubProvider();

    public ActionTypeDescTest() {
    }

    /**
     * Test of execute method, of class ActionTypeDesc.
     */
    @Test
    public void testExecute() {
        MockedGithubProvider provider = new MockedGithubProvider();

        String[] parts = new String[2];

        parts[0] = "megicivovic/githubAPI";
        parts[1] = "megicivovic/eulerproblems";
        provider.executeActionTypeDesc(parts);

        assertEquals("repository full name:megicivovic/githubAPI, creation date:2016-12-24T10:33:44Z", provider.executeActionTypeDesc(parts)[0]);
        assertEquals("repository full name:megicivovic/eulerproblems, creation date:2015-12-09T20:25:57Z", provider.executeActionTypeDesc(parts)[1]);

        parts = new String[2];

        parts[0] = "megicivovic";
        parts[1] = "fasfa/ffdsfsd";
        provider.executeActionTypeDesc(parts);

        assertEquals("Wrong repo id format: megicivovic", provider.executeActionTypeDesc(parts)[0]);
        assertEquals("Repository with id: fasfa/ffdsfsd not found", provider.executeActionTypeDesc(parts)[1]);

        parts = new String[1];
        parts[0] = "";
        assertEquals("", provider.executeActionTypeDesc(parts)[0]);

    }

}
