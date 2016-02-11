/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.JuegosDAO;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import com.objetopruebavictormanuel.Juego;

/**
 *
 * @author dam2
 */
public class ControlJuegos {

    public ArrayList<Juego> getAllJuegos() {
        ArrayList<Juego> juegos = null;
        JuegosDAO juegosDAO = new JuegosDAO();
        juegos = juegosDAO.getAllJuegos();
        return juegos;
    }
    
    public void UpdateJuego(Juego j){
        new JuegosDAO().updateJuegos(j);
    }

    public void InsertJuego(Juego juego) {
        new JuegosDAO().insertJuego(juego);
    }
    
    public boolean removeJuego(int j){
        return new JuegosDAO().deleteJuego(j);
    }

    public LinkedHashMap<Integer,String> getAllTipos() {
        JuegosDAO juegosDAO = new JuegosDAO();
        return juegosDAO.getAllTiposJuegos();
    }
}
