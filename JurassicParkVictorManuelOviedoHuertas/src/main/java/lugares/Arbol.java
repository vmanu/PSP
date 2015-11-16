/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugares;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import static lugares.Lugares.*;
import static lugares.Constantes.*;
import modelo.Dinosaurio;

/**
 *
 * @author dam2
 */
public class Arbol {
    private Lock locker;
    private Dinosaurio esperando;
    private Condition cond;
    
    public Arbol(){
        locker=new ReentrantLock();
        cond=locker.newCondition();
    }
    
    public boolean entrar(Dinosaurio dino){
        boolean entra=false;
        try{
            if(locker.tryLock(TIEMPO_ESPERA_ENTRADA_BOSQUE, TimeUnit.MILLISECONDS)){
                if(esperando==null){
                    esperando=dino;
                    entra=true;
                    cond.await(TIEMPO_ESPERA_BOSQUE, TimeUnit.SECONDS);
                    esperando=null;
                }   else{
                    if(!(!esperando.isCarnivoro()&&!dino.isCarnivoro())){//SOLO NO ENTRA SI AMBOS SON HERVIVOROS, QUE NO PELEARIAN
                        entra=true;
                        dino.luchar(esperando);
                        dino.setLugarActual(HABITAT);
                        cond.signalAll();
                        esperando=null;
                    }
                }
                locker.unlock();
            }
        }catch(InterruptedException e){
            cond.signalAll();
        }
        return entra;
    }
}
