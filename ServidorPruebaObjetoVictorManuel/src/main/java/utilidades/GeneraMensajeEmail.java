/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

/**
 *
 * @author Victor
 */
public class GeneraMensajeEmail {
    public static String generaMensaje(String[] parts){
        StringBuilder concatenador=new StringBuilder();
        for(String part:parts){
            concatenador.append(part);
        }
        return concatenador.toString();
    }
}
