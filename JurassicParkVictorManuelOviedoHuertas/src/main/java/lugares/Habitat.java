/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugares;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import modelo.Dinosaurio;
import static lugares.Constantes.*;

/**
 *
 * @author dam2
 */
public class Habitat implements Runnable{

    private List<Dinosaurio>dinosaurios;
    private List<Dinosaurio>dinosMuertos;
    private Thread tiempo=null;
    private ExecutorService ex=null;
    private boolean stop=false;
    private Thread bBang;
    private Picadero sexBoom;
    private Estadio santiagoBernabeu=null;
    

    public Habitat() {
        dinosaurios = Collections.synchronizedList(new ArrayList());
        dinosMuertos = Collections.synchronizedList(new ArrayList());
        ex=Executors.newFixedThreadPool(DINOSAURIOS_INICIALES);
        santiagoBernabeu=new Estadio();
        sexBoom=new Picadero();
    }
    
    public void bigBang(){
        bBang=new Thread(this);
        bBang.start();
        for(int i=0;i<DINOSAURIOS_INICIALES;i++){
            dinosaurios.add(new Dinosaurio("dino"+i,((int)(Math.random()*1000))+1000,this));
            ex.execute(dinosaurios.get(i));
        }
    }
    
    public void addDinosaurio(Dinosaurio dino){
        dinosaurios.add(dino);
        Thread dinos=new Thread(dino);
        dinos.start();
    }
    
    public String muestraDinosaurios(){
        StringBuffer cadena=new StringBuffer();
        for(Dinosaurio dino:dinosaurios){
            cadena.append(dino.toString()).append("\n");
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
    
    public void lanzaMeteorito(){
        bBang.interrupt();
    }
    
    public void entrarEstadio(Dinosaurio dino){
        santiagoBernabeu.entra(dino);
    }
    
    @Override
    public void run() {
        while(!stop){
            try {
                TimeUnit.MILLISECONDS.sleep(TIEMPO_HABITAT);
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
            } catch (InterruptedException e) {
                stop=true;
            }
        }
        for (Dinosaurio dino : dinosaurios) {
            dino.mata();
        }
        ex.shutdown();
    }
    
}
