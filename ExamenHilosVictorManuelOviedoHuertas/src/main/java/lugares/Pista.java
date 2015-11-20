/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugares;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import modelo.Nino;
import static constantes.Constantes.*;
import java.util.concurrent.TimeUnit;
import modelo.Arbitro;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author dam2
 */
public class Pista {
    private Lock llave;
    private ArrayList<Condition> numeros;
    private ArrayList<Nino> ninos;
    private ArrayList<Nino> equipoA;
    private ArrayList<Nino> equipoB;
    private CyclicBarrier barrera;
    private CyclicBarrier trapo;
    private Arbitro arbitro;
    private ArrayList<Nino> players;
    private Condition arbitroElige;
    private CountDownLatch cuentaAtras;
    private StringBuffer noticias;
//    private int tamanoPista;
    private boolean interrumpido;
    
    
    public Pista(){
        llave=new ReentrantLock();
        interrumpido=false;
        trapo=new CyclicBarrier(2);
        //tamanoPista=TAMAÑO_PISTA;
        ninos=new ArrayList();
        equipoA=new ArrayList();
        equipoB=new ArrayList();
        players=new ArrayList();
        noticias=new StringBuffer();
        numeros=new ArrayList();
        cuentaAtras=new CountDownLatch(NUMERO_NIÑOS);
        arbitroElige=llave.newCondition();
        
        for(int i=0;i<(NUMERO_NIÑOS/2);i++){
            numeros.add(llave.newCondition());
        }
        arbitro=new Arbitro(numeros, arbitroElige,this);
        barrera=new CyclicBarrier(NUMERO_NIÑOS, new Runnable(){

            @Override
            public void run() {
                int j=0;
                if(!interrumpido){
                    for(int i=0;i<NUMERO_NIÑOS;i++){
                        //ASIGNAMOS LOS GRUPOS
                        if(i%2==0){
                            ninos.get(i).setNumero(numeros.get(j));
                            equipoA.add(ninos.get(i));
                        }else{
                            ninos.get(i).setNumero(numeros.get(j));
                            equipoB.add(ninos.get(i));
                            j++;
                        }
                    }
                }
            }
            
        });
        for(int i=0;i<NUMERO_NIÑOS;i++){
            new Nino((i+1),this);
        }
    }
    
    public Lock getLock(){
        return llave;
    }
    
    public void addPlayer(Nino child){
        players.add(child);
        if(players.size()==2){
            quitarNiño(child);
            noticias.append("Ha ganado ").append(players.get(0).toString()).append("\n");
            trapo.reset();
            players.clear();
        }
    }
    
    public String dameNoticias(){
        return noticias.toString();
    }
    
    public ArrayList<Nino> getPlayers(){
        return players;
    }
    
    public CyclicBarrier getTrapo(){
        return trapo;
    }
    
    public String dimeWinner(){
        return equipoA.size()>equipoB.size()?"EquipoA gana":"EquipoB gana";
    }
    
    public void levantaArbitro(){
        llave.lock();
        arbitroElige.signalAll();
        llave.unlock();
    }
    
    public void bajaCountDown(){
        cuentaAtras.countDown();
        if(cuentaAtras.getCount()==0){
            try {
                TimeUnit.SECONDS.sleep(2);
                arbitroElige.signalAll();
            } catch (InterruptedException ex) {
                
            }
        }
    }
    
    public void quitarNiño(Nino child){
        if(!child.equals(players)){
            equipoA.remove(child);
            equipoB.remove(child);
        }
    }
    
    public void entrar(Nino child){
        try {
            synchronized(ninos){
                ninos.add(child);
            }
            barrera.await();
        } catch (InterruptedException ex) {
            
        } catch (BrokenBarrierException ex) {
            
        }
    }
    
    public String muestraNiños(){
        StringBuffer cadena=new StringBuffer();
        cadena.append("Los niños están así:\n");
        cadena.append("equipoA\n");
        for(Nino child:equipoA){
            cadena.append(child.toString()).append("\n");
        }
        cadena.append("equipoB\n");
        for(Nino child:equipoB){
            cadena.append(child.toString()).append("\n");
        }
        return cadena.toString();
    }
    
    public void interrumpir(){
        interrumpido=true;
        for(Nino child:ninos){
            child.interrumpir();
        }
        arbitro.interrumpe();
    }
}
