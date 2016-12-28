/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api.actions;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Magdalina Civovic
 */
public class ActionTypeListTest {

    /**
     * Test of execute method, of class ActionTypeList.
     */
    @Test
    public void testExecute() {
        MockedGithubProvider provider = new MockedGithubProvider();

        String[] parts = new String[2];

        parts[0] = "-n";
        parts[1] = "2";
        provider.executeActionTypeDesc(parts);

        assertEquals("skrp/SICC", provider.executeActionTypeList(parts)[0]);
        assertEquals("khkh3203/invisibleDragon", provider.executeActionTypeList(parts)[1]);

        parts = new String[2];

        parts[0] = "2";
        parts[1] = "";
        provider.executeActionTypeList(parts);

        assertEquals("No repositories found", provider.executeActionTypeList(parts)[0]);

        parts = new String[1];
        parts[0] = "";
        assertEquals(100, provider.executeActionTypeList(parts).length);

    }

}
