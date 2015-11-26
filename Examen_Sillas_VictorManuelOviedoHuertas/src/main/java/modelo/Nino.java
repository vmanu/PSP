/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.logging.Level;
import java.util.logging.Logger;
import lugar.Sala;

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
    private Sala sala;
    private boolean musica;
    
    public Nino(String nombre, Sala sala){
        this.nombre=nombre;
        this.sala=sala;
        yo=new Thread(this);
        yo.start();
        yo.setName(nombre);
        musica=true;
    }
    
    public void setSilla(Condition silla){
        this.silla=silla;
    }
    
    public Condition getSilla(){
        return silla;
    }
    
    public void pararMusica(){
        musica=false;
    }
    
    public String getName(){
        return nombre;
    }
    
    @Override
    public void run() {
        sala.jugar(this);
        while(musica){
            try {
                TimeUnit.SECONDS.sleep(1);
                sala.getLock().lock();
                if(sala.getCountDownLatch().getCount()!=0){
                    sala.getCountDownLatch().countDown();
                }
                if(silla!=null){
                    silla.await();
                }
                if(silla!=null&&silla.equals(sala.getDePie())){
                    silla=null;
                }
                if(musica&&silla==null){
                    sala.cambioSilla(this);
                }
                sala.getLock().unlock();
            } catch (InterruptedException ex) {
                musica=false;
            }
        }
        if(silla==null){
            System.out.println(nombre+" ha perdido");
            sala.gameOver();
        }
    }
    
    public String toString(){
        return nombre+", "+silla==null?"de pie":"sentado";
    }
}
