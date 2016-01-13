/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteweb;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author oscar
 */
public class SendEmail {
    public static void main(String[] args) {
        try {
            Email email = new SimpleEmail();
            //email.setHostName("smtp01.educa.madrid.org");
            email.setHostName("localhost");
            email.setSmtpPort(25);
            //email.setAuthenticator(new DefaultAuthenticator("pp", "prueba2015"));
            email.setSSLOnConnect(false);
            email.setFrom("pepo@educa.madrid.org");
            email.setSubject("TestMail");
            email.setMsg("This is a test mail ... :-)");
            email.addTo("oscar.novillo@gmail.com");
            email.send();
            System.out.println("OK");
        } catch (EmailException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
