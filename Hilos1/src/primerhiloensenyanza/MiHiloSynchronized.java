/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primerhiloensenyanza;

/**
 *
 * @author dam2
 */
public class MiHiloSynchronized implements Runnable {

    private int id;
    private StringBuffer sb;
    private String text;
    private Turno turno;

    public MiHiloSynchronized(int id, String text, StringBuffer sb, Turno turno) {
        this.id = id;
        this.sb = sb;
        this.text = text;
        this.turno = turno;
    }

    @Override
    public void run() {
        boolean hecho = false;
        synchronized (sb) {//Obliga a pasar por aqui, de 1 en 1
            try {
                while (!hecho) {
                    if (turno.getTurno() == id) {
                        sb.append(text);
                        System.out.println("hilo " + id + sb.toString());
                        
                        if(turno.getTurno()!=3){
                            turno.setTurno(++id);
                        }else{
                            turno.setTurno(1);
                        }
                        hecho=true;
                        sb.notifyAll();
                    } else {
                        sb.wait();
                    }
                }
            } catch (Exception ex) {

            }
        }
    }
}
