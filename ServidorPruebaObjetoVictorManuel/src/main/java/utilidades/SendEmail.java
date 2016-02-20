/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

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
    public static boolean sendEmail(String emailTo,String emailFrom,String localhost,int port, String subject,String msg){
        boolean correcto=false;
        try {
            Email email = new SimpleEmail();
            email.setHostName(localhost);
            email.setSmtpPort(port);
            email.setAuthenticator(new DefaultAuthenticator("pp", "prueba2015"));
            email.setSSLOnConnect(false);
            email.setFrom(emailFrom);
            email.setSubject(subject);
            email.setMsg(msg);
            email.addTo(emailTo);
            email.send();
            correcto=true;
            System.out.println("OK");
        } catch (EmailException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }
    
}
