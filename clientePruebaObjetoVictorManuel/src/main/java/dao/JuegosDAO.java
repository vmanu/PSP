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
import static constants.Constantes.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.objetopruebavictormanuel.Juego;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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
                //String ent=EntityUtils.toString(entity1);
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                juegos = mapper.readValue(entity1.getContent(),
                        new TypeReference<ArrayList<Juego>>() {
                        });
            } finally {
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
        System.out.println("JUEGOS: " + juegos.toString() + ", tamano: " + juegos.size());
        return juegos;
    }

    public void updateJuegos(Juego j) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://localhost:8080/ControllerJuegos?opcion=update");
            ObjectMapper mapper = new ObjectMapper();
            String juegoJson = mapper.writeValueAsString(j);
            httpPost.setHeader("juego", juegoJson);
            httpclient.execute(httpPost);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertJuego(Juego j) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://localhost:8080/ControllerJuegos?opcion=insert");
            ObjectMapper mapper = new ObjectMapper();
            String juegoJson = mapper.writeValueAsString(j);
            httpPost.setHeader("juego", juegoJson);
            CloseableHttpResponse response1 = httpclient.execute(httpPost);
            try {
                HttpEntity entity1 = response1.getEntity();
                //String ent=EntityUtils.toString(entity1);
                mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                int id = mapper.readValue(entity1.getContent(),
                        new TypeReference<Integer>() {
                        });
                j.setId(id);
            } finally {
                response1.close();
            }          

        } catch (JsonProcessingException ex) {
            Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
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
            httpPost.setHeader("juego", juegoJson);
            CloseableHttpResponse response1 = httpclient.execute(httpPost);
            try {
                HttpEntity entity1 = response1.getEntity();
                //String ent=EntityUtils.toString(entity1);
                mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                response = mapper.readValue(entity1.getContent(),
                        new TypeReference<Boolean>() {
                        });
            } finally {
                response1.close();
            }       
        } catch (JsonProcessingException ex) {
            Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
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
            } finally {
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
