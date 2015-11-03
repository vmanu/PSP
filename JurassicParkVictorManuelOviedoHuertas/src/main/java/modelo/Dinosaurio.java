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
import static lugares.Lugares.*;
import lugares.Lugares;
import static lugares.Constantes.*;

/**
 *
 * @author dam2
 */
public class Dinosaurio implements Runnable{
    private int vida;
    private int hambre;
    private int alegria;
    private Lugares lugarActual;
    private String nombre;
    private Habitat habitat;

    
    public Dinosaurio(String nombre, int vida, Habitat habitat){
        this.vida=vida;
        this.nombre=nombre;
        this.habitat=habitat;
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
        return SANTIAGO_BERNABEU;
    }
    
    public Lugares getLugar(){
        return lugarActual;
    }
    
    public void setLugarActual(Lugares lugares) {
        lugarActual=lugares;
    }
    
    public void aumentaAlegria(){
        alegria++;
        if(alegria==20){
            alegria=0;
            vida+=5;
        }
    }
    
    @Override
    public String toString(){
        StringBuffer cadena=new StringBuffer();
        return cadena.append(nombre).append(" mi vida es ").append(vida).append(" y mi hambre es de ").append(hambre).append(" y la alegría es de ").append(alegria).append(". Estoy en ").append(getLugar()).toString();
    }

    @Override
    public void run() {
        while(vida>0){
            try {
                TimeUnit.MILLISECONDS.sleep(10);
                Lugares lugarIr=irLugar();
                switch(lugarIr){
                    case BOSQUE:
                        break;
                    case PICADERO:
                        break;
                    case RESTAURANTE:
                        break;
                    case SANTIAGO_BERNABEU:
                        habitat.entrarEstadio(this);
                        break;
                }
            } catch (InterruptedException ex) {
                vida=0;
            }
        }
    }
}
