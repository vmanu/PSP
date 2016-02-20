/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import com.objetopruebavictormanuel.PasswordHash;
import static constantes.Constantes.OPERACION_REGISTRO;
import dao.LoginDAO;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import static constantes.Constantes.*;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author oscar
 */
public class SendEmail {
    public static boolean sendEmail(String emailTo,String user,CloseableHttpClient httpclient){
        boolean correcto=false;
        String msg="";
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/ControllerSendEmail");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("user",new String(Base64.encodeBase64(PasswordHash.cifra(user)))));
            nvps.add(new BasicNameValuePair("email",emailTo));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response=httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            msg=EntityUtils.toString(entity);
            correcto=msg!=null&&msg.equals(MENSAJE_OK);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return correcto;
    }
    
}
