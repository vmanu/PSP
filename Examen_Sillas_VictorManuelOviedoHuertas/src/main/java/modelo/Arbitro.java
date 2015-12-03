/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.logging.Level;
import java.util.logging.Logger;
import lugar.Sala;
import static constantes.Constantes.*;

/**
 * Arbitro duerme en su run y esperar a que niÃ±os despierten a arbitro
 * una vez levantado: suena musica aleatorio (determina cuando debe de analizarse quien pierde
 */
public class Arbitro implements Runnable{
    private Condition levantaParaEmpezar;
    private Sala sala;
    private Thread yo;
    
    public Arbitro(Sala sala, Condition levantaParaEmpezar) {
        this.sala=sala;
        this.levantaParaEmpezar=levantaParaEmpezar;
        yo=new Thread(this);
        yo.start();
        yo.setName("Arbitro");
    }

    @Override
    public void run() {
        try {
            sala.getCountDownLatch().countDown();
            sala.getCountDownLatch().await();
            sala.getLock().lock();
            levantaParaEmpezar.signalAll();
            sala.getLock().unlock();
            TimeUnit.SECONDS.sleep((NUMERO_DE_NIÑOS)+((int)(Math.random()*10)));
            sala.pararMusica();
        } catch (InterruptedException ex) {
            sala.pararMusica();
        } finally{
            
        }
    }

    public void interrumpir() {
        yo.interrupt();
    }
}
