/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.concurrent.locks.Condition;

/**
 * Niño: run dormir hasta que arbitro diga,
 * antes de dormir, countDown que despertará a
 * arbitro para que inicie el juego.
 * su esta despierto porque no tiene condition, espera, levanta al siguiente y se sienta (esto dentro de un "if(!finmusica)
 * una condicion, se reparten tantas como niños, pero uno tendra la de "esperar" que se pondra en su ejecucion del run a null, para que a partir de ahi el juego siga
 */
public class Nino implements Runnable{
    private String nombre;
    private Thread yo;
    private Condition silla;
    
    public Nino(String nombre){
        this.nombre=nombre;
        yo=new Thread();
        yo.start();
    }
    
    public void setSilla(Condition silla){
        this.silla=silla;
    }
    
    public Condition getSilla(){
        return silla;
    }
    
    @Override
    public void run() {
        
    }
    
}
