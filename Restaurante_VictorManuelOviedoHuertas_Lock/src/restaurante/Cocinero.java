/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dam2
 */
public class Cocinero implements Runnable {

    private int tiempo;
    private ArrayList<Almacen> ingredientes;
    private Stopper stop;
    private CountDownLatch comienzo;
    private CountDownLatch finalizo;

    public Cocinero(ArrayList<Almacen> ingredientes, int tiempo, Stopper stop, CountDownLatch comienzo, CountDownLatch finalizo) {
        this.ingredientes = ingredientes;
        this.tiempo = tiempo;
        this.stop = stop;
        this.comienzo = comienzo;
        this.finalizo = finalizo;
    }

    @Override
    public void run() {
        try {
            comienzo.countDown();
            comienzo.await();
        } catch (InterruptedException ex) {

        }
        ArrayList<Lock> lockers = new ArrayList();
        for (int i = 0; i < ingredientes.size(); i++) {
            lockers.add(ingredientes.get(i).getLock());
        }
        boolean[] waiting = new boolean[3];
        while (!stop.isStop()) {
            waiting[0] = true;
            waiting[1] = true;
            waiting[2] = true;
            try {
                TimeUnit.SECONDS.sleep(tiempo);
                for (int i = 0; i < ingredientes.size() && !stop.isStop(); i++) {
                    while (waiting[i]) {
                        try {
                            lockers.get(i).lock();
                            if (ingredientes.get(i).coger()) {
                                System.out.println("cocinero coge " + ingredientes.get(i).toString());
                                waiting[i] = false;
                                ingredientes.get(i).getEstaVacio().signalAll();
                            } else {
                                ingredientes.get(i).getEstaLleno().await();
                            }
                            if (i == ingredientes.size() - 1) {
                                System.out.print("Cocinero ");
                                for (int j = 0; j < ingredientes.size(); j++) {
                                    System.out.print(ingredientes.get(j));
                                }
                                System.out.println(" ha hecho un plato.");
                            }
                        } catch (InterruptedException ex) {
                            stop.setStop(true);
                        } finally {
                            lockers.get(i).unlock();
                            waiting[i] = false;
                        }
                    }
                }
            } catch (InterruptedException ex) {
                stop.setStop(true);
            } 
        }
        try {
            TimeUnit.SECONDS.sleep(3);//PONEMOS ESTOS TIEMPOS ANTES DEL COUNTDOWN PORQUE EXISTE LA POSIBILIDIDAD DE QUE EL COUNTDOWN SEA INTERRUMPIDO DEBIDO AL SHUTDOWN DEL EXECUTOR
        } catch (InterruptedException ex) {

        }
        try {
            finalizo.countDown();
            finalizo.await();
        } catch (InterruptedException ex) {

        }
    }
}
