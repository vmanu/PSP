/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import lugares.Pista;
import static constantes.Constantes.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dam2
 */
public class Nino implements Runnable {
    private Condition miCondition;
    private Thread yo;
    private boolean interrumpido;
    private String nombre;
    private Pista pista;
    private boolean soyPrimero;
    private int velocidad;
    
    public Nino(int valor, Pista pista){
        interrumpido=false;
        nombre="Niño "+valor;
        velocidad=(int)(Math.random()*20)+20;
        soyPrimero=false;
        this.pista=pista;
        yo=new Thread(this);
        yo.start();
        yo.setName(nombre);
    }
    
    public void setNumero(Condition get) {
        miCondition=get;
    }
    
    public void correrCasa(boolean salido){
        int velocidadTemporal=velocidad;
        if(!salido){
            try {
                int distancia=0;
                while(distancia<LONGITUD_CARRERA){
                    TimeUnit.MILLISECONDS.sleep(TIEMPO_EJECUCION_VELOCIDAD);
                    distancia+=velocidadTemporal;
                    if(distancia<LONGITUD_CARRERA){
                        if(velocidadTemporal>10){
                            velocidadTemporal-=(int)(Math.random()*7);
                            if(velocidadTemporal<10){
                                velocidadTemporal=10;
                            }
                        }
                    }
                }
                synchronized(pista.getPlayers()){
                    pista.addPlayer(this);
                }
                if(pista.getPlayers().size()==2){
                    pista.levantaArbitro();
                }
            } catch (InterruptedException ex) {
                interrumpido=true;
            } 
        }else{
            try {
                TimeUnit.SECONDS.sleep(10);
                synchronized(pista.getPlayers()){
                    pista.addPlayer(this);
                }
                if(pista.getPlayers().size()==2){
                    pista.levantaArbitro();
                }
            } catch (InterruptedException ex) {
                
            }
        }
    }
    
    public boolean cogerTrapo(){
        boolean cogido=true;
        int velocidadTemporal=velocidad;
        try {
            int distancia=0;
            while(distancia<LONGITUD_CARRERA){
                TimeUnit.MILLISECONDS.sleep(TIEMPO_EJECUCION_VELOCIDAD);
                distancia+=velocidadTemporal;
                if(distancia>(LONGITUD_CARRERA+MARGEN_ERROR)){
                    cogido=false;
                    System.out.println(nombre+" se ha pasado, pierde");
                    correrCasa(true);
                }else{
                    if(distancia<LONGITUD_CARRERA){
                        if(velocidadTemporal>10){
                            velocidadTemporal-=(int)(Math.random()*7);
                            if(velocidadTemporal<10){
                                velocidadTemporal=10;
                            }
                        }
                    }else{
                        pista.getTrapo().await(TIEMPO_ESPERA_TRAPO, TimeUnit.MILLISECONDS);
                        System.out.println(nombre+" corre a casa");
                        correrCasa(false);
                    }
                }
            }
        } catch (InterruptedException ex) {
            interrumpido=true;
        } catch (BrokenBarrierException ex) {
            correrCasa(false);
        } catch (TimeoutException ex) {
            correrCasa(false);
        }
        return cogido;
    }
    
    public void interrumpir(){
        interrumpido=true;
        yo.interrupt();
    }
    
    @Override
    public String toString(){
        return nombre+" mi velocidad inicial es de "+velocidad+" y tengo el numero "+miCondition.toString().substring(miCondition.toString().indexOf("@")+1);
    }
    
    @Override
    public void run() {
        pista.entrar(this);
        while(!interrumpido){
            try {
                pista.getLock().lock();
                pista.bajaCountDown();
                miCondition.await();
                pista.getLock().unlock();
                cogerTrapo();
            } catch (InterruptedException ex) {
                interrumpido=true;
                pista.getLock().unlock();
            }
        }
    }

    
    
    
}
