package Model;

public class Automotor {
    private int id;
    private String marca;
    private String modelo;
    private int year;
    private int clientId; // Clave foránea

    public Automotor() {}

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }
}