<!DOCTYPE html>
<html lang="es">
<head>
    <title>Resultado Estudiante</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #e8f0fe;
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-top: 50px;
        }
        h1 {
            color: #333;
        }
        table {
            background: #fff;
            border-collapse: collapse;
            width: 400px;
            margin-top: 20px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            border-radius: 10px;
            overflow: hidden;
        }
        td {
            padding: 12px 15px;
            border-bottom: 1px solid #ddd;
        }
        tr:last-child td {
            border-bottom: none;
        }
        a {
            margin-top: 20px;
            text-decoration: none;
            color: #ffffff;
            background-color: #2196F3;
            padding: 10px 20px;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        a:hover {
            background-color: #0b7dda;
        }
    </style>
</head>
<body>
<h1>Datos del Estudiante</h1>
<table>
    <tr><td><strong>Carnet</strong></td><td><%= request.getAttribute("carnet") %></td></tr>
    <tr><td><strong>Nombres</strong></td><td><%= request.getAttribute("nombres") %></td></tr>
    <tr><td><strong>Apellidos</strong></td><td><%= request.getAttribute("apellidos") %></td></tr>
    <tr><td><strong>Dirección</strong></td><td><%= request.getAttribute("direccion") %></td></tr>
    <tr><td><strong>Teléfono</strong></td><td><%= request.getAttribute("telefono") %></td></tr>
    <tr><td><strong>Email</strong></td><td><%= request.getAttribute("email") %></td></tr>
    <tr><td><strong>Fecha de nacimiento</strong></td><td><%= request.getAttribute("fechaNacimiento") %></td></tr>
</table>

<a href="index.jsp">Volver al formulario</a>
</body>
</html>