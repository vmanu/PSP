/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import static constantes.Constantes.*;
import java.util.concurrent.TimeUnit;
import lugares.Pista;

/**
 *
 * @author dam2
 */
public class Arbitro implements Runnable{
    private ArrayList<Condition> numeros;
    private Condition eligenumero;
    private ArrayList<Integer> numerosDichos;
    private Thread yo;
    private boolean interrumpido;
    private Pista pista;
    
    public Arbitro(ArrayList<Condition> numeros, Condition eligeNumero,Pista pista){
        this.numeros=numeros;
        this.pista=pista;
        this.eligenumero=eligeNumero;
        yo=new Thread(this);
        yo.setName("Arbitro");
        yo.start();
        numerosDichos=new ArrayList();
        interrumpido=false;
    }
    
    public void eligeNumero(){
        int pos=-1;
        do{
            pos=(int)(Math.random()*(NUMERO_NIÑOS/2));
        }while(numerosDichos.contains(pos));
        numerosDichos.add(pos);
        numeros.get(pos).signalAll();
        System.out.println(numeros.get(pos).toString().substring(numeros.get(pos).toString().indexOf("@")+1));
    }

    @Override
    public void run() {
        while(!interrumpido&&numerosDichos.size()<(NUMERO_NIÑOS/2)){
            try {
                if(numerosDichos.size()!=0){
                    TimeUnit.MILLISECONDS.sleep(1500);
                }
                pista.getLock().lock();
                eligenumero.await();
                eligeNumero();
            } catch (InterruptedException ex) {
                
            } finally{
                pista.getLock().unlock();
            }
        }
        System.out.println(pista.dimeWinner());
    }
    
    public void interrumpe(){
        interrumpido=true;
        yo.interrupt();
    }
}
