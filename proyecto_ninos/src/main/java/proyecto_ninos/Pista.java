/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_ninos;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import static proyecto_ninos.Constantes.*;

/**
 *
 * @author dam2
 */
public class Pista {
    private Lock llave;
    private ArrayList<Condition> turnos;
    private ArrayList<Nino> ninos;
    private CyclicBarrier barrera;
    private int tamanoPista;
    
    
    public Pista(){
        llave=new ReentrantLock();
        tamanoPista=TAMAÑO_PISTA;
        for(int i=0;i<NUMERO_DE_NIÑOS;i++){
            new Nino((i+1),this);
            turnos.add(llave.newCondition());
        }
        barrera=new CyclicBarrier(NUMERO_DE_NIÑOS, new Runnable(){

            @Override
            public void run() {
                for(int i=0;i<NUMERO_DE_NIÑOS;i++){
                    ninos.get(i).setConditions(turnos.get(i), turnos.get(i+1%NUMERO_DE_NIÑOS));
                }
                turnos.get(0).signal();
            }
            
        });
    }
    
    public void entrar(Nino child){
        try {
            synchronized(ninos){
                ninos.add(child);
            }
            barrera.await();
        } catch (InterruptedException ex) {
            
        } catch (BrokenBarrierException ex) {
            
        }
    }
}
