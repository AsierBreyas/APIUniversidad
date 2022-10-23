package com.example.apiuniversidad;

public class ListUniversidad {
    public String web;
    public String nombre;

    public ListUniversidad(String web, String nombre) {
        this.web = web;
        this.nombre = nombre;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
