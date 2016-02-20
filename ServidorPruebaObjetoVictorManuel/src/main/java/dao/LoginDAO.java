/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.objetopruebavictormanuel.PasswordHash;
import constants.Constantes;
import static constants.Constantes.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dam2
 */
public class LoginDAO {

    public ArrayList<String> getAllUsers() {
        ArrayList<String> users = new ArrayList<>();
        Connection connection = null;
        DBConnector con = new DBConnector();
        try {
            connection = con.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_SENTENCIA_DAME_TODO_LOGIN);
            while (rs.next()) {
                String nombre = rs.getString("LOG");
                users.add(nombre);
            }
        } catch (ClassNotFoundException ex) {
            System.err.println("Error al obtener la conexión a la base de datos.");
        } catch (SQLException ex) {
            System.err.println("Error al ejecutar sql");
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(connection!=null){
                con.cerrarConexion(connection);
            }
        }
        System.out.println("LISTA DE LOGINS: "+users);
        return users;
    }

    public boolean registra(String user, String pass) {
        boolean correcto=false;
        Connection connection = null;
        DBConnector con = new DBConnector();
        try {
            connection = con.getConnection();
            PreparedStatement stmt = connection.prepareStatement(SQL_SENTENCIA_REGISTRA_LOGIN);
            stmt.setString(1,user);
            stmt.setString(2,pass);
            stmt.executeUpdate();
            correcto=true;
        } catch (SQLException ex) {
            System.err.println("Error al ejecutar sql");
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            System.err.println("Error al obtener la conexión a la base de datos.");
        } finally {
            if(connection!=null){
                con.cerrarConexion(connection);
            }
        }
        return correcto;
    }

    public boolean login(String user, String pass) {
        boolean correcto=false;
        Connection connection = null;
        DBConnector con = new DBConnector();
        try {
            connection = con.getConnection();
            PreparedStatement stmt = connection.prepareStatement(SQL_SENTENCIA_DAME_LOGIN);
            stmt.setString(1, user);
            ResultSet rs = stmt.executeQuery();
            String passBD=rs.getString("Pass");
            correcto=PasswordHash.validatePassword(pass, passBD);
        } catch (ClassNotFoundException ex) {
            System.err.println("Error al obtener la conexión a la base de datos.");
        } catch (SQLException ex) {
            System.err.println("Error al ejecutar sql");
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("Error en la comparacion de pass");
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            System.err.println("Error en la comparacion de pass");
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(connection!=null){
                con.cerrarConexion(connection);
            }
        }
        return correcto;
    }

    public boolean activaUser(String user) {
        boolean correcto=false;
        Connection connection = null;
        DBConnector con = new DBConnector();
        try {
            connection = con.getConnection();
            PreparedStatement stmt = connection.prepareStatement(SQL_SENTENCIA_ACTIVA_LOGIN);
            stmt.setString(1, user);
            stmt.executeUpdate();
            correcto=true;
            System.out.println("SE SUPONE QUE LO HA CAMBIADO");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(connection!=null){
                con.cerrarConexion(connection);
            }
        }
        return correcto;
    }

    public void darBaja(String user) {
        Connection connection = null;
        DBConnector con = new DBConnector();
        try {
            connection = con.getConnection();
            PreparedStatement stmt = connection.prepareStatement(SQL_SENTENCIA_DELETE_LOGIN);
            stmt.setString(1,user);
            stmt.executeUpdate();
        } catch (ClassNotFoundException ex) {
            System.err.println("Error al obtener la conexión a la base de datos.");
        } catch (SQLException ex) {
            System.err.println("Error al ejecutar sql");
            Logger.getLogger(JuegosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(connection!=null){
                con.cerrarConexion(connection);
            }
        }
    }
    
}
