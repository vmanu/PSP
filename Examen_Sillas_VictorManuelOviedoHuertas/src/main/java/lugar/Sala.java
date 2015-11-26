/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugar;

import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import modelo.Arbitro;
import modelo.Nino;
import static constantes.Constantes.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;


/**
 * Barrera en Sala que controla la asignacion de sillas (condicion)
 * 
 */ 
public class Sala {
    private CyclicBarrier barrera;
    private CountDownLatch aaaarranca;
    private Lock locker;
    private ArrayList<Condition> sillas;
    private Condition dePie;
    private ArrayList<Nino>childs;
    private Arbitro arbi;
    private boolean interrumpido;
    
    public Sala(){
        interrumpido=false;
        aaaarranca=new CountDownLatch(NUMERO_DE_NIÑOS+1);
        locker=new ReentrantLock();
        sillas=new ArrayList();
        childs=new ArrayList();
        for(int i=0;i<NUMERO_DE_NIÑOS;i++){
            if(i<(NUMERO_DE_NIÑOS-1)){
                sillas.add(locker.newCondition());
            }else{
                dePie=locker.newCondition();
            }
            new Nino("Niño "+(i+1));
        }
        arbi=new Arbitro(this,dePie);
        barrera=new CyclicBarrier(NUMERO_DE_NIÑOS, new Runnable(){

            @Override
            public void run() {
                if(!interrumpido){
                    for(int i=0;i<(NUMERO_DE_NIÑOS-1);i++){
                        childs.get(i).setSilla(sillas.get(i));
                    }
                    childs.get(NUMERO_DE_NIÑOS-1).setSilla(dePie);
                }
            }
            
        });
    }
    
    public CountDownLatch despiertaArbitro(){
        return aaaarranca;
    }
    
    public Lock getLock(){
        return locker;
    }
    
    /**
     * Metodo equivalente al "entrar" en dinosaurios
     * @param peque 
     */
    public void jugar(Nino peque){
        try {
            synchronized(childs){
                childs.add(peque);
            }
            barrera.await();
        } catch (InterruptedException ex) {
            interrumpido=true;
        } catch (BrokenBarrierException ex) {
            
        }
    }
}
