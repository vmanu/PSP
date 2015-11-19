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
    private boolean interrumpido;
    
    
    public Pista(){
        llave=new ReentrantLock();
        interrumpido=false;
        tamanoPista=TAMAÑO_PISTA;
        ninos=new ArrayList();
        turnos=new ArrayList();
        for(int i=0;i<NUMERO_DE_NIÑOS;i++){
            turnos.add(llave.newCondition());
        }
        barrera=new CyclicBarrier(NUMERO_DE_NIÑOS, new Runnable(){

            @Override
            public void run() {
                if(!interrumpido){
                    for(int i=0;i<NUMERO_DE_NIÑOS;i++){
                        ninos.get(i).setConditions(turnos.get(i), turnos.get((i+1)%NUMERO_DE_NIÑOS));
                    }
                    ninos.get(0).setPrimero();
                }
            }
            
        });
        for(int i=0;i<NUMERO_DE_NIÑOS;i++){
            new Nino((i+1),this);
        }
    }
    
    public Lock getLock(){
        return llave;
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
    
    public String muestraNiños(){
        StringBuffer cadena=new StringBuffer();
        cadena.append("Los niños están así:\n");
        for(Nino child:ninos){
            cadena.append(child.toString()).append("\n");
        }
        return cadena.toString();
    }
    
    public void interrumpir(){
        interrumpido=true;
        for(Nino child:ninos){
            child.interrumpir();
        }
    }
}
