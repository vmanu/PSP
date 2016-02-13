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
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author dam2
 */
public class JuegosDAO {

    public ArrayList<Juego> getAllJuegos() {
        /*ArrayList<Juego> juegos = new ArrayList<>();
        Connection connection = null;
        DBConnector con = new DBConnector();
        try {
            connection = con.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_SENTENCIA_DAME_TODO);
            while (rs.next()) {
                int id = rs.getInt("ID");
                String nombre = rs.getString("NOMBRE");
                Date fecha_creacion = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("FECHA_CREACION"));
                int ventas=rs.getInt("VENTAS");
                int tipo=rs.getInt("GAME_TYPE");
                int creador=rs.getInt("GAME_CREATOR");
                Juego j = new Juego(id, nombre,fecha_creacion,ventas,tipo,creador);
                juegos.add(j);
            }
        } catch (ClassNotFoundException ex) {
            System.err.println("Error al obtener la conexión a la base de datos. Linea 27 JuegosDAO");
        } catch (SQLException ex) {
            System.err.println("Error al ejecutar sql");
            Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            System.err.println("Fallo al parsear Date de la tabla");
        } finally {
            if(connection!=null){
                con.cerrarConexion(connection);
            }
        }*/
        
        ArrayList<Juego> juegos = new ArrayList();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet("http://localhost:8080/ControllerJuegos?opcion=get");
           
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            
            try {
                System.out.println(response1.getStatusLine());
                HttpEntity entity1 = response1.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                System.out.println("entity1: "+entity1.getContent());
                juegos=mapper.readValue(entity1.getContent(),
                        new TypeReference<ArrayList<Juego>>() {});
                
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
        System.out.println("JUEGOS: "+juegos.toString()+", tamaño: "+juegos.size());
        return juegos;
    }

    public void updateJuegos(Juego j){
        Connection connection = null;
        DBConnector con = new DBConnector();
        try {
            connection = con.getConnection();
            PreparedStatement stmt = connection.prepareStatement(SQL_SENTENCIA_UPDATE);
            stmt.setString(1,j.getNombre());
            stmt.setString(2,new SimpleDateFormat("yyyy-MM-dd").format(j.getFecha_creacion()));
            stmt.setInt(3, j.getVentas());
            stmt.setInt(4, j.getTipo());
            stmt.setInt(5, j.getCreador());
            stmt.setInt(6, j.getId());
            stmt.executeUpdate();
        } catch (ClassNotFoundException ex) {
            System.err.println("Error al obtener la conexión a la base de datos. Linea 27 JuegosDAO");
        } catch (SQLException ex) {
            System.err.println("Error al ejecutar sql");
            Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(connection!=null){
                con.cerrarConexion(connection);
            }
        }
    }

    public void insertJuego(Juego j) {
        Connection connection = null;
        DBConnector con = new DBConnector();
        try {
            connection = con.getConnection();
            PreparedStatement stmt = connection.prepareStatement(SQL_SENTENCIA_INSERT);
            stmt.setString(1,j.getNombre());
            stmt.setString(2,new SimpleDateFormat("yyyy-MM-dd").format(j.getFecha_creacion()));
            stmt.setInt(3, j.getVentas());
            stmt.setInt(4, j.getTipo());
            stmt.setInt(5, j.getCreador());
            stmt.executeUpdate();
            Statement stmt2 = connection.createStatement();
            ResultSet rs = stmt2.executeQuery(SQL_SENTENCIA_ULTIMO_ID);
            j.setId(rs.getInt(1));
        } catch (ClassNotFoundException ex) {
            System.err.println("Error al obtener la conexión a la base de datos. Linea 27 JuegosDAO");
        } catch (SQLException ex) {
            System.err.println("Error al ejecutar sql");
            Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(connection!=null){
                con.cerrarConexion(connection);
            }
        }
    }
    
    public boolean deleteJuego(int idJuego){
        Connection connection = null;
        DBConnector con = new DBConnector();
        int rs=0;
        try {
            connection = con.getConnection();
            PreparedStatement stmt = connection.prepareStatement(SQL_SENTENCIA_DELETE);
            stmt.setInt(1,idJuego);
            rs=stmt.executeUpdate();
        } catch (ClassNotFoundException ex) {
            System.err.println("Error al obtener la conexión a la base de datos. Linea 27 JuegosDAO");
        } catch (SQLException ex) {
            System.err.println("Error al ejecutar sql");
            Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(connection!=null){
                con.cerrarConexion(connection);
            }
        }
        return rs!=0;
    }
    
    public LinkedHashMap<Integer,String> getAllTiposJuegos(){
        LinkedHashMap<Integer,String> tipos = new LinkedHashMap<>();
        /*Connection connection = null;
        DBConnector con = new DBConnector();
        try {
            connection = con.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_SENTENCIA_DAME_TODO_TIPOS);
            while (rs.next()) {
                int id = rs.getInt("ID");
                String tipo = rs.getString("TIPO");
                tipos.put(id,tipo);
            }
        } catch (ClassNotFoundException ex) {
            System.err.println("Error al obtener la conexión a la base de datos. Linea 27 JuegosDAO");
        } catch (SQLException ex) {
            System.err.println("Error al ejecutar sql");
            Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(connection!=null){
                con.cerrarConexion(connection);
            }
        }*/
        
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
                tipos=mapper.readValue(entity1.getContent(),
                        new TypeReference<LinkedHashMap<Integer,String>>() {});
                
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
        System.out.println("TIPOS: "+tipos.toString());
        return tipos;
    }
    
    /*public LinkedHashMap<Integer,String[]> getAllCreadorJuegos(){
        LinkedHashMap<Integer,String[]> tipos = new LinkedHashMap<>();
        Connection connection = null;
        DBConnector con = new DBConnector();
        try {
            connection = con.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_SENTENCIA_DAME_TODO_CREADORES);
            while (rs.next()) {
                int id = rs.getInt("ID");
                String nombre = rs.getString("NOMBRE");
                String ape=rs.getString("APELLIDOS");
                tipos.put(id,new String[]{nombre,ape});
            }
        } catch (ClassNotFoundException ex) {
            System.err.println("Error al obtener la conexión a la base de datos. Linea 27 JuegosDAO");
        } catch (SQLException ex) {
            System.err.println("Error al ejecutar sql");
            Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(connection!=null){
                con.cerrarConexion(connection);
            }
        }
        return tipos;
    }*/
}
