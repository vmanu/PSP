/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import lugares.Habitat;
import lugares.Lugares;

/**
 *
 * @author dam2
 */
public class Dinosaurio implements Runnable{
    int vida;
    int hambre;
    Lugares lugarActual;
    String nombre;

    
    public Dinosaurio(String nombre, int vida, Habitat habitat){
        this.vida=vida;
        hambre=0;
    }
    
    public void restaVida(){
        vida--;
        if(hambre>80&&hambre<100){
            vida--;
        }else{
            if(hambre>=100){
               vida-=2; 
            }
        }
    }
    
    public void aumentaHambre(){
        hambre++;
    }
    
    public void restaHambre(int cuanto){
        hambre-=cuanto;
        if(hambre<0){
            hambre=0;
        }
    }
    
    public int getVida(){
        return vida;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void mata(){
        vida=0;
    }
    
    public Lugares irLugar(){
        return Lugares.SANTIAGO_BERNABEU;
    }
    
    @Override
    public String toString(){
        StringBuffer cadena=new StringBuffer();
        return cadena.append(nombre).append(" mi vida es ").append(vida).append(" y mi hambre es de ").append(hambre).toString();
    }

    @Override
    public void run() {
        while(vida>0){
            try {
                TimeUnit.MILLISECONDS.sleep(10);
                //Lugares lugarIr=irLugar();
            } catch (InterruptedException ex) {
                Logger.getLogger(Dinosaurio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setLugarActuar(Lugares lugares) {
        lugarActual=lugares;
    }
}
