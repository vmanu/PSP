/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.objetopruebavictormanuel.Juego;
import dao.JuegosDAO;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 *
 * @author dam2
 */
public class ControllerServiceTipos {
    
    public LinkedHashMap<Integer,String> getAllTipos() {
        JuegosDAO juegosDAO = new JuegosDAO();
        return juegosDAO.getAllTiposJuegos();
    }
}
