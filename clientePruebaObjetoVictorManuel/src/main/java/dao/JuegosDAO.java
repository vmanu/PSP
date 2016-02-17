/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.objetopruebavictormanuel.*;
import java.io.IOException;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author dam2
 */
public class JuegosDAO {

    public ArrayList<Juego> getAllJuegos() {
        ArrayList<Juego> juegos = new ArrayList();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet("http://localhost:8080/ControllerJuegos?opcion=get");
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            try {
                HttpEntity entity1 = response1.getEntity();
                String ent=EntityUtils.toString(entity1);
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                juegos = mapper.readValue(PasswordHash.descifra(Base64.decodeBase64(ent.getBytes("UTF-8"))), new TypeReference<ArrayList<Juego>>() {});
                /*juegos = mapper.readValue(entity1.getContent(),
                        new TypeReference<ArrayList<Juego>>() {
                        });*/
            } finally {
                response1.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("JUEGOS: " + juegos.toString() + ", tamano: " + juegos.size());
        return juegos;
    }

    public void updateJuegos(Juego j) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://localhost:8080/ControllerJuegos?opcion=update");
            ObjectMapper mapper = new ObjectMapper();
            String juegoJson = mapper.writeValueAsString(j);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            //EN ESTA LINEA QUE VIENE, PASAMOS UN JUEGO CIFRADO (PasswordHash.cifra(juegoJson))
            nvps.add(new BasicNameValuePair("juego",new String(Base64.encodeBase64(PasswordHash.cifra(juegoJson)))));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            httpclient.execute(httpPost);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertJuego(Juego j) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://localhost:8080/ControllerJuegos?opcion=insert");
            ObjectMapper mapper = new ObjectMapper();
            String juegoJson = mapper.writeValueAsString(j);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            //EN ESTA LINEA QUE VIENE, PASAMOS UN JUEGO CIFRADO (PasswordHash.cifra(juegoJson))
            nvps.add(new BasicNameValuePair("juego",new String(Base64.encodeBase64(PasswordHash.cifra(juegoJson)))));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response1 = httpclient.execute(httpPost);
            try {
                HttpEntity entity1 = response1.getEntity();
                //String ent=EntityUtils.toString(entity1);
                /*mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                int id = mapper.readValue(entity1.getContent(),
                        new TypeReference<Integer>() {
                        });*/
                String ent=EntityUtils.toString(entity1);
                mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                int id = mapper.readValue(PasswordHash.descifra(Base64.decodeBase64(ent.getBytes("UTF-8"))), new TypeReference<Integer>() {});
                j.setId(id);
            } finally {
                response1.close();
            }          

        } catch (JsonProcessingException ex) {
            Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean deleteJuego(int idJuego) {
        boolean response=false;
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://localhost:8080/ControllerJuegos?opcion=remove");
            ObjectMapper mapper = new ObjectMapper();
            String juegoJson = mapper.writeValueAsString(idJuego);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            //EN ESTA LINEA QUE VIENE, PASAMOS UN JUEGO CIFRADO (PasswordHash.cifra(juegoJson))
            nvps.add(new BasicNameValuePair("juego",new String(Base64.encodeBase64(PasswordHash.cifra(juegoJson)))));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response1 = httpclient.execute(httpPost);
            try {
                HttpEntity entity1 = response1.getEntity();
                //String ent=EntityUtils.toString(entity1);
                /*mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                response = mapper.readValue(entity1.getContent(),
                        new TypeReference<Boolean>() {
                        });*/
                String ent=EntityUtils.toString(entity1);
                mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                response = mapper.readValue(PasswordHash.descifra(Base64.decodeBase64(ent.getBytes("UTF-8"))), new TypeReference<Boolean>() {});
            } finally {
                response1.close();
            }       
        } catch (JsonProcessingException ex) {
            Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    public LinkedHashMap<Integer, String> getAllTiposJuegos() {
        LinkedHashMap<Integer, String> tipos = new LinkedHashMap<>();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet("http://localhost:8080/ControllerTipos");
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            try {
                System.out.println(response1.getStatusLine());
                HttpEntity entity1 = response1.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                tipos = mapper.readValue(entity1.getContent(),
                        new TypeReference<LinkedHashMap<Integer, String>>() {
                        });
                /*String ent=EntityUtils.toString(entity1);
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                tipos = mapper.readValue(PasswordHash.descifra(Base64.decodeBase64(ent.getBytes("UTF-8"))), new TypeReference<LinkedHashMap<Integer, String>>() {});
            } catch (Exception ex) {
                Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
            */} finally {
                response1.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("TIPOS: " + tipos.toString());
        return tipos;
    }
}
