<!DOCTYPE html>
<html lang="es">
<head>
    <title>Ficha de Estudiante</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f2f5;
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-top: 50px;
        }
        h1 {
            color: #333;
        }
        form {
            background: #fff;
            padding: 20px 40px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            width: 350px;
        }
        input[type="text"], input[type="email"] {
            width: 100%;
            padding: 8px;
            margin: 8px 0 16px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 10px;
            width: 100%;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        .error {
            color: red;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
<h1>Ficha de Estudiante</h1>

<% if (request.getAttribute("error") != null) { %>
<div class="error"><%= request.getAttribute("error") %></div>
<% } %>

<form action="procesarEstudiante" method="post">
    Carnet: <input type="text" name="carnet" required>
    Nombres: <input type="text" name="nombres" required>
    Apellidos: <input type="text" name="apellidos" required>
    Direccion: <input type="text" name="direccion" required>
    Telefono (0000-0000): <input type="text" name="telefono" required>
    Email: <input type="email" name="email" required>
    Fecha de nacimiento (DD/MM/YYYY): <input type="text" name="fechaNacimiento" required>

    <input type="submit" value="Enviar">
</form>
</body>
</html>