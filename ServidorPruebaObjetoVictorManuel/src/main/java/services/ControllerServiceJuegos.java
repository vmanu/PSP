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
public class ControllerServiceJuegos {
    
    
    public ArrayList<Juego> getAllJuegos() {
        ArrayList<Juego> juegos = null;
        JuegosDAO juegosDAO = new JuegosDAO();
        juegos = juegosDAO.getAllJuegos();
        return juegos;
    }
    
    public void updateJuego(Juego j){
        new JuegosDAO().updateJuegos(j);
    }

    public void insertJuego(Juego juego) {
        new JuegosDAO().insertJuego(juego);
    }
    
    public boolean removeJuego(int j){
        return new JuegosDAO().deleteJuego(j);
    }
}
