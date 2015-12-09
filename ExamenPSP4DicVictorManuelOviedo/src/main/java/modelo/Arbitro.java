/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import static constantes.Constantes.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dam2
 */
public class Arbitro implements Runnable{

    private Pista pista;
    private Thread yo;
    
    public Arbitro(Pista pista){
        this.pista=pista;
        yo=new Thread(this);
        yo.start();
        yo.setName("Arbitro");
    }
    
    @Override
    public void run() {
        try {
            pista.getComienza().await();
            ArrayList<Atleta> corredores=pista.getCorredores();
            corredores.get(0).empiezaCorrer(0);
            corredores.get(1).empiezaCorrer(0);
            corredores.get(2).empiezaCorrer(0);
            corredores.get(3).empiezaCorrer(0);
            pista.getFinaliza().await();
            HashMap <String,Integer> equipos=pista.getResultados();
            System.out.println("El equipo1 tiene: "+equipos.get(EQUIPO1));
            System.out.println("El equipo2 tiene: "+equipos.get(EQUIPO2));
            System.out.println("El equipo3 tiene: "+equipos.get(EQUIPO3));
            System.out.println("El equipo4 tiene: "+equipos.get(EQUIPO4));
            System.out.println("El equipo ganador es: "+pista.getEquipoGanador());
        } catch (InterruptedException ex) {
            Logger.getLogger(Arbitro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
