package com.mycompany.api.actions;

/**
 *
 * @author Magdalina Civovic
 * Mocked command provider
 */
public class MockedGithubProvider implements IGithubProvider {

    @Override
    public String[] executeActionTypeDesc(String[] parts) {
        String[] list = null;

        if (parts[0].equals("megicivovic") && parts[1].equals("fasfa/ffdsfsd")) {
            list = new String[2];
            list[0] = "Wrong repo id format: megicivovic";
            list[1] = "Repository with id: fasfa/ffdsfsd not found";
        }

        if (parts[0].equals("megicivovic/githubAPI") && parts[1].equals("megicivovic/eulerproblems")) {
            list = new String[2];
            list[0] = "repository full name:megicivovic/githubAPI, creation date:2016-12-24T10:33:44Z";
            list[1] = "repository full name:megicivovic/eulerproblems, creation date:2015-12-09T20:25:57Z";
        }

        if (parts[0].equals("")) {
            list = new String[1];
            try {
                GithubProvider.getInstance().displayHelp("description_command.txt");
                list[0] = "";
            } catch (Exception ex) {
                list[0] = ex.getMessage();
            }
        }
        return list;
    }

    @Override
    public String executeActionTypeExit(String command) {
        if (command.equals("jkl")) {
            return "Invalid exit command";
        }
        return "";
    }

    @Override
    public String executeActionTypeHelp(String command) {
        if (command.equals("ghi")) {
            return "Invalid help command";
        }
        return "";
    }

    @Override
    public String[] executeActionTypeList(String[] parts) {

        String[] list = null;

        if (parts[0].equals("-n") && parts[1].equals("2")) {
            list = new String[2];
            list[0] = "skrp/SICC";
            list[1] = "khkh3203/invisibleDragon";
        }

        if (parts[0].equals("2") && parts[1].equals("")) {
            list = new String[1];
            list[0] = "No repositories found";
        }

        if (parts[0].equals("")) {
            list = new String[100];
        }
        return list;

    }

    @Override
    public String executeActionTypeRepo(String command) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNewAuthorization(String clientID, String clientSecret, String username, String password, String note) throws Exception {
        String result = null;
        if (clientID.equals("2134") && clientSecret.equals("2feb00") && username.equals("megicivovic") && password.equals("*****") && note.equals("dd")) {
            result = "fdkj943y2hfk";
        }
        if (clientID.equals("213") && clientSecret.equals("2feb") && username.equals("megicivovic,") && password.equals("*****") && note.equals("dd")) {
            result = "Bad credentials";
        }
        if (clientID.equals("2134937") && clientSecret.equals("2feb006b815111da9d9e65280e39aafbec2bdf41") && username.equals("megicivovic") && password.equals("*****") && note.equals("dd")) {
            result = "Invalid OAuth application client_id or secret.";
        }
        if (username == null) {
            if (clientID.equals("2134937") && clientSecret.equals("2feb006b815111da9d9e65280e39aafbec2bdf41") && password.equals("*****") && note.equals("dd")) {
                result = "Username may not be null";
            }
        }
        return result;
    }

    @Override
    public String createNewRepo(String token) throws Exception {
        String result = null;
        if (token.equals("fsgs")) {
            result = "megicivovic/myCoolRepo";
        } else if (token.equals("fsgsgdfgd")) {
            result = "Bad credentials";
        }
        return result;
    }

}
