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
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Dinosaurio;
import static lugares.Constantes.*;
import static lugares.Lugares.*;

/**
 *
 * @author Victor
 */
public class QuiquiRoom {

    private List<Dinosaurio> dinos;
    private Habitat hab;
    private CyclicBarrier barrera;

    public QuiquiRoom(Habitat habitat) {
        dinos = Collections.synchronizedList(new ArrayList<Dinosaurio>());
        this.hab = habitat;
        barrera = new CyclicBarrier(2, new Runnable() {

            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(TIEMPO_FORNICACION);
                    for (Dinosaurio dinosaurio : dinos) {
                        dinosaurio.aumentaAlegria(ALEGRIA_FORNICACION);
                        dinosaurio.setLugarActual(HABITAT);
                    }
                    hab.addDinosaurio();
                    dinos.clear();
                    barrera.reset();
                } catch (InterruptedException ex) {
                    barrera.reset();
                }
            }

        });
    }

    public boolean entrar(Dinosaurio dino) {
        boolean entrar = false;
        try {
            synchronized (dinos) {
                if (dinos.size() == 0) {
                    entrar = true;
                    dinos.add(dino);
                } else {
                    if (dinos.size() == 1 && !dino.getSexo().equals(dinos.get(0).getSexo())&&(dino.isCarnivoro()&&dinos.get(0).isCarnivoro())) {
                        entrar = true;
                        dinos.add(dino);
                    }
                }
            }
            if (entrar) {
                dino.setLugarActual(PICADERO);
                barrera.await(TIEMPO_ESPERA_FORNICACION, TimeUnit.MILLISECONDS);
            }
            
        } catch (InterruptedException | BrokenBarrierException | TimeoutException ex) {
            barrera.reset();
        }
        return entrar;
    }

    void coitusInterruptus() {
        barrera.reset();
    }
}
