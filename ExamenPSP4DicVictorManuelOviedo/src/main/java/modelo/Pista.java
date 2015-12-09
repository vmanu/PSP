/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.concurrent.CountDownLatch;
import static constantes.Constantes.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dam2
 */
public class Pista {

    private CountDownLatch comienza;
    private CountDownLatch finaliza;
    private CyclicBarrier setUp;
    private ArrayList<Atleta> corredores;
    private HashMap<String, Integer> equipos;//NOmbre de equipo y ticks
    private Lock pista1;
    private Lock pista2;
    private Lock pista3;
    private Lock pista4;
    private Lock carrera;//PARA ARBITRO Y Terminar Carrera (Saber ganador)
    private ArrayList<Condition> testigos;
    private Atleta ganador;
    

    public Pista() {
        comienza = new CountDownLatch(NUMERO_CORREDORES);
        finaliza = new CountDownLatch(NUMERO_CORREDORES);
        corredores = new ArrayList();
        equipos = new HashMap();
        pista1 = new ReentrantLock();
        pista2 = new ReentrantLock();
        pista3 = new ReentrantLock();
        pista4 = new ReentrantLock();
        carrera=new ReentrantLock(true);
        testigos=new ArrayList();
        for (int i = 0; i < NUMERO_CORREDORES; i = i + 4) {
            testigos.add(pista1.newCondition());
            testigos.add(pista2.newCondition());
            testigos.add(pista3.newCondition());
            testigos.add(pista4.newCondition());
        }
        setUp = new CyclicBarrier(NUMERO_CORREDORES, new Runnable() {

            @Override
            public void run() {
                equipos.put(EQUIPO1, 0);
                equipos.put(EQUIPO1, 0);
                equipos.put(EQUIPO1, 0);
                equipos.put(EQUIPO1, 0);
                for (int i = 0; i < NUMERO_PISTAS; i++) {
                    corredores.get(i).setEquipo("eq" + (i + 1));
                    corredores.get(i).setTestigo(testigos.get(i));
                    corredores.get(i + 4).setEquipo("eq" + (i + 1));
                    corredores.get(i + 4).setTestigo(testigos.get(i + 4));
                    corredores.get(i + 8).setEquipo("eq" + (i + 1));
                    corredores.get(i + 8).setTestigo(testigos.get(i + 8));
                    corredores.get(i + 12).setEquipo("eq" + (i + 1));
                    corredores.get(i + 12).setTestigo(testigos.get(i + 12));
                }
            }
        });
        for(int i=0;i<NUMERO_CORREDORES;i++){
            new Atleta(this, "Dorsal: "+(i+1));
        }
        new Arbitro(this);
    }
    
    public CountDownLatch getFinaliza(){
        return finaliza;
    }

    public CountDownLatch getComienza() {
        return comienza;
    }

    public Lock getLock(String equipo) {
        Lock devuelve = null;
        switch (equipo) {
            case EQUIPO1:
                devuelve = pista1;
                break;
            case EQUIPO2:
                devuelve = pista2;
                break;
            case EQUIPO3:
                devuelve = pista3;
                break;
            case EQUIPO4:
                devuelve = pista4;
                break;
        }
        return devuelve;
    }
    
    public Lock carreraLocker(){
        return carrera;
    }
    
    void pasarTestigo(int ticks,String nombre) {
        boolean encontrado=false;
        for(int i=0;i<NUMERO_CORREDORES&&!encontrado;i++){
            if(corredores.get(i).getNombre().equals(nombre)){
                equipos.put(corredores.get(i).getEquipo(), ticks);
                if(i<NUMERO_CORREDORES-4){
                    corredores.get(i+4).empiezaCorrer(ticks);
                    System.out.println("CEDE EL TESTIGO "+nombre);
                }
            }
        }
    }
    
    void comprobarSiGano(Atleta candidato) {
        if(ganador==null){
            ganador=candidato;
        }
    }

    public ArrayList<Atleta> getCorredores(){
        return corredores;
    }
    
    public HashMap getResultados(){
        return equipos;
    }

    public String getEquipoGanador(){
        return ganador.getEquipo();
    }
    
    public void entrar(Atleta s) {
        try {
            corredores.add(s);
            setUp.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(Pista.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BrokenBarrierException ex) {
            Logger.getLogger(Pista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    

    
}
