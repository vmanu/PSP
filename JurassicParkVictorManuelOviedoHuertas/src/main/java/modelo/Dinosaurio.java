/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.concurrent.TimeUnit;
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
        carnivoro=((int)(Math.random())*100)%2==2?true:false;
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
        carnivoro=herencia;
    }
    
    public void restaVida(){
        vida--;
        edad++;
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
    
    public void mata(){
        vida=0;
    }

    public String getSexo() {
        return sexo;
    }
    
    public void luchar(Dinosaurio esperando) {
        
    }
    
    public Lugares irLugar(){
        Lugares lugar;
        if(hambre>DINOSAURIOS_HAMBRE_MAXIMA*0.5){
            lugar=RESTAURANTE;
            //AQUI UN CONDICIONAL DE PROBABILIDAD(POSIBLE JUEGO CON LA VIDA
        }else{
            if(edad>18&&edad<vida*0.8&&((int)(Math.random()*10))<7){
                lugar=PICADERO;
            }else{
                lugar=SANTIAGO_BERNABEU;
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
        return cadena.append(nombre).append(" mi edad es ").append(edad).append(" años y mi hambre es de ").append(hambre).append(" y la alegría es de ").append(alegria).append(". Estoy en ").append(getLugar()).append(". Mi sexo es ").append(sexo).toString();
    }//.append("\tME RESTA=").append(vida)

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
                        habitat.entrarRedHouse(this);
                        break;
                    case RESTAURANTE:
                        habitat.entrarRestaurante(this);
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
