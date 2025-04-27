<%@ page import="java.util.List" %>
<%@ page import="com.example.ejercicio1.Contacto" %>
<%@ page import="com.example.ejercicio1.ContactoDAO" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Contactos</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="container">
    <h1>Lista de Contactos</h1>

    <%
        ContactoDAO contactoDAO = new ContactoDAO();
        List<Contacto> contactos = contactoDAO.obtenerTodosContactos();

        if (contactos != null && !contactos.isEmpty()) {
    %>
    <table>
        <thead>
        <tr>
            <th>Nombre</th>
            <th>Apellidos</th>
            <th>Teléfono</th>
            <th>Correo Electrónico</th>
        </tr>
        </thead>
        <tbody>
        <% for (Contacto contacto : contactos) { %>
        <tr>
            <td><%= contacto.getNombre() %></td>
            <td><%= contacto.getApellidos() %></td>
            <td><%= contacto.getTelefono() %></td>
            <td><%= contacto.getEmail() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <br>
    <%
    } else {
    %>
    <p>No hay contactos en la agenda.</p>
    <%
        }
    %>

    <a href="index.jsp" class="button">Volver a la Agenda</a>
</div>
</body>
</html>