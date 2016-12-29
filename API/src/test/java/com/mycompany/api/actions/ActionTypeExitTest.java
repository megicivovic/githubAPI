package com.mycompany.api.actions;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Magdalina Civovic
 */
public class ActionTypeExitTest {

    public ActionTypeExitTest() {
    }

    /**
     * Test of execute method, of class ActionTypeExit.
     */
    @Test
    public void testExecute() {
        MockedGithubProvider provider = new MockedGithubProvider();

        String command = "jkl";

        assertEquals("Invalid exit command", provider.executeActionTypeExit(command));
    }

}
