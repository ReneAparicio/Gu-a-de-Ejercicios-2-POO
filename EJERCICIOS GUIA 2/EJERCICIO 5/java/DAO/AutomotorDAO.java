package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.*;
import Util.*;

public class AutomotorDAO {

    public void insertarAutomotor(Connection conexion, Automotor automotor) throws SQLException {
        PreparedStatement stmt = null;

        try {
            String sql = "INSERT INTO Automotor (marca, modelo, year, client_id) VALUES (?, ?, ?, ?)";
            stmt = conexion.prepareStatement(sql);

            stmt.setString(1, automotor.getMarca());
            stmt.setString(2, automotor.getModelo());
            stmt.setInt(3, automotor.getYear());
            stmt.setInt(4, automotor.getClientId());

            stmt.executeUpdate();
        } finally {
            if (stmt != null) stmt.close();
        }
    }

    public void eliminarAutomotoresPorCliente(Connection conexion, int clienteId) throws SQLException {
        String sql = "DELETE FROM Automotor WHERE client_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            stmt.executeUpdate();
        }
    }



    public List<Automotor> obtenerAutomotoresPorCliente(Connection conexion, int clientId) throws SQLException {
        List<Automotor> automotores = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT marca, modelo, year FROM Automotor WHERE client_id = ?";
            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, clientId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Automotor auto = new Automotor();
                auto.setMarca(rs.getString("marca"));
                auto.setModelo(rs.getString("modelo"));
                auto.setYear(rs.getInt("year"));
                automotores.add(auto);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
        return automotores;
    }
}