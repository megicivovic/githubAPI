/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api.actions;

import java.util.List;

/**
 *
 * @author Magdalina Civovic
 */
public class MockedGithubProvider implements IGithubProvider{

    @Override
    public String[] executeActionTypeDesc(String[] parts) {
        String[] list = new String[2];
        if (parts[0].equals("megicivovic") && parts[1].equals("fasfa/ffdsfsd")){
        list[0]="Wrong repo id format: megicivovic";
        list[1]="Repository with id: fasfa/ffdsfsd not found";
        }
        return list;
        
    }

    @Override
    public String executeActionTypeExit(String command) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String executeActionTypeHelp(String command) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String executeActionTypeList(String[] parts) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String executeActionTypeRepo(String command) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    
}
