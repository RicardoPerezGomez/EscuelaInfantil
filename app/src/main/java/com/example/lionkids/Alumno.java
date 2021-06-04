package com.example.lionkids;

public class Alumno {

    public String nombre, direccion, edad, padre, url;

    public Alumno(){

    }



    public Alumno(String nombre, String direccion, String edad, String padre, String url) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.edad = edad;
        this.padre = padre;
        this.url = url;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
