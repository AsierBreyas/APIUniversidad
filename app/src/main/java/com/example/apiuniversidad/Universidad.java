package com.example.apiuniversidad;

public class Universidad {
    private String web;
    private String nombre;

    public Universidad(String nombre, String web){
        this.nombre = nombre;
        this.web = web;
    }
    public String getWeb(){
        return web;
    }
    public void setWeb(String web){
        this.web = web;
    }
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
}
