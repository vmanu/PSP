/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clientepruebavictormanuel;

import com.objetopruebavictormanuel.Paquete;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ClientCommon {

    public static void main(String[] args) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet("http://localhost:8080/ControllerPeliculas?op=ADD");
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            try {
                System.out.println(response1.getStatusLine());
                HttpEntity entity1 = response1.getEntity();
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    Paquete j = mapper.readValue(entity1.getContent(), new TypeReference<Paquete>() {
                    });
                    System.out.println(j.getNombre());
                } catch (IOException ex) {
                }
            } finally {
                response1.close();
            }
            ResponseHandler<Paquete> rh = new ResponseHandler<Paquete>() {
                @Override
                public Paquete handleResponse(final HttpResponse response) throws IOException {
                    Paquete j = null;
                    StatusLine statusLine = response.getStatusLine();
                    HttpEntity entity = response.getEntity();
                    if (statusLine.getStatusCode() >= 300) {
                        throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
                    }
                    if (entity == null) {
                        throw new ClientProtocolException("Response contains no content");
                    }
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        j = mapper.readValue(entity.getContent(), new TypeReference<Paquete>() {});
                    } catch (IOException ex) {
                    }
                    return j;
                }
            };
            Paquete jjj = httpclient.execute(httpGet, rh);
            System.out.println(jjj.getNombre());
        } catch (IOException ex) {
            Logger.getLogger(ClientCommon.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientCommon.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
