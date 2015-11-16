/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugares;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import modelo.Dinosaurio;
import static lugares.Constantes.*;

/**
 *
 * @author Victor Manuel Oviedo Huertas
 */
public class Habitat implements Runnable {

    private List<Dinosaurio> dinosaurios;
    private List<Dinosaurio> dinosMuertos;
    //private Thread tiempo=null;
    private boolean stop = false;
    private Thread habitat;
    private Picadero sexBoom;
    private Estadio santiagoBernabeu = null;
    private Restaurante bulli;
    private Bosque bosque;

    public Habitat() {
        dinosaurios = Collections.synchronizedList(new ArrayList());
        dinosMuertos = Collections.synchronizedList(new ArrayList());
        santiagoBernabeu = new Estadio();
        bulli = new Restaurante();
        sexBoom = new Picadero(this);
        bosque=new Bosque();
        habitat = new Thread(this);
        habitat.start();
        habitat.setName("HABITAT");
    }

    public void bigBang() {
        for (int i = 0; i < DINOSAURIOS_INICIALES; i++) {
            dinosaurios.add(new Dinosaurio("dino" + (dimeCuantosVivos() + dimeCuantosMuertos()), ((int) (Math.random() * RANGO_VIDA_DINO)) + MINIMO_VIDA_DINO, this));
        }
    }

    public void addDinosaurio(Dinosaurio dino) {
        if (dinosaurios != null) {
            synchronized (dinosaurios) {
                dinosaurios.add(dino);
            }
        }
    }
    
    public void addDinosaurio(boolean carnivoro) {
        if (dinosaurios != null) {
            synchronized (dinosaurios) {
                dinosaurios.add(new Dinosaurio("dino" + (dimeCuantosVivos() + dimeCuantosMuertos()), ((int) (Math.random() * 100)) + 200, this, carnivoro));
            }
        }
    }

    public String muestraDinosaurios() {
        StringBuffer cadena = new StringBuffer();
        if (dinosaurios != null) {
            for (Dinosaurio dino : dinosaurios) {
                cadena.append(dino.toString()).append("\n");
            }
        } else {
            cadena.append("ESTAN TODOS MUERTOS");
        }
        return cadena.toString();
    }

    public String muestraDinosauriosMuertos() {
        StringBuffer cadena = new StringBuffer();
        for (Dinosaurio dino : dinosMuertos) {
            cadena.append(dino.getNombre()).append(" murio con ").append(dino.getEdad()).append(" años, en ").append(dino.getLugar()).append(dino.getVida()).append(".").append("\n");
        }
        return cadena.toString();
    }

    public void lanzaMeteorito() {
        habitat.interrupt();
    }

    public void entrarEstadio(Dinosaurio dino) {
        santiagoBernabeu.entra(dino);
    }

    public void entrarRestaurante(Dinosaurio dino) {
        bulli.entra(dino);
    }

    public void entrarRedHouse(Dinosaurio dino) {
        sexBoom.entrar(dino);
    }

    public void entrarBosque(Dinosaurio dino){
        bosque.entrar(dino);
    }
    
    public int dimeCuantosVivos() {
        return dinosaurios.size();
    }

    public int dimeCuantosMuertos() {
        return dinosMuertos.size();
    }
    
    public void modernizaEstadio(int tamano){
        santiagoBernabeu.setTamanoEstadio(tamano);
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                TimeUnit.MILLISECONDS.sleep(TIEMPO_HABITAT);
                synchronized (dinosaurios) {
                    for (Dinosaurio dino : dinosaurios) {
                        dino.aumentaHambre();
                        dino.restaVida();
                        if (dino.getVida() <= 0) {
                            dinosMuertos.add(dino);
                        }
                    }
                    dinosaurios.removeAll(dinosMuertos);
                }
            } catch (InterruptedException e) {
                stop = true;
            }
        }
        sexBoom.coitusInterruptus();
        santiagoBernabeu.interrumpe();
        bulli.para();
        bosque.para();
        synchronized (dinosaurios) {
            for (Dinosaurio dino : dinosaurios) {
                dino.mata();
            }
            dinosMuertos.addAll(dinosaurios);
            dinosaurios = null;
        }
    }
}
