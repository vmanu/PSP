/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.LoginDAO;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 *
 * @author Victor
 */
public class ControlLogin {

    public String registra(String user, String pass,CloseableHttpClient httpclient) {
        return new LoginDAO().registra(user,pass,httpclient);
    }

    public boolean login(String user, String pass, CloseableHttpClient httpclient) {
        return new LoginDAO().login(user,pass,httpclient);
    }
    
}
