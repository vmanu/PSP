/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
        Cocinero cVerCar = new Cocinero(recetaVerdura, timeCookVCS, stop);
        Cocinero cPasta = new Cocinero(recetaPasta, timeCookPS, stop);
        Buyer bVerd = new Buyer(verduras, timeByVerd, stop);
        Buyer bCarne = new Buyer(carne, timeByCarne, stop);
        Buyer bSal = new Buyer(sal, timeBySal, stop);
        Buyer bPasta = new Buyer(pasta, timeByPasta, stop);
        ExecutorService ex = Executors.newFixedThreadPool(7);
        ex.execute(bSal);
        ex.execute(bPasta);
        ex.execute(bCarne);
        ex.execute(bVerd);
        ex.execute(cPasta);
        ex.execute(cVerCar);

        new Scanner(System.in).nextLine();
        stop.setStop(true);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            stop.setStop(true);
        }
        ex.shutdownNow();
        //ex.shutdown();
    }
}
