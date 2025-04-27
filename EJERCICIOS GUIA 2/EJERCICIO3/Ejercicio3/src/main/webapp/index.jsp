<%-- Rafael Alejandro Escorcia Siliézar ES232712 --%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.ejercicio3.Contacto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agenda de Contactos</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        /* Estilos (simplificados ya que no hay botones de acción) */
        .error-message { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; padding: 10px; margin-bottom: 15px; border-radius: 5px; }
        .success-message { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; padding: 10px; margin-bottom: 15px; border-radius: 5px; }
        body { font-family: sans-serif; margin: 20px; background-color: #f4f4f4; }
        .container { background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); max-width: 800px; margin: auto; }
        h1, h2, h3 { color: #333; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; }
        .form-group input[type="text"],
        .form-group input[type="email"] { width: calc(100% - 22px); padding: 10px; border: 1px solid #ccc; border-radius: 4px; }
        .button { background-color: #007bff; color: white; padding: 10px 15px; border: none; border-radius: 4px; cursor: pointer; font-size: 1em; margin-right: 10px; margin-bottom: 10px; }
        .button:hover { background-color: #0056b3; }
        .options, .contact-list, .search-results { margin-top: 20px; padding-top: 20px; border-top: 1px solid #eee; }
        .options form { margin-bottom: 10px; display: inline-block; vertical-align: top; margin-right: 15px; }
        ul { list-style-type: none; padding: 0; }
        li { background-color: #f9f9f9; border: 1px solid #eee; padding: 15px; margin-bottom: 10px; border-radius: 4px; }
        li strong { display: inline-block; min-width: 150px; margin-right: 5px; }
        /* Ya no se necesita .contact-actions, .edit-link, .delete-button, .delete-form */
    </style>
</head>
<body>
<div class="container">
    <h1>Agenda de Contactos</h1>

    <%-- Leer y mostrar mensajes desde la SESIÓN --%>
    <%
        HttpSession currentSession = request.getSession(false);
        List<String> errores = null;
        String mensaje = null;
        if (currentSession != null) {
            errores = (List<String>) currentSession.getAttribute("errores");
            mensaje = (String) currentSession.getAttribute("mensaje");
            if (errores != null) currentSession.removeAttribute("errores");
            if (mensaje != null) currentSession.removeAttribute("mensaje");
        }
        if (errores != null && !errores.isEmpty()) {
    %>
    <div class="error-message"><strong>Errores:</strong><ul><% for (String error : errores) { %><li><%= error %></li><% } %></ul></div>
    <% }
        if (mensaje != null && !mensaje.trim().isEmpty()) {
    %>
    <div class="success-message"><%= mensaje %></div>
    <% } %>

    <h2>Agregar Nuevo Contacto</h2>
    <form action="ContactoServlet" method="post" onsubmit="return validateForm()">
        <input type="hidden" name="action" value="agregar">
        <div class="form-group">
            <label for="nombre">Nombre:</label>
            <input type="text" id="nombre" name="nombre" placeholder="Nombre del contacto" pattern="^[a-zA-Z\s]+$" required>
            <div class="invalid-feedback" id="nombre-error">
                *Por favor, ingresar un nombre válido.
            </div>
        </div>
        <div class="form-group">
            <label for="apellidos">Apellidos:</label>
            <input type="text" id="apellidos" name="apellidos" placeholder="Apellidos del contacto" pattern="^[a-zA-Z\s]+$" required>
            <div class="invalid-feedback" id="apellidos-error">
                *Por favor, ingresar un apellido válido.
            </div>
        </div>
        <div class="form-group">
            <label for="telefono">Teléfono:</label>
            <input type="text" id="telefono" name="telefono" placeholder="0000-0000" minlenght="9" required>
            <div class="invalid-feedback" id="telefono-error">
                *Por favor, ingrese un teléfono válido (0000-0000).
            </div>
        </div>
        <div class="form-group">
            <label for="email">Correo Electrónico:</label>
            <input type="email" id="email" name="email" placeholder="ejemplo@example.com" required>
        </div>
        <button type="submit" class="button">Agregar Contacto</button>
    </form>

    <style>
        .invalid-feedback {
            color: #898181;
            display: none; /* Initially hide the error messages */
        }

        input:invalid[focus],
        input:invalid:focus {
            border-color: #898181;
            outline: none;
        }

        input:invalid ~ .invalid-feedback {
            display: block; /* Show the error message when the input is invalid */
        }
    </style>
    <script>
        function validateForm() {
            let nombre = document.getElementById("nombre");
            let apellidos = document.getElementById("apellidos");
            let telefono = document.getElementById("telefono");
            let isValid = true;

            if (!nombre.checkValidity()) {
                document.getElementById("nombre-error").style.display = "block";
                nombre.classList.add("is-invalid");
                isValid = false;
            } else {
                document.getElementById("nombre-error").style.display = "none";
                nombre.classList.remove("is-invalid");
            }

            if (!apellidos.checkValidity()) {
                document.getElementById("apellidos-error").style.display = "block";
                apellidos.classList.add("is-invalid");
                isValid = false;
            } else {
                document.getElementById("apellidos-error").style.display = "none";
                apellidos.classList.remove("is-invalid");
            }

            if (!telefono.checkValidity()) {
                document.getElementById("telefono-error").style.display = "block";
                telefono.classList.add("is-invalid");
                isValid = false;
            } else {
                document.getElementById("telefono-error").style.display = "none";
                telefono.classList.remove("is-invalid");
            }

            return isValid;
        }
    </script>
    <h2>Opciones</h2>
    <div class="options">
        <form action="ContactoServlet" method="get">
            <input type="hidden" name="action" value="listar">
            <button type="submit" class="button">Ver Lista Completa de contactos</button> <%-- Esto lleva a lista_contactos.jsp --%>
        </form>
        <form action="ContactoServlet" method="get">
            <input type="hidden" name="action" value="buscar">
            <div class="form-group">
                <label for="nombreBusqueda">Buscar por Apellido:</label>
                <% String busquedaAnterior = (String) request.getAttribute("busqueda"); %>
                <input type="text" id="nombreBusqueda" name="nombreBusqueda" value="<%= busquedaAnterior != null ? busquedaAnterior : "" %>">
            </div>
            <button type="submit" class="button">Buscar Contacto</button> <%-- Esto muestra resultados aquí mismo --%>
        </form>
    </div>

    <%-- Sección de Resultados de Búsqueda (se muestra en index.jsp) --%>
    <%
        List<Contacto> resultadosBusqueda = (List<Contacto>) request.getAttribute("resultados");
        String busqueda = (String) request.getAttribute("busqueda");
        boolean huboBusqueda = request.getAttribute("huboBusqueda") != null && (Boolean)request.getAttribute("huboBusqueda");

        if (huboBusqueda) {
            if (resultadosBusqueda == null) resultadosBusqueda = new ArrayList<>();
    %>
    <div class="search-results">
        <h3>Resultados de la Búsqueda para "<%= busqueda != null ? busqueda : "" %>"</h3>
        <% if (resultadosBusqueda.isEmpty()) { %>
        <p>No se encontraron contactos con ese nombre.</p>
        <% } else { %>
        <ul>
            <% for (Contacto contacto : resultadosBusqueda) { %>
            <li>
                <strong>Nombre:</strong> <%= contacto.getNombre()%><br>
                <strong>Apellidos</strong> <%= contacto.getApellidos()%><br>
                <strong>Teléfono:</strong> <%= contacto.getTelefono() != null && !contacto.getTelefono().isEmpty() ? contacto.getTelefono() : "N/A" %><br>
                <strong>Correo Electrónico:</strong> <%= contacto.getEmail() %>
                <%-- Botones Editar y Eliminar quitados --%>
            </li>
            <% } %>
        </ul>
        <% } %>
    </div>
    <% } %>

    <%-- La sección de Lista Completa ya no se muestra aquí, se muestra en lista_contactos.jsp --%>

</div>
</body>
</html>
