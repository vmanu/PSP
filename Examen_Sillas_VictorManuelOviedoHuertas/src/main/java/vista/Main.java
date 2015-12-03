/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.util.InputMismatchException;
import java.util.Scanner;
import lugar.Sala;

/**
 *
 * @author dam2
 */
public class Main {

    public static int getOpcion(){
        Scanner keyb=new Scanner(System.in);
        int op=0;
        do{
            System.out.println("Introduce una opcion:\n\t1.Parar Juego");
            try{
                op=keyb.nextInt();
                if(op!=1){
                    System.out.print("El valor introducido no se encuentra entre los elegibles. ");
                    keyb.nextLine();
                }
            }catch(InputMismatchException e){
                keyb.nextLine();
                System.out.print("El valor introducido no es de tipo numerico entero (acorde a las opciones). ");
            }
        }while(op!=1);
        return op;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Sala mi=new Sala();
        getOpcion();
        mi.interrumpir();
    }
    
}
