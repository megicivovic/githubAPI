package com.mycompany.api.main;

import com.mycompany.api.actions.ActionCommand;
import com.mycompany.api.actions.ActionTypeExit;
import com.mycompany.api.actions.ActionTypeHelp;
import com.mycompany.api.actions.GithubProvider;
import com.mycompany.api.builders.CompositeCommandBuilder;
import com.mycompany.api.builders.DescCommandBuilder;
import com.mycompany.api.builders.ICommandBuilder;
import com.mycompany.api.builders.ListCommandBuilder;
import com.mycompany.api.builders.RepoCommandBuilder;
import java.io.Console;
import java.io.IOException;

/**
 *
 * @author Magdalina Civovic
 */
public class GithubAPI {

    static CompositeCommandBuilder mainBuilder = new CompositeCommandBuilder();
    static GithubProvider githubProvider = new GithubProvider();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        buildCompositeStructure();

        while (true) {
            System.out.print("Type a command>");

            try {
                String input = getInput();
                if (input == null || input.length() == 0) {
                    System.out.println("Command is empty or null");
                    continue;
                }
                handleCommand(input);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Builds the initial structure of command builders
     */
    private static void buildCompositeStructure() {
        CompositeCommandBuilder ghtoolBuilder = new CompositeCommandBuilder();
        ghtoolBuilder.registerBuilder("list", new ListCommandBuilder());
        ghtoolBuilder.registerBuilder("desc", new DescCommandBuilder());
        ghtoolBuilder.registerBuilder("repo", new RepoCommandBuilder());
        ghtoolBuilder.registerBuilder("", new ICommandBuilder() {
            @Override
            public ActionCommand getCommand(String command) {
                return new ActionTypeHelp(command);
            }
        });

        mainBuilder.registerBuilder("ghtool", ghtoolBuilder);
        mainBuilder.registerBuilder("exit", new ICommandBuilder() {
            @Override
            public ActionCommand getCommand(String command) {
                return new ActionTypeExit(command);
            }
        });
    }

    /**
     * Handles the command from parameter
     *
     * @param command
     *
     */
    private static void handleCommand(String command) {

        try {
            ActionCommand cmd = mainBuilder.getCommand(command);
            cmd.execute(githubProvider);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * returns the input from command line
     *
     * @return
     * @throws Exception
     *
     */
    public static String getInput() throws Exception {
        Console console = System.console();
        String input = console.readLine();
        return input;   
    }
    
    public static String getPassword() throws Exception {
        Console console = System.console();
        String input = new String(console.readPassword());        
        return input;
    }

}
