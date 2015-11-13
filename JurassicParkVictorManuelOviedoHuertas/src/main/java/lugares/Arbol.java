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
import modelo.Dinosaurio;

/**
 *
 * @author dam2
 */
public class Arbol {
    private Lock locker;
    private Dinosaurio esperando;
    private Condition cond;
    /*
    Lock (usamos tryLock)
    condition
    
    if(Lock.tryLock()){
        if(soyprimero){
            await(n segundos);
        }   else{
            luchar;
        }
        signalall()
    }
    */
    public Arbol(){
        locker=new ReentrantLock();
        cond=locker.newCondition();
    }
    
    public void entrar(Dinosaurio dino){
        try{if(locker.tryLock(1000, TimeUnit.MILLISECONDS)){
            if(esperando==null){
                esperando=dino;
                cond.await();
            }   else{
                dino.luchar(esperando);
                cond.signalAll();
            }
            locker.unlock();
        }
        }catch(InterruptedException e){
    
        }
    }
}
