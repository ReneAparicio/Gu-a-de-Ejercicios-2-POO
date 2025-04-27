<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="Model.*" %>
<%@ page import="DAO.*" %>
<%@ page import="Util.DatabaseUtil" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.SQLException" %>


<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Clientes - Car Clean</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-5">
    <div class="card shadow-sm border-0 rounded-4">
        <div class="card-body">
            <%-- Mensajes de éxito/error --%>
            <c:if test="${not empty mensajeExito}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <i class="bi bi-check-circle-fill"></i> ${mensajeExito}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Cerrar"></button>
                </div>
            </c:if>

            <c:if test="${not empty mensajeError}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <i class="bi bi-exclamation-triangle-fill"></i> ${mensajeError}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Cerrar"></button>
                </div>
            </c:if>

            <%-- Encabezado y botón nuevo --%>
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2 class="card-title mb-0">Clientes Registrados</h2>
                <a href="registroCliente.jsp" class="btn btn-primary">
                    <i class="bi bi-plus-circle"></i> Nuevo Cliente
                </a>
            </div>

            <%-- Tabla de clientes --%>
            <div class="table-responsive">
                <table class="table table-hover align-middle">
                    <thead class="table-primary">
                    <tr>
                        <th>Nombre</th>
                        <th>Vehículo</th>
                        <th>Servicio</th>
                        <th>Total a Pagar</th>
                        <th>Acciones</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        Connection conexion = null;
                        try {
                            conexion = DatabaseUtil.getConnection();
                            ClienteDAO clienteDAO = new ClienteDAO();
                            AutomotorDAO automotorDAO = new AutomotorDAO();
                            ServicioDAO servicioDAO = new ServicioDAO();

                            List<Cliente> clientes = clienteDAO.obtenerTodosClientes(conexion);

                            // Aquí creamos dos Mapas para almacenar automotores y servicios por cliente
                            Map<Integer, List<Automotor>> automotoresPorCliente = new HashMap<>();
                            Map<Integer, List<Servicio>> serviciosPorCliente = new HashMap<>();

                            for (Cliente cliente : clientes) {
                                List<Automotor> automotores = automotorDAO.obtenerAutomotoresPorCliente(conexion, cliente.getId());
                                automotoresPorCliente.put(cliente.getId(), automotores);

                                List<Servicio> servicios = servicioDAO.obtenerServiciosPorCliente(conexion, cliente.getId());
                                serviciosPorCliente.put(cliente.getId(), servicios);
                            }

                            // Los ponemos en atributos
                            request.setAttribute("clientes", clientes);
                            request.setAttribute("automotoresPorCliente", automotoresPorCliente);
                            request.setAttribute("serviciosPorCliente", serviciosPorCliente);

                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            if (conexion != null) conexion.close();
                        }
                    %>

                    <c:forEach items="${clientes}" var="cliente">
                        <tr>
                            <td>${cliente.nombre} ${cliente.apellidos}</td>
                            <td>
                                <c:forEach items="${automotoresPorCliente[cliente.id]}" var="auto">
                                    ${auto.marca} ${auto.modelo} (${auto.year})
                                    <br/>
                                </c:forEach>
                            </td>
                            <td>
                                <c:forEach items="${serviciosPorCliente[cliente.id]}" var="servicio">
                                    ${servicio.tipo} - $${servicio.precio}
                                    <br/>
                                    <c:if test="${cliente.vip}">
                                        (15% descuento)
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>
                                <c:set var="total" value="0" scope="page" />
                                <c:forEach items="${serviciosPorCliente[cliente.id]}" var="servicio">
                                    <c:set var="precio" value="${servicio.precio}" />
                                    <c:if test="${cliente.vip}">
                                        <c:set var="precio" value="${precio * 0.85}" />
                                    </c:if>
                                    <c:set var="total" value="${total + precio}" />
                                </c:forEach>
                                $${total}
                            </td>
                            <td>
                                <form action="ProcesarCliente" method="post" style="display:inline;">
                                    <input type="hidden" name="accion" value="eliminar">
                                    <input type="hidden" name="clienteId" value="${cliente.id}">
                                    <button type="submit" onclick="return confirm('¿Estás seguro de que deseas eliminar este cliente?')">Eliminar</button>
                                </form>

                            </td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>