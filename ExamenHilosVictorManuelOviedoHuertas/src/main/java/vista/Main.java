/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.util.InputMismatchException;
import java.util.Scanner;
import lugares.Pista;

/**
 *
 * @author dam2
 */
public class Main {

    public static int getOpcion(){
        Scanner keyb=new Scanner(System.in);
        int op=0;
        do{
            System.out.println("Introduce una opcion:\n\t1.Ver estado Juego\n\t2.Parar Juego\n\t3.Noticias");
            try{
                op=keyb.nextInt();
                if(op<1||op>3){
                    System.out.print("El valor introducido no se encuentra entre los elegibles. ");
                    keyb.nextLine();
                }
            }catch(InputMismatchException e){
                keyb.nextLine();
                System.out.print("El valor introducido no es de tipo numerico entero (acorde a las opciones). ");
            }
        }while(op<1||op>3);
        return op;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Pista miPista=new Pista();
        int op;
        do{
            op=getOpcion();
            switch(op){
                case 1:
                    System.out.print(miPista.muestraNiños());
                    break;
                case 2:
                    miPista.interrumpir();
                    break;
                case 3:
                    System.out.println(miPista.dameNoticias());
            }
        }while(op!=2);
    }
    
}
