/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante;

import java.util.concurrent.TimeUnit;
/**
 *
 * @author dam2
 */
public class Buyer implements Runnable{
    private Almacen alm;
    private int timeBy;
    private Stopper stop;
    
    public Buyer(Almacen alm, int timeBy,Stopper stop){
        this.alm=alm;
        this.timeBy=timeBy;
        this.stop=stop;
    }
    
    @Override
    public void run() {
        while(!stop.isStop()){
            try {
                TimeUnit.SECONDS.sleep(timeBy);
            } catch (InterruptedException ex) {
                stop.setStop(true);
            }
            synchronized(alm){
                if(alm.reponer()){
                    System.out.println("reponedor repone ingrediente "+alm.toString());
                    alm.notifyAll();
                }else{
                    try {
                        alm.wait();
                    } catch (InterruptedException ex) {
                        stop.setStop(true);
                    }
                }
            }
        }
    }
    
}
