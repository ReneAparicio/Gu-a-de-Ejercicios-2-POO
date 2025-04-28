<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%--  Rafael Alejandro Escorcia Siliezar ES232712 --%>
<!DOCTYPE html>
<html>
<head>
  <title>Cálculo de Vacaciones</title>
  <style>
    .error {
      color: red;
    }
  </style>
</head>
<body>
<h2>Ingrese los datos del empleado</h2>
<form action="VacacionesServlet" method="post">
  <div>
    <label for="nombre">Nombre:</label>
    <input type="text" id="nombre" name="nombre" required>
  </div>
  <div>
    <label for="apellidos">Apellidos:</label>
    <input type="text" id="apellidos" name="apellidos" required>
  </div>
  <div>
    <label for="salario">Salario:</label>
    <input type="number" id="salario" name="salario" step="0.01" required>
  </div>
  <div>
    <label for="fechaIngreso">Fecha de Ingreso (YYYY-MM-DD):</label>
    <input type="date" id="fechaIngreso" name="fechaIngreso" required>
  </div>
  <input type="submit" value="Calcular Vacaciones">
  <%--  Mostrar errores si existen --%>
  <% if (request.getAttribute("errores") != null) { %>
  <div class="error">
    <h3>Errores:</h3>
    <ul>
      <% for (String error : (List<String>) request.getAttribute("errores")) { %>
      <li><%= error %></li>
      <% } %>
    </ul>
  </div>
  <% } %>
</form>
</body>
</html>
