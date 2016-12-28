/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api.actions;

/**
 *
 * @author Magdalina Civovic
 */
public interface IGithubProvider {
    
    String[] executeActionTypeDesc(String[] parts);
    String executeActionTypeExit(String command);
    String executeActionTypeHelp(String command);
    String executeActionTypeList(String[] parts);
    String executeActionTypeRepo(String command);
    
    
}
