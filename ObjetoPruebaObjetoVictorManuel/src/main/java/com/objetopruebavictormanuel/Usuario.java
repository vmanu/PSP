/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.objetopruebavictormanuel;

/**
 *
 * @author Victor
 */
public class Usuario {
    private String login;
    private String pass;
    
    public Usuario(){
        
    }
    
    public Usuario(String login,String pass){
        this.login=login;
        this.pass=pass;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    
}
