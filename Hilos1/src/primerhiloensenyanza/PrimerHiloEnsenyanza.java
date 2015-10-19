/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primerhiloensenyanza;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
        Turno turno=new Turno();
        Scanner keyb=new Scanner(System.in);
        int choose=0, numhilos=0;
        do{
            System.out.println("Introduzca el turno por el que quiere empezar");
            try{
                choose=keyb.nextInt();
                if(choose<1||choose>3){
                    System.out.print("No es un numero válido, escribe 1, 2 o 3. ");
                }
            }catch (InputMismatchException s){
                System.out.println("El valor introducido no es numérico");
            }
        }while(choose<1||choose>3);
        turno.setTurno(choose);
        StringBuffer sd=new StringBuffer();
        Thread hiloSinc=new Thread(new MiHiloSynchronized(1, "1", sd,turno));
        Thread hiloSinc2=new Thread(new MiHiloSynchronized(2, "2", sd,turno));
        Thread hiloSinc3=new Thread(new MiHiloSynchronized(3, "3", sd,turno));
        hiloSinc.start();
        hiloSinc2.start();
        hiloSinc3.start();
        
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ex) {
            System.out.println("Fallo en espera");
        }
        sd=null;
        sd=new StringBuffer();
        do{
            System.out.println("Introduzca numero de hilos.");
            try{
                numhilos=keyb.nextInt();
                if(numhilos<1){
                    System.out.print("No es un numero válido, es menor que 1. ");
                }
            }catch (InputMismatchException s){
                System.out.println("El valor introducido no es numérico");
            }
        }while(numhilos<1);
        ExecutorService executor=Executors.newFixedThreadPool(numhilos);
        turno.setTurno(1);
        for(int i=1;i<=numhilos;i++){
            MiHiloSynchronized2 milo=new MiHiloSynchronized2(i, ""+i, sd, turno);
            executor.execute(milo);
        }
        executor.shutdown();
        
    }
    
    @Override
    public void run(){
        System.out.println("hello world "+id);//LOS INPUTS Y LOS OUTPUTS SIEMPRE TIENEN MENOS PRIORIDAD, POR ESO SALEN EN DIFERENTE ORDEN
    }
}
