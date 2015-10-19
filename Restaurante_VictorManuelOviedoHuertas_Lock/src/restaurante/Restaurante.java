/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dam2
 */
public class Restaurante {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Pulse intro para parar");
        CountDownLatch comienzo=new CountDownLatch(5);
        CountDownLatch finalizo=new CountDownLatch(6);
        int ns = 4, nc = 2, nv = 3, np = 1, timeByVerd = 2, timeByPasta = 1, timeBySal = 5, timeByCarne = 3, timeCookVCS = 3, timeCookPS = 2;
        Almacen verduras = new Almacen("verdura", nv, 0);
        Almacen carne = new Almacen("carne", nc, 0);
        Almacen pasta = new Almacen("pasta", np, 0);
        Almacen sal = new Almacen("sal", ns, 0);
        Stopper stop = new Stopper();
        ArrayList<Almacen> recetaPasta = new ArrayList();
        recetaPasta.add(pasta);
        recetaPasta.add(sal);
        ArrayList<Almacen> recetaVerdura = new ArrayList();
        recetaVerdura.add(verduras);
        recetaVerdura.add(carne);
        recetaVerdura.add(sal);
        /*2 Cocineros -->VCS-3seg/PS-2seg
         4 Buyer-->V-2seg/C-3seg/S-5seg/P-1seg
         Thread.sleep(1000) esto duerme el hilo*/
        Cocinero cVerCar = new Cocinero(recetaVerdura, timeCookVCS, stop,comienzo,finalizo);
        Cocinero cPasta = new Cocinero(recetaPasta, timeCookPS, stop,comienzo,finalizo);
        Buyer bVerd = new Buyer(verduras, timeByVerd, stop,comienzo,finalizo);
        Buyer bCarne = new Buyer(carne, timeByCarne, stop,comienzo,finalizo);
        Buyer bSal = new Buyer(sal, timeBySal, stop,comienzo,finalizo);
        Buyer bPasta = new Buyer(pasta, timeByPasta, stop,comienzo,finalizo);
        ExecutorService ex = Executors.newFixedThreadPool(6);
        /*try {
            comienzo.await();
        } catch (InterruptedException ex1) {
            System.out.println("FALLO EN FINAL");
        }
        System.out.println("He segido en main");*/
        ex.execute(bSal);
        ex.execute(bPasta);
        ex.execute(bCarne);
        ex.execute(bVerd);
        ex.execute(cPasta);
        ex.execute(cVerCar);
        
        new Scanner(System.in).nextLine();
        stop.setStop(true);
        ex.shutdownNow();
        try {
            TimeUnit.SECONDS.sleep(3);//PONEMOS ESTOS TIEMPOS ANTES DEL COUNTDOWN PORQUE EXISTE LA POSIBILIDIDAD DE QUE EL COUNTDOWN SEA INTERRUMPIDO DEBIDO AL SHUTDOWN DEL EXECUTOR
        } catch (InterruptedException ex2) {
            
        }
        try {
            finalizo.countDown();
            finalizo.await();
        } catch (InterruptedException ex1) {
            System.out.println("FALLO EN FINAL");
        }
    }
}
