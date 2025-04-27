package com.udb.ejercicio04.controlador;

import com.udb.ejercicio04.modelo.Ejercicio04;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ejercicio04")
public class Ejercicio04Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();

        // Recuperar la lista de ventas de la sesión
        @SuppressWarnings("unchecked")
        List<Ejercicio04> ventas = (List<Ejercicio04>) session.getAttribute("ventas");
        if (ventas == null) {
            ventas = new ArrayList<>();
        }
        req.setAttribute("ventas", ventas);

        //Calcular estadísticas por marca y por rango de año
        int countNissan = 0, countToyota = 0, countKia = 0;
        double sumNissan = 0, sumToyota = 0, sumKia = 0;
        int count2000_2015 = 0, count2016_2025 = 0;

        for (Ejercicio04 v : ventas) {
            switch (v.getMarca()) {
                case "Nissan":
                    countNissan++;
                    sumNissan += v.getPrecio();
                    break;
                case "Toyota":
                    countToyota++;
                    sumToyota += v.getPrecio();
                    break;
                case "Kia":
                    countKia++;
                    sumKia += v.getPrecio();
                    break;
            }
            int año = v.getAnio();
            if (año >= 2000 && año <= 2015)      count2000_2015++;
            else if (año >= 2016 && año <= 2025) count2016_2025++;
        }

        // Guardar estadísticas como atributos de petición
        req.setAttribute("countNissan",    countNissan);
        req.setAttribute("sumNissan",      sumNissan);
        req.setAttribute("countToyota",    countToyota);
        req.setAttribute("sumToyota",      sumToyota);
        req.setAttribute("countKia",       countKia);
        req.setAttribute("sumKia",         sumKia);
        req.setAttribute("count2000_2015", count2000_2015);
        req.setAttribute("count2016_2025", count2016_2025);

        // Forward a la JSP
        req.getRequestDispatcher("/index.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // Lee parámetros
        String nombre    = req.getParameter("nombre");
        String sexo      = req.getParameter("sexo");
        String marca     = req.getParameter("marca");
        String anioStr   = req.getParameter("anio");
        String precioStr = req.getParameter("precio");

        List<String> errores = new ArrayList<>();
        int anio = 0;
        double precio = 0;

        // Validaciones
        if (nombre == null || nombre.isBlank())
            errores.add("Nombre obligatorio.");
        if (sexo == null)
            errores.add("Sexo obligatorio.");
        if (marca == null || marca.isBlank())
            errores.add("Marca obligatoria.");

        try {
            anio = Integer.parseInt(anioStr);
            if (anio < 2000 || anio > 2025)
                errores.add("Año debe estar entre 2000 y 2025.");
        } catch (Exception e) {
            errores.add("Año inválido.");
        }

        try {
            precio = Double.parseDouble(precioStr);
            if (precio < 0) errores.add("Precio no puede ser negativo.");
        } catch (Exception e) {
            errores.add("Precio inválido.");
        }

        //Si hay errores, devolver al GET con mensajes
        if (!errores.isEmpty()) {
            req.setAttribute("errores", errores);
            doGet(req, resp);
            return;
        }

        // Crear objeto y añadir a la sesión
        Ejercicio04 venta = new Ejercicio04(nombre, sexo, marca, anio, precio);
        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        List<Ejercicio04> ventas = (List<Ejercicio04>) session.getAttribute("ventas");
        if (ventas == null) {
            ventas = new ArrayList<>();
            session.setAttribute("ventas", ventas);
        }
        ventas.add(venta);

        // Redirect para evitar repost
        resp.sendRedirect(req.getContextPath() + "/ejercicio04");
    }
}


