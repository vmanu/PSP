/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugares;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Dinosaurio;

/**
 *
 * @author dam2
 */
public class Habitat implements Runnable{

    ArrayList<Dinosaurio>dinosaurios;
    ArrayList<Dinosaurio>dinosMuertos;
    

    public Habitat() {
        this.dinosaurios = new ArrayList();
        this.dinosMuertos = new ArrayList();
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
        while(true){//Quitar el true
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
            }
        }
        for (Dinosaurio dino : dinosaurios) {
            dino.mata();
        }
    }
    
}
