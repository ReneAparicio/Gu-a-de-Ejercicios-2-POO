package com.example.ejercicio2;

// Rafael Alejandro Escorcia Silizar ES232712

public class Persona {
    private String nombre;
    private String apellidos;
    private double salario;
    private String fechaIngreso;
    private int diasVacaciones;

    public Persona() {
    }

    public Persona(String nombre, String apellidos, double salario, String fechaIngreso, int diasVacaciones) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.salario = salario;
        this.fechaIngreso = fechaIngreso;
        this.diasVacaciones = diasVacaciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public int getDiasVacaciones() {
        return diasVacaciones;
    }

    public void setDiasVacaciones(int diasVacaciones) {
        this.diasVacaciones = diasVacaciones;
    }
}