/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primerhiloensenyanza;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dam2
 */
public class PrimerHiloEnsenyanza implements Runnable{

    private int id;
    
    
    public PrimerHiloEnsenyanza (){
    }
    
    public PrimerHiloEnsenyanza (int id){
        this.id=id;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //clonemos!!!!!
        StringBuffer sb=new StringBuffer();
        StringBuffer out=new StringBuffer();
        Thread miHilo=new Thread(new PrimerHiloEnsenyanza(1));
        miHilo.start();
        miHilo=new Thread(new PrimerHiloEnsenyanza(2));
        miHilo.start();
        miHilo=new Thread(new PrimerHiloEnsenyanza(3));
        miHilo.start();
        miHilo=new Thread(new PrimerHiloEnsenyanza());
        miHilo.start();
        miHilo=new Thread(new MiHilo(4,"d",sb));
        miHilo.start();
        miHilo=new Thread(new MiHilo(5,"e",sb));
        miHilo.start();
        miHilo=new Thread(new MiHilo(6,"f",sb));
        miHilo.start();
        System.out.println("Hola mundo");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ex) {
            Logger.getLogger(PrimerHiloEnsenyanza.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("bufefer "+sb);
        System.out.println(out);
        
        Turno turno=new Turno();
        turno.setTurno(1);
        StringBuffer sd=new StringBuffer();
        
        Thread hiloSinc=new Thread(new MiHiloSynchronized(1, "1", sd,out,turno));
        Thread hiloSinc2=new Thread(new MiHiloSynchronized(2, "2", sd,out,turno));
        Thread hiloSinc3=new Thread(new MiHiloSynchronized(3, "3", sd,out,turno));
        hiloSinc.start();
        hiloSinc2.start();
        hiloSinc3.start();
        
    }
    
    @Override
    public void run(){
        System.out.println("hello world "+id);//LOS INPUTS Y LOS OUTPUTS SIEMPRE TIENEN MENOS PRIORIDAD, POR ESO SALEN EN DIFERENTE ORDEN
    }
}
