/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.objetopruebavictormanuel.PasswordHash;
import com.objetopruebavictormanuel.Usuario;
import static constantes.Constantes.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Victor
 */
public class LoginDAO {

    public String registra(String user, String pass,CloseableHttpClient httpclient) {
        String msg="";
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/ControllerLogin");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Usuario usuario=new Usuario(user,pass);
            ObjectMapper mapper=new ObjectMapper();
            String usuarioJSON=mapper.writeValueAsString(usuario);
            nvps.add(new BasicNameValuePair("user",new String(Base64.encodeBase64(PasswordHash.cifra(usuarioJSON)))));
            nvps.add(new BasicNameValuePair("operacion",OPERACION_REGISTRO));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response=httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            msg=EntityUtils.toString(entity);
            if(msg==null){
                msg="";
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return msg;
    }

    public boolean login(String user, String pass, CloseableHttpClient httpclient) {
        boolean correcto=false;
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/ControllerLogin");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Usuario usuario=new Usuario(user,pass);
            ObjectMapper mapper=new ObjectMapper();
            String usuarioJSON=mapper.writeValueAsString(usuario);
            nvps.add(new BasicNameValuePair("user",new String(Base64.encodeBase64(PasswordHash.cifra(usuarioJSON)))));
            nvps.add(new BasicNameValuePair("operacion",OPERACION_LOGIN));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response=httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String msg=EntityUtils.toString(entity);
            if(msg==null){
                msg=MENSAJE_WRONG;
            }
            if(msg.equals(MENSAJE_OK)){
                correcto=true;
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return correcto;
    }
    
}
