/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.objetopruebavictormanuel;

import java.util.Date;

/**
 *
 * @author dam2
 */
public class Juego {

    private int id;
    private String nombre;
    private Date fecha_creacion;
    private int ventas;
    private int tipo;
    private int creador;

    public Juego(int id, String nombre,Date fecha_creacion,int ventas, int tipo,int creador) {
        this.id = id;
        this.nombre = nombre;
        this.fecha_creacion=fecha_creacion;
        this.ventas=ventas;
        this.tipo=tipo;
        this.creador=creador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public int getVentas() {
        return ventas;
    }

    public void setVentas(int ventas) {
        this.ventas = ventas;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getCreador() {
        return creador;
    }

    public void setCreador(int creador) {
        this.creador = creador;
    }
}
