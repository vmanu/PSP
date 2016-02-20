/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import dao.LoginDAO;
import utilidades.SendEmail;

/**
 *
 * @author Victor
 */
public class ControllerServiceSendEmail {
    public boolean sendEmail(String emailTo,String emailFrom,String localhost,int port, String subject,String msg){
        return SendEmail.sendEmail(emailTo, emailFrom, localhost, port, subject, msg);
    }

    public void darBaja(String user) {
        new LoginDAO().darBaja(user);
    }
}
