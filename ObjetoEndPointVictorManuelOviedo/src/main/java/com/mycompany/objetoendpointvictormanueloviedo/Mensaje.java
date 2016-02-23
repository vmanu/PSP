/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.objetoendpointvictormanueloviedo;

/**
 *
 * @author oscar
 */
public class Mensaje {
    
    private String room;
    private String mensaje;
    private String from;
    private boolean formateado;
    private boolean enforceCreation;

    public boolean isEnforceCreation() {
        return enforceCreation;
    }

    public void setEnforceCreation(boolean enforceCreation) {
        this.enforceCreation = enforceCreation;
    }

    public boolean isFormateado() {
        return formateado;
    }

    public void setFormateado(boolean formateado) {
        this.formateado = formateado;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
    

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Mensaje() {
    }
    
}