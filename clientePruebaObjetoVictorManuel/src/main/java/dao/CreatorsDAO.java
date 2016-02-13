/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import static constants.Constantes.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author Victor
 */
public class CreatorsDAO {

    public LinkedHashMap<Integer, String> getAllCreators() {
        LinkedHashMap<Integer, String> creadores = new LinkedHashMap();
        /*Connection connection = null;
        DBConnector con = new DBConnector();
        try {
            connection = con.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_SENTENCIA_DAME_TODO_CREADORES);
            while (rs.next()) {
                int id = rs.getInt("ID");
                String creador = rs.getString("NOMBRE");
                creadores.put(id, creador);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(con!=null&&connection!=null)
                con.cerrarConexion(connection);
        }*/
        
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet("http://localhost:8080/ControllerCreadores");
           

            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            
            try {
                System.out.println(response1.getStatusLine());
                HttpEntity entity1 = response1.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                creadores=mapper.readValue(entity1.getContent(),
                        new TypeReference<LinkedHashMap<Integer,String>>() {});
                System.out.println("CREADORES: "+creadores.toString());
            } finally {
                response1.close();
            }
        } catch (IOException ex) {
            //Logger.getLogger(ClientWebWithObjects.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                //Logger.getLogger(ClientWebWithObjects.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return creadores;
    }

}
