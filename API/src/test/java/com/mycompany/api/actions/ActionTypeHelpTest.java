package com.mycompany.api.actions;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Magdalina Civovic
 */
public class ActionTypeHelpTest {

    /**
     * Test of execute method, of class ActionTypeHelp.
     */
    @Test
    public void testExecute() {
        MockedGithubProvider provider = new MockedGithubProvider();
        String command = "ghi";
        assertEquals("Invalid help command", provider.executeActionTypeHelp(command));
    }

}
