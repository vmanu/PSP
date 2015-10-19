/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author dam2
 */
public class Almacen {
    private String nombre;
    private byte[] almacenado;
    private Lock lock;
    private Condition estaLleno;
    private Condition estaVacio;
    
    public Almacen(String nombre,int tamano,int relleno){
        this.nombre=nombre;
        almacenado=new byte[tamano];
        for(int i=0;i<relleno;i++){
            almacenado[i]=1;
        }
        lock=new ReentrantLock();
        estaLleno=lock.newCondition();
        estaVacio=lock.newCondition();
    }
    
    public boolean reponer(){
        boolean repuesto=false;
        for(int i=0;!repuesto&&i<almacenado.length;i++){
            if(almacenado[i]!=1){
                almacenado[i]=1;
                repuesto=true;
            }
        }
        return repuesto;
    }
    
    public boolean coger(){
        boolean cogido=false;
        for(int i=0;!cogido&&i<almacenado.length;i++){
            if(almacenado[i]!=0){
                almacenado[i]=0;
                cogido=true;
            }
        }
        return cogido;
    }
    
    public Condition getEstaLleno() {
        return estaLleno;
    }

    public Condition getEstaVacio() {
        return estaVacio;
    }
    
    public Lock getLock(){
        return lock;
    }
    
    @Override
    public String toString(){
        return nombre;
    }
}
