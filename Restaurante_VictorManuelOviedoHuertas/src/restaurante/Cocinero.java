/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author dam2
 */
public class Cocinero implements Runnable{
    private int tiempo;
    private ArrayList <Almacen> ingredientes;
    private Stopper stop;
    
    public Cocinero(ArrayList<Almacen> ingredientes, int tiempo,Stopper stop){
       this.ingredientes=ingredientes;
       this.tiempo=tiempo;
       this.stop=stop;
    }
    
    @Override
    public void run() {
        boolean[] waiting= new boolean[3];
        while(!stop.isStop()){
            waiting[0]=true;
            waiting[1]=true;
            waiting[2]=true;
            try {
                TimeUnit.SECONDS.sleep(tiempo);
            } catch (InterruptedException ex) {
                stop.setStop(true);
            }
            for(int i=0;i<ingredientes.size();i++){
                while(waiting[i]){
                    synchronized(ingredientes.get(i)){
                        if(ingredientes.get(i).coger()){
                            System.out.println("cocinero coge "+ingredientes.get(i).toString());
                            waiting[i]=false;
                            ingredientes.get(i).notifyAll();
                        }else{
                            try {
                                ingredientes.get(i).wait();
                            } catch (InterruptedException ex) {
                                waiting[i]=false;
                                stop.setStop(true);
                            }
                        }
                        if(i==ingredientes.size()-1){
                            System.out.print("Cocinero ");
                            for(int j=0;j<ingredientes.size();j++){
                                System.out.print(ingredientes.get(j));
                            }
                            System.out.println(" ha hecho un plato.");
                        }
                    }
                }
            }
        }
    }
    
}
