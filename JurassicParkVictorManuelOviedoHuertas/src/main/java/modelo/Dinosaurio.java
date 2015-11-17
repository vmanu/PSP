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
 * @author Victor Manuel Oviedo Huertas
 */
public class Dinosaurio implements Runnable{
    private int vida;
    private int hambre;
    private int alegria;
    private int edad;
    private Lugares lugarActual;
    private String nombre;
    private Habitat habitat;
    private Thread dino;
    private String sexo;
    private boolean carnivoro;
    private int pelea;

    
    public Dinosaurio(String nombre, int vida, Habitat habitat){
        this.vida=vida;
        this.nombre=nombre;
        this.habitat=habitat;
        edad=0;
        hambre=0;
        alegria=0;
        dino=new Thread(this);
        dino.start();
        dino.setName(nombre);
        sexo=(((int)(Math.random()*10)%2)==0?MASCULINO:FEMENINO);
        pelea=0;
        carnivoro=((int)(Math.random()*10)%2)==0;
    }
    
    public Dinosaurio(String nombre, int vida, Habitat habitat, boolean herencia){
        this.vida=vida;
        this.nombre=nombre;
        this.habitat=habitat;
        edad=0;
        hambre=0;
        alegria=0;
        dino=new Thread(this);
        dino.start();
        dino.setName(nombre);
        sexo=(((int)(Math.random()*10)%2)==0?MASCULINO:FEMENINO);
        pelea=0;
        carnivoro=herencia;
    }
    
    public void restaVida(){
        vida--;
        edad++;
        if(edad%2==0&&edad<MINIMO_VIDA_DINO*0.65){
            pelea++;
            if(edad<MINIMO_VIDA_DINO*0.2){
                pelea++;
            }
        }
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
    
    public int getEdad(){
        return edad;
    }
    
    public void setVida(int valor){
        vida-=valor;
    }
    
    public void mata(){
        vida=0;
    }

    public String getSexo() {
        return sexo;
    }
    
    public int getPuntosLucha(){
        return pelea;
    }
    
    public void setPuntosLucha(int resta){
        pelea-=resta;
    }
    
    public void resetPuntosLucha(){
        pelea=0;
    }
    
    public void luchar(Dinosaurio esperando) {
        try {
            TimeUnit.MILLISECONDS.sleep(TIEMPO_LUCHA);
            if(pelea<esperando.getPuntosLucha()){
                vida-=(esperando.getPuntosLucha()-pelea);
                esperando.setPuntosLucha(pelea);
                pelea=0;
                if(vida<=0&&esperando.isCarnivoro()){
                    esperando.restaHambre(EFECTO_COMIDA_DINO);
                }
                esperando.aumentaAlegria(EFECTO_ALEGRIA_VENCER_PELEA);
            }else{
                esperando.setVida(pelea-esperando.getPuntosLucha());
                this.setPuntosLucha(esperando.getPuntosLucha());
                esperando.resetPuntosLucha();
                if(esperando.getVida()<=0&&carnivoro){
                    this.restaHambre(EFECTO_COMIDA_DINO);
                }
                this.aumentaAlegria(EFECTO_ALEGRIA_VENCER_PELEA);
            }
        } catch (InterruptedException ex) {
            
        }
    }
    
    public Lugares irLugar(){
        Lugares lugar;
        int loteria=((int)(Math.random()*100))+1;
        if(hambre>DINOSAURIOS_HAMBRE_MAXIMA*0.6){
            lugar=RESTAURANTE;
        }else{
            if(edad>18&&edad<MINIMO_VIDA_DINO*0.5){
                lugar=PICADERO;
            }else{
                if(loteria%2==0){
                    lugar=SANTIAGO_BERNABEU;
                }else{
                    lugar=BOSQUE;
                }
            }
        }
        return lugar;
    }
    
    public Lugares getLugar(){
        return lugarActual;
    }
    
    public void setLugarActual(Lugares lugares) {
        lugarActual=lugares;
    }
    
    public boolean isCarnivoro(){
        return carnivoro;
    }
    
    public void aumentaAlegria(){
        alegria++;
        if(alegria==20){
            alegria=0;
            if(vida>0){
                vida+=5;
            }
        }
    }
    
    public void aumentaAlegria(int j) {
        for(int i=0;i<j;i++){
            aumentaAlegria();
        }
    }
    
    @Override
    public String toString(){
        StringBuffer cadena=new StringBuffer();
        return cadena.append(nombre).append(" mi edad es ").append(edad).append(" años y mi hambre es de ").append(hambre).append(" y la alegría es de ").append(alegria).append(". Estoy en ").append(getLugar()).append(". Mi sexo es ").append(sexo).append(", soy ").append(carnivoro?"Carnivoro":"Herbivoro").append(" y tengo ").append(pelea).append(" puntos de ").append(carnivoro?"ataque":"defensa").toString();
    }//.append("\tME RESTA=").append(vida)

    @Override
    public void run() {
        while(vida>0){
            try {
                TimeUnit.MILLISECONDS.sleep(10);
                Lugares lugarIr=irLugar();
                //System.out.println("Entra a cumplir "+nombre+" a "+lugarIr);
                switch(lugarIr){
                    case BOSQUE:
                        habitat.entrarBosque(this);
                        break;
                    case PICADERO:
                        habitat.entrarRedHouse(this);
                        break;
                    case RESTAURANTE:
                        habitat.entrarRestaurante(this);
                        break;
                    case SANTIAGO_BERNABEU:
                        habitat.entrarEstadio(this);
                        break;
                }
                //System.out.println("Ha cumplido "+nombre);
            } catch (InterruptedException ex) {
                vida=0;
            }
        }
    }


}
