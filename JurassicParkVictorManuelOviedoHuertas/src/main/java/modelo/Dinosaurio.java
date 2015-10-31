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
import static lugares.Constantes.*;

/**
 *
 * @author dam2
 */
public class Dinosaurio implements Runnable{
    int vida;
    int hambre;
    int alegria;
    Lugares lugarActual;
    String nombre;

    
    public Dinosaurio(String nombre, int vida){
        this.vida=vida;
        this.nombre=nombre;
        hambre=0;
        alegria=0;
    }
    
    public void restaVida(){
        vida--;
    }
    
    public void aumentaHambre(){
        hambre++;
        if(hambre>DINOSAURIOS_HAMBRE_MAXIMA){
            hambre=DINOSAURIOS_HAMBRE_MAXIMA;
        }
        if(hambre>DINOSAURIOS_HAMBRE_MAXIMA*0.8){
            vida--;
        }
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

    public void setLugarActual(Lugares lugares) {
        lugarActual=lugares;
    }
}
