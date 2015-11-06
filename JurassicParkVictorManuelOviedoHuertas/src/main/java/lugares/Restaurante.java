/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugares;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Dinosaurio;
import static lugares.Constantes.*;
import static lugares.Lugares.*;


/**
 *
 * @author Victor Manuel Oviedo Huertas
 */
public class Restaurante implements Runnable{
    /*ArrayBlockingQueue
    Baja hambre*/
    private ArrayBlockingQueue<Object> comensales;
    private boolean stop=false;
    private Thread bulli;
    
    
    public Restaurante(){
        comensales=new ArrayBlockingQueue(RESTAURANT_SIZE,true);
        bulli=new Thread(this);
        bulli.start();
        bulli.setName("RESTAURANTE");
    }

    @Override
    public void run() {
        while(!stop){
            try {
                TimeUnit.MILLISECONDS.sleep(TIEMPO_COCINA);
                comensales.put(new Object());
            } catch (InterruptedException ex) {
                stop=true;
            }
        }
    }
    
    public void para(){
        bulli.interrupt();
    }
    
    /**
     * poll gestiona que el tiempo de espera máximo es de 500 milisegundos
     * @param dino 
     */
    public void entra(Dinosaurio dino){
        dino.setLugarActual(RESTAURANTE);
        try{
            if(comensales.poll(TIEMPO_ESPERA_RESTAURANTE,TimeUnit.MILLISECONDS)!=null){
                TimeUnit.MILLISECONDS.sleep(TIEMPO_COMER);
                dino.restaHambre(EFECTO_COMIDA_RESTAURANTE);
                dino.setLugarActual(HABITAT);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Restaurante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
