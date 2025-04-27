package Model;
// Getters y Setters de los clientes
public class Cliente {
    private int id;
    private String nombre;
    private String apellidos;
    private boolean vip;

    public Cliente() {}

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    // Para booleanos se usa "is" en lugar de "get"
    public boolean isVip() { return vip; }
    public void setVip(boolean vip) { this.vip = vip; }
}