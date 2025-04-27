<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contacto Agregado</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="container">
    <h1>Contacto Agregado Exitosamente</h1>
    <p>Los siguientes detalles han sido guardados:</p>
    <ul>
        <li><strong>Nombre:</strong> <%= request.getAttribute("nombre") %></li>
        <li><strong>Teléfono:</strong> <%= request.getAttribute("telefono") %></li>
        <li><strong>Correo Electrónico:</strong> <%= request.getAttribute("email") %></li>
    </ul>
    <p><a href="index.jsp" class="button">Volver a la Agenda</a></p>
</div>
</body>
</html>