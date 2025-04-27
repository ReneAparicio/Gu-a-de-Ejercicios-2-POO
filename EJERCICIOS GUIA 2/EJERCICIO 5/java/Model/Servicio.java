package Model;
public class Servicio implements CalculadoraPago {
    private int id;
    private String tipo;
    private double precio;
    private int clientId; // Clave foránea

    // Constructor vacío
    public Servicio() {}

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }

    // Implementación de la interfaz
    @Override
    public double totalPago(boolean isVip) {
        return isVip ? precio * 0.85 : precio; // 15% descuento si es VIP
    }
}