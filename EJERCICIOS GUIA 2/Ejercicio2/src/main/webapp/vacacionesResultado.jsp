<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.ejercicio2.Persona" %>  <%--  Ajusta el paquete --%>

<%--  Rafael Alejandro Escorcia Siliezar ES232712 --%>
<!DOCTYPE html>
<html>
<head>
  <title>Resultado de Vacaciones</title>
</head>
<body>
<h2>Información del Empleado y Vacaciones</h2>
<%
  Persona persona = (Persona) request.getAttribute("persona");
  if (persona != null) {
%>
<p>Nombre: <%= persona.getNombre() %></p>
<p>Apellidos: <%= persona.getApellidos() %></p>
<p>Salario: $<%= persona.getSalario() %></p>
<p>Fecha de Ingreso: <%= persona.getFechaIngreso() %></p>
<p>Días de Vacaciones Correspondientes: <%= persona.getDiasVacaciones() %></p>
<%
} else {
%>
<p>No hay datos de empleado para mostrar.</p>
<%
  }
%>
</body>
</html>