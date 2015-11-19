/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_ninos;

import java.util.concurrent.locks.Condition;

/**
 *
 * @author dam2
 */
public class Nino implements Runnable {
    private Condition miCondition;
    private Condition nextCondition;
    private Thread yo;
    private boolean interrumpido;
    
    public Nino(){
        interrumpido=false;
        yo=new Thread(this);
        yo.start();
    }

    public void setConditions(Condition miCondition, Condition nextCondition){
        this.miCondition=miCondition;
        this.nextCondition=nextCondition;
    }
    
    @Override
    public void run() {
        while(!interrumpido){
            
        }
    }
    
    
}
