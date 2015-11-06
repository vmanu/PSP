/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugares;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Dinosaurio;

/**
 *
 * @author Victor
 */
public class QuiquiRoom{
    private List<Dinosaurio> dinos;
    private Habitat hab;
    private CyclicBarrier barrera;
    
    public QuiquiRoom(Habitat habitat){
        dinos=Collections.synchronizedList(new ArrayList<Dinosaurio>());
        this.hab=habitat;
        barrera=new CyclicBarrier(2, new Runnable(){

            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                    for(Dinosaurio dinosaurio : dinos){
                        dinosaurio.aumentaAlegria(5);
                    }
                    dinos.clear();
                    hab.addDinosaurio(new Dinosaurio("dino"+hab.dimeCuantosVivos(), ((int)(Math.random()*100))+100, hab));
                    barrera.reset();
                } catch (InterruptedException ex) {
                    barrera.reset();
                }
            }
            
        });
    }
    
    public boolean entrar(Dinosaurio dino) {
        boolean entrar=false;
        synchronized(dinos){
            if(dinos.size()==0){
                entrar=true;
                dinos.add(dino);
            }else{
                if(dinos.size()==1&&dino.getSexo().equals(dinos.get(0).getSexo())){
                    entrar=true;
                    dinos.add(dino);
                }
            }
        }
        return entrar;
    }
}
