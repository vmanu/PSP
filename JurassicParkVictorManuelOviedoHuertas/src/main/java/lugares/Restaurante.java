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

/**
 *
 * @author dam2
 */
public class Restaurante implements Runnable{
    /*ArrayBlockingQueue
    Baja hambre*/
    ArrayBlockingQueue<Object> comensales;
    boolean stop=false;
    
    public Restaurante(){
        comensales=new ArrayBlockingQueue(Constantes.RESTAURANT_SIZE,true);
        Thread t=new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        while(!stop){
            try {
                TimeUnit.MILLISECONDS.sleep(500);
                comensales.put(new Object());
            } catch (InterruptedException ex) {
                Logger.getLogger(Restaurante.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * poll gestiona que el tiempo de espera máximo es de 500 milisegundos
     * @param dino 
     */
    public void entrar(Dinosaurio dino){
        dino.setLugarActuar(Lugares.RESTAURANTE);
        try{
            if(comensales.poll(500,TimeUnit.MILLISECONDS)!=null){
                
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Restaurante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
