package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.*;
import Util.*;

public class ServicioDAO {

    public void insertarServicio(Connection conexion, Servicio servicio) throws SQLException {
        PreparedStatement stmt = null;

        try {
            String sql = "INSERT INTO Servicio (tipo, precio, client_id) VALUES (?, ?, ?)";
            stmt = conexion.prepareStatement(sql);

            stmt.setString(1, servicio.getTipo());
            stmt.setDouble(2, servicio.getPrecio());
            stmt.setInt(3, servicio.getClientId());

            stmt.executeUpdate();
        } finally {
            if (stmt != null) stmt.close();
        }
    }

    public void eliminarServiciosPorCliente(Connection conexion, int clienteId) throws SQLException {
        String sql = "DELETE FROM Servicio WHERE client_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            stmt.executeUpdate();
        }
    }

    public List<Servicio> obtenerServiciosPorCliente(Connection conexion, int clientId) throws SQLException {
        List<Servicio> servicios = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT tipo, precio FROM Servicio WHERE client_id = ?";
            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, clientId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Servicio servicio = new Servicio();
                servicio.setTipo(rs.getString("tipo"));
                servicio.setPrecio(rs.getDouble("precio"));
                servicios.add(servicio);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
        return servicios;
    }
}