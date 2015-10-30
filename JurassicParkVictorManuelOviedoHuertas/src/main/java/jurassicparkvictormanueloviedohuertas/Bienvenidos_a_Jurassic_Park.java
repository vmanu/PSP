/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jurassicparkvictormanueloviedohuertas;

import java.util.Scanner;
import lugares.Constantes;
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
        Scanner keyb=new Scanner(System.in);
        Habitat habitat=new Habitat();
        //Thread h=new Thread(habitat);
        //h.start();
        int op=0;
        do{
            System.out.println("Introduce una opcion:\n\t1.Ver Dinosaurios Vivos\n\t2.BigBang\n\t3.Lanzar Meteorito");
            op=keyb.nextInt();
            switch(op){
                case 1:
                    habitat.muestraDinosaurios();
                    break;
                case 2:
                    habitat.bigBang();
                    break;
                case Constantes.MENU_SIZE:
                    System.out.println("Adios");
                    break;
            }
        }while(op!=Constantes.MENU_SIZE);
    }
    
}
