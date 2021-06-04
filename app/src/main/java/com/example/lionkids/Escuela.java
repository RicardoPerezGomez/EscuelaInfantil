package com.example.lionkids;

public class Escuela {

    public String id, nombre, direccion, email, movil;

    public Escuela (){

    }

    public Escuela(String nombre, String direccion, String email, String movil) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.movil = movil;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
