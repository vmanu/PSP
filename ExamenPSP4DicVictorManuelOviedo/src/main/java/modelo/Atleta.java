/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import static constantes.Constantes.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dam2
 */
public class Atleta implements Runnable {

    private int ticks;
    private String equipo;
    private Thread yo;
    private Pista pista;
    private String nombre;
    private Condition testigo;
    private int velocidad;
    private int corrido;

    public Atleta(Pista pista, String nombre) {
        this.pista = pista;
        this.nombre = nombre;
        corrido = 0;
        velocidad = ((int) (Math.random() * VELOCIDAD_VARIACION)) + VELOCIDAD_MINIMA;
        yo = new Thread(this);
        yo.start();
        yo.setName(nombre + " " + equipo);
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public void setTestigo(Condition testigo) {
        this.testigo = testigo;
    }

    public void empiezaCorrer(int ticks) {
        this.ticks=ticks;
        pista.getLock(equipo).lock();
        testigo.signal();
        pista.getLock(equipo).unlock();
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public String getEquipo(){
        return equipo;
    }

    @Override
    public void run() {
        pista.entrar(this);
        pista.getComienza().countDown();
        try {
            pista.getLock(equipo).lock();
            System.out.println("Esperando "+nombre+" del equipo "+equipo+" con una velocidad de "+velocidad);
            testigo.await();
            System.out.println("Saliendo "+nombre+" del equipo "+equipo);
            while (corrido < DISTANCIA_CARRERA) {
                ticks++;
                TimeUnit.MILLISECONDS.sleep(TIEMPO_CICLO_CARRERA);
                corrido+=velocidad;
                System.out.println(nombre+" ha recorrido "+corrido+" ticks: "+ticks);
                if(corrido>=DISTANCIA_CARRERA){
                    pista.pasarTestigo(ticks,nombre);
                }
            }
            
        } catch (InterruptedException ex) {
            
        } finally{
            pista.getLock(equipo).unlock();
        }
        pista.carreraLocker().lock();
        pista.comprobarSiGano(this);
        pista.carreraLocker().unlock();
        pista.getFinaliza().countDown();
        try {
            pista.getFinaliza().await();
        } catch (InterruptedException ex) {
            
        }
    }
}
