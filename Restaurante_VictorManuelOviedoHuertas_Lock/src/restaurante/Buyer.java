/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dam2
 */
public class Buyer implements Runnable {

    private Almacen alm;
    private int timeBy;
    private Stopper stop;
    private CountDownLatch comienzo;
    private CountDownLatch finalizo;

    public Buyer(Almacen alm, int timeBy, Stopper stop, CountDownLatch comienzo, CountDownLatch finalizo) {
        this.alm = alm;
        this.timeBy = timeBy;
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
        Lock lock = alm.getLock();
        while (!stop.isStop()) {
            try {
                TimeUnit.SECONDS.sleep(timeBy);
            } catch (InterruptedException ex) {
                stop.setStop(true);
            }
            try {
                lock.lock();
                if (alm.reponer()) {
                    System.out.println("reponedor repone ingrediente " + alm.toString());
                    alm.getEstaLleno().signalAll();
                } else {

                    alm.getEstaVacio().await();

                }
            } catch (InterruptedException ex) {
                stop.setStop(true);
            } finally {
                lock.unlock();
            }
        }
        System.out.println("Espero Buyer " + alm.toString());
        try {
            TimeUnit.SECONDS.sleep(3);//PONEMOS ESTOS TIEMPOS ANTES DEL COUNTDOWN PORQUE EXISTE LA POSIBILIDIDAD DE QUE EL COUNTDOWN SEA INTERRUMPIDO DEBIDO AL SHUTDOWN DEL EXECUTOR
        } catch (InterruptedException ex) {

        }
        System.out.println("Ya no espera Buyer " + alm.toString());
        try {
            finalizo.countDown();
            System.out.println("Buyer " + alm.toString() + " esta counting=" + finalizo.getCount());
            finalizo.await();
        } catch (InterruptedException ex) {

        }
    }

}
