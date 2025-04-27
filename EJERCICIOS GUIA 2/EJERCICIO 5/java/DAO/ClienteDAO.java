package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.*;
import Util.*;

public class ClienteDAO {

    public int insertarCliente(Connection conexion, Cliente cliente) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;

        try {
            String sql = "INSERT INTO Clientes (nombre, apellidos, vip) VALUES (?, ?, ?)";
            stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellidos());
            stmt.setBoolean(3, cliente.isVip());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al insertar cliente, ninguna fila afectada");
            }

            generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Error al obtener ID generado para cliente");
            }
        } finally {
            if (generatedKeys != null) generatedKeys.close();
            if (stmt != null) stmt.close();
        }
    }

    public void eliminarCliente(Connection conexion, int clienteId) throws SQLException {
        String sql = "DELETE FROM Clientes WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            stmt.executeUpdate();
        }
    }

    public List<Cliente> obtenerTodosClientes(Connection conexion) throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT id, nombre, apellidos, vip FROM Clientes";
            stmt = conexion.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellidos(rs.getString("apellidos"));
                cliente.setVip(rs.getBoolean("vip"));

                clientes.add(cliente);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
        return clientes;
    }
}