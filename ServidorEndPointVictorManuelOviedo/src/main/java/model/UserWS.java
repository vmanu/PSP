/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author dam2
 */
public class UserWS {
    private String nombre;
    private ArrayList<String>rooms;
    
    public UserWS(){
        rooms=new ArrayList();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<String> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<String> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "UserWS{" + "nombre=" + nombre + ", rooms=" + rooms + '}';
    }    
}
