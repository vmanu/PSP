/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.LoginDAO;
import java.util.ArrayList;

/**
 *
 * @author dam2
 */
public class ControllerServiceLogin {

    public boolean compruebaLoginUnico(String newUser) {
        boolean unico=true;
        ArrayList<String> users=getAllUsers();
        for(String user:users){
            if(user.equals(newUser)){
                unico=false;
                System.out.println("Entramos en: no es login unico");
                break;
            }
        }
        return unico;
    }
    
    public ArrayList<String> getAllUsers(){
        return new LoginDAO().getAllUsers();
    }

    public boolean registra(String user, String pass) {
        return new LoginDAO().registra(user,pass);
    }

    public boolean login(String user, String pass) {
        return new LoginDAO().login(user,pass);
    }
}
