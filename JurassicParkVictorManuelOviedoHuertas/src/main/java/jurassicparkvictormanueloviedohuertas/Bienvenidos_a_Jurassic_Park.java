/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jurassicparkvictormanueloviedohuertas;

import java.util.Scanner;
import static lugares.Constantes.*;
import lugares.Habitat;
import modelo.Dinosaurio;

/**
 *
 * @author dam2
 */
public class Bienvenidos_a_Jurassic_Park {

    public static Dinosaurio creaDinosaurio(){
        System.out.println("Introduzca el nombre de su nuevo dinosaurio");
        Dinosaurio dino=new Dinosaurio(new Scanner(System.in).nextLine(),((int)(Math.random()*1000)+1000));
        return dino;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner keyb=new Scanner(System.in);
        Habitat habitat=new Habitat();
        int op=0;
        do{
            System.out.println("Introduce una opcion:\n\t1.BigBang\n\t2.Ver Dinosaurios Vivos\n\t3.Ver Dinosaurios Muertos\n\t4.Crear Dinosaurio"
                    +"\n\t"+MENU_SIZE+".Lanzar Meteorito");
            op=keyb.nextInt();
            switch(op){
                case 1:
                    habitat.bigBang();
                    break;
                case 2:
                    System.out.println(habitat.muestraDinosaurios());
                    break;
                case 3:
                    System.out.println(habitat.muestraDinosauriosMuertos());
                    break;
                case 4:
                    habitat.addDinosaurio(creaDinosaurio());
                    break;
                case MENU_SIZE:
                    habitat.lanzaMeteorito();
                    System.out.println("Este es el final");
                    break;
            }
        }while(op!=MENU_SIZE);
    }
    
}
