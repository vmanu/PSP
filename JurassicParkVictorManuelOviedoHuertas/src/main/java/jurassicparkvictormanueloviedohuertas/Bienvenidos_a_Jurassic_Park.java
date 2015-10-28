/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jurassicparkvictormanueloviedohuertas;

import lugares.Habitat;

/**
 *
 * @author dam2
 */
public class Bienvenidos_a_Jurassic_Park {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int generaVida=((int)Math.random()*100)+1000;
        Thread h=new Thread(new Habitat());
        h.start();
        int op=0;
        do{
            System.out.println("Introduce una opcion:\n\t1.");
        }while(op!=3);
    }
    
}
