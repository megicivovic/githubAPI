/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api.actions;

/**
 *
 * @author Magdalina Civovic Interface for concrete and mockup command providers
 */
public interface IGithubProvider {

    /**
     * Executes repo description command
     *
     * @param parts
     * @return
     */
    String[] executeActionTypeDesc(String[] parts);

    /**
     * Executes exit command
     *
     * @param command
     * @return
     */
    String executeActionTypeExit(String command);

    /**
     * Executes help command
     *
     * @param command
     * @return
     */
    String executeActionTypeHelp(String command);

    /**
     * Executes list command
     *
     * @param parts
     * @return
     */
    String[] executeActionTypeList(String[] parts);

    /**
     * Executes repo creation command
     *
     * @param command
     * @return
     */
    String executeActionTypeRepo(String command);

    /**
     * Gets new access token from the provided parameters
     *
     * @param clientID
     * @param clientSecret
     * @param username
     * @param password
     * @param note
     * @return
     * @throws Exception
     */
    public String getNewAuthorization(String clientID, String clientSecret, String username, String password, String note) throws Exception;

    /**
     * Creates a new repository with token for authentication
     *
     * @param token
     * @return
     * @throws Exception
     */
    public String createNewRepo(String token) throws Exception;
}
