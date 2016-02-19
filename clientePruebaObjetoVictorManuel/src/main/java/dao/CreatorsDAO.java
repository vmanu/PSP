/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.objetopruebavictormanuel.PasswordHash;
import static constants.Constantes.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Victor
 */
public class CreatorsDAO {

    public LinkedHashMap<Integer, String> getAllCreators(CloseableHttpClient httpclient) {
        LinkedHashMap<Integer, String> creadores = new LinkedHashMap();
        try {
            HttpGet httpGet = new HttpGet("http://localhost:8080/ControllerCreadores");
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            try {
                System.out.println(response1.getStatusLine());
                HttpEntity entity1 = response1.getEntity();
                String ent=EntityUtils.toString(entity1);
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                creadores = mapper.readValue(PasswordHash.descifra(Base64.decodeBase64(ent.getBytes("UTF-8"))), new TypeReference<LinkedHashMap<Integer, String>>() {});
                System.out.println("CREADORES: "+creadores.toString());
            } catch (Exception ex) {
                Logger.getLogger(CreatorsDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                response1.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(CreatorsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            /*try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(CreatorsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        }
        return creadores;
    }

}
