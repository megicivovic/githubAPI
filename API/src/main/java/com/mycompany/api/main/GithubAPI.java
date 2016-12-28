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
import java.io.IOException;
import java.util.Scanner;

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
            System.out.println("Type a command>");

            try {
                handleCommand(getInput());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

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

    private static void handleCommand(String command) {

        try {
            ActionCommand cmd = mainBuilder.getCommand(command);
            cmd.execute(githubProvider);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static String getInput() throws Exception {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        input = scanner.nextLine().trim();
        if (input == null || input.length() == 0) {
            throw new Exception("Command is empty or null");
        }
        return input;
    }

}
