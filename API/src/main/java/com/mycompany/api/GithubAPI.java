package com.mycompany.api;

import java.io.IOException;
import java.util.Scanner;


/**
 *
 * @author Megi
 */
public class GithubAPI {

    private static Scanner scanner = new Scanner(System.in);
    private static CompositeCommandBuilder mainBuilder = new CompositeCommandBuilder();

    ;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        CompositeCommandBuilder ghtoolBuilder = new CompositeCommandBuilder();
        ghtoolBuilder.registerBuilder("list", new ListCommandBuilder());
        ghtoolBuilder.registerBuilder("desc", new DescCommandBuilder());
        ghtoolBuilder.registerBuilder("repo", new RepoCommandBuilder());
        ghtoolBuilder.registerBuilder("", new ICommandBuilder() {
            @Override
            public ActionCommand getCommand(String command) {
                return new ActionTypeHelp();
            }
        });

        mainBuilder.registerBuilder("ghtool", ghtoolBuilder);
        mainBuilder.registerBuilder("exit", new ICommandBuilder() {
            @Override
            public ActionCommand getCommand(String command) {
                return new ActionTypeExit();
            }
        });

        while (true) {
            System.out.println("Type a command>");
            handleCommand(getInput());
        }
    }

    private static void handleCommand(String command) {

        try {
            ActionCommand cmd = mainBuilder.getCommand(command);
            cmd.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void validateInput(String command) throws Exception {
        if (command == null || command.length() == 0) {
            throw new Exception("Command is empty or null");
        }
    }

    static String getInput() {
        String input = "";
        try {
            input = scanner.nextLine().trim();
            validateInput(input);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return input;
    }

}
