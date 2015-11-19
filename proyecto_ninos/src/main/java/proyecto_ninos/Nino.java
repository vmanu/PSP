/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_ninos;

import java.util.concurrent.locks.Condition;
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
    
    public Nino(int valor, Pista pista){
        interrumpido=false;
        nombre="Niño "+valor;
        this.pista=pista;
        yo=new Thread(this);
        yo.start();
    }

    public void setConditions(Condition miCondition, Condition nextCondition){
        this.miCondition=miCondition;
        this.nextCondition=nextCondition;
    }
    
    public void tirarChapa(){
        int random=(int)(Math.random()*(TAMAÑO_PISTA/10));
        distanciaRecorrida+=random;
    }
    
    public void interrumpir(){
        interrumpido=true;
    }
    
    @Override
    public void run() {
        pista.entrar(this);
        while(!interrumpido){
            try {
                miCondition.await();
                tirarChapa();
                nextCondition.signal();
            } catch (InterruptedException ex) {
                
            }
            
        }
    }
    
    
}
