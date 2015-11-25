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
import java.util.logging.Level;
import java.util.logging.Logger;
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
        System.out.println("ARBITRO ELIGIENDO NUMERO...");
        do{
            pos=(int)(Math.random()*(NUMERO_NIÑOS/2));
        }while(numerosDichos.contains(pos));
        numerosDichos.add(pos);
        numeros.get(pos).signalAll();
        System.out.println("El arbitro dice: "+numeros.get(pos).toString().substring(numeros.get(pos).toString().indexOf("@")+1));
    }

    @Override
    public void run() {
        while(!interrumpido&&numerosDichos.size()<(NUMERO_NIÑOS/2)){
            try {
                if(!numerosDichos.isEmpty()){
                    TimeUnit.MILLISECONDS.sleep(1500);
                }
                pista.getLock().lock();
                System.out.println("ARBITRO ENTRA A ESPERAR");
                eligenumero.await();
                eligeNumero();
            } catch (InterruptedException ex) {
                numeros.clear();
            } finally{
                pista.getLock().unlock();
            }
        }
        try {
            TimeUnit.SECONDS.sleep(15);
        } catch (InterruptedException ex) {
            
        }
        System.out.println(pista.dimeWinner());
    }
    
    public void interrumpe(){
        interrumpido=true;
        yo.interrupt();
    }
}
