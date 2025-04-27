<%-- 26-04-2025 Rene Aparicio --%>
<%@ page contentType="text/html; charset=UTF-8"
         import="java.util.List,com.udb.ejercicio04.modelo.Ejercicio04" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Ejercicio04 – Importadora de Vehículos</title>
    <style>
        /* Estilos para el formulario */
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            background: #e6f2ff;
            color: #333;
        }
        .container {
            max-width: 900px;
            margin: 40px auto;
            background: #fff;
            padding: 20px 30px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }
        h1, h2 {
            text-align: center;
            color: #005b96;
            margin-bottom: 20px;
        }

        form {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            justify-content: space-between;
        }
        form p {
            flex: 1 1 45%;
            margin: 0;
        }
        form p.full-row {
            flex: 1 1 100%;
        }
        form label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }
        form input[type="text"],
        form select {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        form .radio-group {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        form button {
            background: #005b96;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            transition: background 0.2s ease;
            margin-top: 10px;
        }
        form button:hover {
            background: #004570;
        }

        .error {
            background: #ffdddd;
            border: 1px solid #ff5c5c;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 20px;
            list-style-type: none;
        }
        .error li {
            color: #a10000;
            margin-left: 0;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: center;
        }
        th {
            background: #005b96;
            color: #fff;
        }
        tr:nth-child(even) td {
            background: #f2f9ff;
        }
        .logo {
            max-height: 40px;
            display: block; margin: 0 auto;
        }

        .section { margin-top: 40px; }
    </style>
</head>
<body>
<div class="container">

    <h1>Ejercicio04: Importadora</h1>

    <%-- mostrar errores de validación --%>
    <%
        List<String> errores = (List<String>) request.getAttribute("errores");
        if (errores != null && !errores.isEmpty()) {
    %>
    <ul class="error">
        <% for (String err : errores) { %>
        <li><%= err %></li>
        <% } %>
    </ul>
    <%
        }
    %>

    <%-- formulario de registro --%>
    <form action="ejercicio04" method="post">
        <p>
            <label for="nombre">Nombre completo:</label>
            <input id="nombre" type="text" name="nombre"
                   value="<%= request.getParameter("nombre")==null ? "" : request.getParameter("nombre") %>">
        </p>
        <p class="full-row">
            <label>Sexo:</label>
        <div class="radio-group">
            <label><input type="radio" name="sexo" value="Masculino"
                <%= "Masculino".equals(request.getParameter("sexo")) ? "checked" : "" %>>
                Masculino</label>
            <label><input type="radio" name="sexo" value="Femenino"
                <%= "Femenino".equals(request.getParameter("sexo")) ? "checked" : "" %>>
                Femenino</label>
        </div>
        </p>
        <p>
            <label for="marca">Marca:</label>
            <select id="marca" name="marca">
                <option value="">--Seleccione--</option>
                <option value="Nissan" <%= "Nissan".equals(request.getParameter("marca")) ? "selected" : "" %>>Nissan</option>
                <option value="Toyota" <%= "Toyota".equals(request.getParameter("marca")) ? "selected" : "" %>>Toyota</option>
                <option value="Kia"    <%= "Kia".equals(request.getParameter("marca"))    ? "selected" : "" %>>Kia</option>
            </select>
        </p>
        <p>
            <label for="anio">Año (2000–2025):</label>
            <input id="anio" type="text" name="anio" size="4"
                   value="<%= request.getParameter("anio")==null ? "" : request.getParameter("anio") %>">
        </p>
        <p class="full-row">
            <label for="precio">Precio:</label>
            <input id="precio" type="text" name="precio"
                   value="<%= request.getParameter("precio")==null ? "" : request.getParameter("precio") %>">
        </p>
        <p class="full-row" style="text-align:center;">
            <button type="submit">Registrar Venta</button>
        </p>
    </form>

    <%-- listado de ventas en sesión --%>
    <div class="section">
        <%
            List<Ejercicio04> ventas = (List<Ejercicio04>) request.getAttribute("ventas");
            if (ventas != null && !ventas.isEmpty()) {
        %>
        <h2>Ventas Registradas</h2>
        <table>
            <tr><th>Nombre</th><th>Sexo</th><th>Marca</th><th>Año</th><th>Precio</th></tr>
            <% for (Ejercicio04 v : ventas) { %>
            <tr>
                <td><%= v.getNombreCliente() %></td>
                <td><%= v.getSexo()          %></td>
                <td><%= v.getMarca()         %></td>
                <td><%= v.getAnio()          %></td>
                <td><%= v.getPrecio()        %></td>
            </tr>
            <% } %>
        </table>
        <%
            }
        %>
    </div>

    <%-- estadísticas con logos y rangos de año --%>
    <div class="section">
        <h2>Estadísticas de Ventas</h2>
        <table>
            <tr><th>Marca</th><th>Total Vendidos</th><th>Suma de Precios</th></tr>
            <tr>
                <td><img class="logo" src="https://wallpapers.com/images/hd/nissan-automotive-brand-logo-gcfu8m53losfzum1.png" alt="Nissan"></td>
                <td><%= request.getAttribute("countNissan") %></td>
                <td><%= request.getAttribute("sumNissan")   %></td>
            </tr>
            <tr>
                <td><img class="logo" src="https://images.seeklogo.com/logo-png/17/2/toyota-logo-png_seeklogo-171947.png" alt="Toyota"></td>
                <td><%= request.getAttribute("countToyota") %></td>
                <td><%= request.getAttribute("sumToyota")   %></td>
            </tr>
            <tr>
                <td><img class="logo" src="https://logos-world.net/wp-content/uploads/2021/03/Kia-Logo.png" alt="Kia"></td>
                <td><%= request.getAttribute("countKia")    %></td>
                <td><%= request.getAttribute("sumKia")      %></td>
            </tr>
        </table>

        <h2>Ventas por Rango de Año</h2>
        <table>
            <tr><th>2000–2015</th><th>2016–2025</th></tr>
            <tr>
                <td><%= request.getAttribute("count2000_2015") %></td>
                <td><%= request.getAttribute("count2016_2025") %></td>
            </tr>
        </table>
    </div>

</div>
</body>
</html>







