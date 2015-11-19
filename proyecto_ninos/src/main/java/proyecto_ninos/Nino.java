/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_ninos;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;
import static proyecto_ninos.Constantes.*;

/**
 *
 * @author dam2
 */
public class Nino implements Runnable {
    private Condition miCondition;
    private Condition nextCondition;
    private Thread yo;
    private boolean interrumpido;
    private String nombre;
    private Pista pista;
    private int distanciaRecorrida;
    private boolean soyPrimero;
    private int tiros;
    
    public Nino(int valor, Pista pista){
        interrumpido=false;
        nombre="Niño "+valor;
        soyPrimero=false;
        this.pista=pista;
        yo=new Thread(this);
        yo.start();
        yo.setName(nombre);
    }

    public void setConditions(Condition miCondition, Condition nextCondition){
        this.miCondition=miCondition;
        this.nextCondition=nextCondition;
        
    }
    
    public void tirarChapa(){
        try {
            TimeUnit.SECONDS.sleep(2);
            int random=(int)(Math.random()*(TAMAÑO_PISTA/10));
            distanciaRecorrida+=random;
            tiros++;
        } catch (InterruptedException ex) {
            interrumpido=true;
        }
    }
    
    public void interrumpir(){
        interrumpido=true;
        yo.interrupt();
    }
    
    @Override
    public String toString(){
        return nombre+" ha recorrido "+distanciaRecorrida+" metros. He tirado "+tiros+" veces.";
    }
    
    public void setPrimero(){
        soyPrimero=true;
    }
    
    @Override
    public void run() {
        pista.entrar(this);
        if(soyPrimero){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Nino.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        while(!interrumpido){
            try {
                pista.getLock().lock();
                if(soyPrimero){
                    soyPrimero=false;
                }else{
                    miCondition.await();
                }
                tirarChapa();
                if(distanciaRecorrida>=TAMAÑO_PISTA){
                    pista.interrumpir();
                }else{
                    nextCondition.signalAll();
                }
            } catch (InterruptedException ex) {
                interrumpido=true;
            } finally{
                pista.getLock().unlock();
            }
        }
    }
    
    
}
