package com.udb.ejercicio04.modelo;

import java.io.Serializable;

public class Ejercicio04 implements Serializable {
    // Datos que van a describen una venta
    private String nombreCliente;
    private String sexo;
    private String marca;
    private int anio;
    private double precio;

    // Constructor sin parámetros (necesario para JSP/beans)
    public Ejercicio04() { }

    // Crea una venta completa con toda la información
    public Ejercicio04(String nombreCliente, String sexo,
                       String marca, int anio, double precio) {
        this.nombreCliente = nombreCliente;
        this.sexo          = sexo;
        this.marca         = marca;
        this.anio          = anio;
        this.precio        = precio;
    }

    // modificación de los parámetros
    public String getNombreCliente() {
        return nombreCliente;
    }
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
}
