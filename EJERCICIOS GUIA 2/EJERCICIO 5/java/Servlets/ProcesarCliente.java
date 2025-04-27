package Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import DAO.AutomotorDAO;
import DAO.ClienteDAO;
import DAO.ServicioDAO;
import Model.Automotor;
import Model.Cliente;
import Model.Servicio;
import Util.DatabaseUtil;

@WebServlet("/ProcesarCliente")
public class ProcesarCliente extends HttpServlet {
    private ClienteDAO clienteDAO;
    private AutomotorDAO automotorDAO;
    private ServicioDAO servicioDAO;

    @Override
    public void init() {
        clienteDAO = new ClienteDAO();
        automotorDAO = new AutomotorDAO();
        servicioDAO = new ServicioDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("eliminar".equalsIgnoreCase(accion)) {
            eliminarClienteConRelacionados(request, response);
        } else {
            Map<String, String> errores = validarCampos(request);
            if (!errores.isEmpty()) {
                forwardConErrores(request, response, errores);
                return;
            }

            Connection conexion = null;
            try {
                // Obtener conexión y control transaccional
                conexion = DatabaseUtil.getConnection();
                if (conexion == null) {
                    throw new SQLException("No se pudo establecer conexión a la base de datos");
                }
                conexion.setAutoCommit(false); // Inicio transacción

                // 1. Insertar Cliente
                Cliente cliente = crearClienteDesdeRequest(request);
                int clienteId = clienteDAO.insertarCliente(conexion, cliente);

                // 2. Insertar Automotor
                Automotor automotor = crearAutomotorDesdeRequest(request, clienteId);
                automotorDAO.insertarAutomotor(conexion, automotor);

                // 3. Insertar Servicio
                Servicio servicio = crearServicioDesdeRequest(request, clienteId);
                servicioDAO.insertarServicio(conexion, servicio);

                conexion.commit(); // Confirmar transacción
                response.sendRedirect("listaClientes.jsp");

            } catch (SQLException e) {
                manejarErrorTransaccion(conexion, request, response, "Error de base de datos: " + e.getMessage());
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                manejarErrorTransaccion(conexion, request, response, "Datos inválidos: " + e.getMessage());
            } catch (Exception e) {
                manejarErrorTransaccion(conexion, request, response, "Error inesperado: " + e.getMessage());
            } finally {
                cerrarConexion(conexion);
            }
        }
    }

    private void eliminarClienteConRelacionados(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Connection conexion = null;
        try {
            conexion = DatabaseUtil.getConnection();
            if (conexion == null) {
                throw new SQLException("No se pudo establecer conexión a la base de datos");
            }
            conexion.setAutoCommit(false); // Transacción para asegurar integridad

            int clienteId = Integer.parseInt(request.getParameter("clienteId"));

            servicioDAO.eliminarServiciosPorCliente(conexion, clienteId);

            automotorDAO.eliminarAutomotoresPorCliente(conexion, clienteId);

            clienteDAO.eliminarCliente(conexion, clienteId);

            conexion.commit();
            response.sendRedirect("listaClientes.jsp");

        } catch (SQLException | NumberFormatException e) {
            manejarErrorTransaccion(conexion, request, response, "Error al eliminar cliente: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarConexion(conexion);
        }
    }


    // Métodos auxiliares
    private Map<String, String> validarCampos(HttpServletRequest request) {
        Map<String, String> errores = new HashMap<>();

        validarCampoRequerido(request, "nombre", "Nombre", 50, errores);
        validarCampoRequerido(request, "apellidos", "Apellidos", 50, errores);
        validarCampoRequerido(request, "marca", "Marca", 30, errores);
        validarCampoRequerido(request, "modelo", "Modelo", 30, errores);
        validarCampoRequerido(request, "tipo_servicio", "Tipo de servicio", 20, errores);

        try {
            int year = Integer.parseInt(request.getParameter("year"));
            if (year < 1900 || year > java.time.Year.now().getValue()) {
                errores.put("year", "Año debe estar entre 1900 y el actual");
            }
        } catch (NumberFormatException e) {
            errores.put("year", "Formato de año inválido");
        }

        return errores;
    }

    private void validarCampoRequerido(HttpServletRequest request, String campo, String nombreCampo,
                                       int maxLength, Map<String, String> errores) {
        String valor = request.getParameter(campo);
        if (valor == null || valor.trim().isEmpty()) {
            errores.put(campo, nombreCampo + " es obligatorio");
        } else if (valor.length() > maxLength) {
            errores.put(campo, nombreCampo + " no puede exceder " + maxLength + " caracteres");
        }
    }

    private Cliente crearClienteDesdeRequest(HttpServletRequest request) {
        Cliente cliente = new Cliente();
        cliente.setNombre(request.getParameter("nombre").trim());
        cliente.setApellidos(request.getParameter("apellidos").trim());
        cliente.setVip(request.getParameter("vip") != null);
        return cliente;
    }

    private Automotor crearAutomotorDesdeRequest(HttpServletRequest request, int clienteId) {
        Automotor auto = new Automotor();
        auto.setMarca(request.getParameter("marca").trim());
        auto.setModelo(request.getParameter("modelo").trim());
        auto.setYear(Integer.parseInt(request.getParameter("year")));
        auto.setClientId(clienteId);
        return auto;
    }

    private Servicio crearServicioDesdeRequest(HttpServletRequest request, int clienteId) {
        Servicio servicio = new Servicio();
        String tipoServicio = request.getParameter("tipo_servicio");
        servicio.setTipo(tipoServicio);
        servicio.setPrecio(obtenerPrecioServicio(tipoServicio));
        servicio.setClientId(clienteId);
        return servicio;
    }

    private double obtenerPrecioServicio(String tipo) {
        switch (tipo) {
            case "Motocicleta": return 2.75;
            case "Sedan": return 3.50;
            case "Camioneta": return 4.00;
            case "Microbus": return 5.00;
            case "Autobus": return 7.00;
            default: throw new IllegalArgumentException("Servicio no válido: " + tipo);
        }
    }

    private void forwardConErrores(HttpServletRequest request, HttpServletResponse response,
                                   Map<String, String> errores) throws ServletException, IOException {
        request.setAttribute("nombre", request.getParameter("nombre"));
        request.setAttribute("apellidos", request.getParameter("apellidos"));
        request.setAttribute("marca", request.getParameter("marca"));
        request.setAttribute("modelo", request.getParameter("modelo"));
        request.setAttribute("year", request.getParameter("year"));
        request.setAttribute("vipChecked", request.getParameter("vip") != null ? "checked" : "");

        for (Map.Entry<String, String> error : errores.entrySet()) {
            request.setAttribute("error_" + error.getKey(), error.getValue());
        }
        request.getRequestDispatcher("registroCliente.jsp").forward(request, response);
    }

    private void manejarErrorTransaccion(Connection conexion, HttpServletRequest request,
                                         HttpServletResponse response, String mensaje) throws ServletException, IOException {
        try {
            if (conexion != null) conexion.rollback();
        } catch (SQLException e) {
            mensaje += " | Error en rollback: " + e.getMessage();
        }
        request.setAttribute("error", mensaje);
        forwardConErrores(request, response, new HashMap<>());
    }

    private void cerrarConexion(Connection conexion) {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
}