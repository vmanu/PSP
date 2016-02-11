/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static constants.Constantes.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Victor
 */
public class CreatorsDAO {

    public LinkedHashMap<Integer, String> getAllCreators() {
        LinkedHashMap<Integer, String> creadores = new LinkedHashMap();
        Connection connection = null;
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
        }
        return creadores;
    }

}
