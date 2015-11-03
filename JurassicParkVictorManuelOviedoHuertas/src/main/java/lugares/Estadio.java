/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugares;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import modelo.Dinosaurio;
import static lugares.Constantes.*;
import static lugares.Lugares.*;

/**
 *
 * @author dam2
 */
public class Estadio implements Runnable {

    private ArrayList<Dinosaurio> dinos;
    private boolean stop;
    private Thread estadio;
    private CyclicBarrier barrera;

    public Estadio() {
        dinos = new ArrayList();
        stop = false;
        estadio = new Thread(this);
        estadio.start();
    }

    public void parar() {
        estadio.interrupt();
    }

    public void entra(Dinosaurio di) {
        try {
            if (dinos.size() < ESTADIO_SIZE) {//USAR EL BARRIER.getWaitingNuember
                synchronized (dinos) {
                    dinos.add(di);
                    di.setLugarActual(SANTIAGO_BERNABEU);
                }
                barrera.await();
            }
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }
    }

    @Override
    public void run() {
        barrera = new CyclicBarrier(ESTADIO_SIZE, new Runnable() {

            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(TIEMPO_ESPECTACULO_ESTADIO);
                    for (Dinosaurio dino : dinos) {
                        dino.setLugarActual(HABITAT);
                        dino.aumentaAlegria();
                        
                    }
                    dinos.clear();
                    barrera.reset();
                } catch (InterruptedException ex) {
                    barrera.reset();
                }
            }
        });
    }
}
