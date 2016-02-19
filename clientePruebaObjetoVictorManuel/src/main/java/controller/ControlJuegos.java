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
import org.apache.http.impl.client.CloseableHttpClient;

/**
 *
 * @author dam2
 */
public class ControlJuegos {

    public ArrayList<Juego> getAllJuegos(CloseableHttpClient httpclient) {
        return new JuegosDAO().getAllJuegos(httpclient);
    }
    
    public void UpdateJuego(Juego j,CloseableHttpClient httpclient){
        new JuegosDAO().updateJuegos(j,httpclient);
    }

    public void InsertJuego(Juego juego,CloseableHttpClient httpclient) {
        new JuegosDAO().insertJuego(juego,httpclient);
    }
    
    public boolean removeJuego(int j,CloseableHttpClient httpclient){
        return new JuegosDAO().deleteJuego(j,httpclient);
    }

    public LinkedHashMap<Integer,String> getAllTipos(CloseableHttpClient httpclient) {
        return new JuegosDAO().getAllTiposJuegos(httpclient);
    }
}
