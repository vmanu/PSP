/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugares;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
public class Estadio {

    private List<Dinosaurio> dinos;
    private boolean stop;
    private Thread estadio;
    private CyclicBarrier barrera;

    public Estadio() {
        dinos = Collections.synchronizedList(new ArrayList());
        stop = false;
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

    public void entra(Dinosaurio di) {
        try {
            boolean entrar=false;//SE USA PARA SABER SI ENTRA EN AWAIT O NO
            synchronized (dinos) {
                if (dinos.size() < ESTADIO_SIZE) {//USAR EL BARRIER.getWaitingNuember
                    dinos.add(di);
                    di.setLugarActual(SANTIAGO_BERNABEU);
                    entrar=true;
                }
            }
            if(entrar){
                barrera.await();
            }
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }
    }
}
