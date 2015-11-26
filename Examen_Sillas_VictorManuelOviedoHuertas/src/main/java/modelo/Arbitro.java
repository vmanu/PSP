/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.concurrent.locks.Condition;
import java.util.logging.Level;
import java.util.logging.Logger;
import lugar.Sala;

/**
 * Arbitro duerme en su run y esperar a que niños despierten a arbitro
 * una vez levantado: suena musica aleatorio (determina cuando debe de analizarse quien pierde
 */
public class Arbitro implements Runnable{
    private Condition levantaParaEmpezar;
    private Sala sala;
    private Thread yo;
    
    public Arbitro(Sala sala, Condition levantaParaEmpezar) {
        this.sala=sala;
        this.levantaParaEmpezar=levantaParaEmpezar;
        yo=new Thread();
        yo.start();
    }

    @Override
    public void run() {
        try {
            sala.despiertaArbitro().countDown();
            sala.despiertaArbitro().await();
        } catch (InterruptedException ex) {
            Logger.getLogger(Arbitro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
