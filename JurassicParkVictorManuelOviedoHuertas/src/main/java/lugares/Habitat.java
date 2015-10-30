/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugares;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Dinosaurio;

/**
 *
 * @author dam2
 */
public class Habitat implements Runnable{

    List<Dinosaurio>dinosaurios;
    List<Dinosaurio>dinosMuertos;
    Thread tiempo=null;
    ExecutorService ex=null;
    boolean stop=false;
    Estadio santiagoBernabeu=null;
    

    public Habitat() {
        dinosaurios = Collections.synchronizedList(new ArrayList());
        dinosMuertos = Collections.synchronizedList(new ArrayList());
        ex=Executors.newFixedThreadPool(10);
        santiagoBernabeu=new Estadio();
    }
    
    public void bigBang(){
        Thread bBang=new Thread(this);
        for(int i=0;i<10;i++){
            ex.execute(new Dinosaurio("dino"+1,100,this));
        }
    }
    
    public void addDinosaurio(Dinosaurio dino){
        Thread dinos=new Thread(dino);
        dinosaurios.add(dino);
        dinos.start();
    }
    
    public String muestraDinosaurios(){
        StringBuffer cadena=new StringBuffer();
        for(Dinosaurio dino:dinosaurios){
            cadena.append(dino.toString());
        }
        return cadena.toString();
    }
    
    public String muestraDinosauriosMuertos(){
        StringBuffer cadena=new StringBuffer();
        for(Dinosaurio dino:dinosMuertos){
            cadena.append(dino.getNombre()).append("\n");
        }
        return cadena.toString();
    }
    
    @Override
    public void run() {
        while(!stop){//Quitar el true
            try {
                TimeUnit.MILLISECONDS.sleep(10);
                synchronized(dinosaurios){
                    for(Dinosaurio dino:dinosaurios){
                        dino.aumentaHambre();
                        dino.restaVida();
                        if(dino.getVida()<=0){
                            dinosMuertos.add(dino);
                        }
                    }
                    dinosaurios.removeAll(dinosMuertos);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Habitat.class.getName()).log(Level.SEVERE, null, ex);
                stop=true;
            }
        }
        for (Dinosaurio dino : dinosaurios) {
            dino.mata();
        }
    }
    
}
