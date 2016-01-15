/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteweb;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import sun.net.www.http.HttpClient;

/**
 *
 * @author oscar
 */
public class ClienteWebCookies {

    public static void main(String[] args) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            //PRIMER NUMERO
            HttpGet httpGet = new HttpGet("http://localhost:8080/ContrasenaVictorManuelOviedo/cajaFuerte?num=1");
            HttpClientContext context = HttpClientContext.create();
            CloseableHttpResponse response1 = httpclient.execute(httpGet, context);
            try {
                HttpEntity entity1 = response1.getEntity();
                System.out.println(EntityUtils.toString(entity1, "UTF-8"));
                //SEGUNDO NUMERO
                httpGet = new HttpGet("http://localhost:8080/ContrasenaVictorManuelOviedo/cajaFuerte?num=2");
                response1 = httpclient.execute(httpGet, context);
                entity1 = response1.getEntity();
                System.out.println(EntityUtils.toString(entity1, "UTF-8"));
                //TERCER NUMERO
                httpGet = new HttpGet("http://localhost:8080/ContrasenaVictorManuelOviedo/cajaFuerte?num=3");
                response1 = httpclient.execute(httpGet, context);
                entity1 = response1.getEntity();
                System.out.println(EntityUtils.toString(entity1, "UTF-8"));
                //CONTRASEÑA
                httpGet = new HttpGet("http://localhost:8080/ContrasenaVictorManuelOviedo/password?pass=paquete");
                response1 = httpclient.execute(httpGet, context);
                entity1 = response1.getEntity();
                System.out.println(EntityUtils.toString(entity1, "UTF-8"));
            } finally {
                response1.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ClienteWebCookies.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(ClienteWebCookies.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
