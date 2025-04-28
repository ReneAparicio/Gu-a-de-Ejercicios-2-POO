package com.example.ejercicio2;

 // Rafael Alejandro Escorcia Silizar ES232712

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "VacacionesServlet", urlPatterns = {"/VacacionesServlet"})
public class VacacionesServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String salarioStr = request.getParameter("salario");
        String fechaIngresoStr = request.getParameter("fechaIngreso");

        List<String> errores = new ArrayList<>();

        // Validaciones
        if (nombre == null || nombre.trim().isEmpty()) {
            errores.add("El nombre es requerido.");
        }
        if (apellidos == null || apellidos.trim().isEmpty()) {
            errores.add("Los apellidos son requeridos.");
        }

        double salario = 0;
        try {
            salario = Double.parseDouble(salarioStr);
        } catch (NumberFormatException e) {
            errores.add("El salario debe ser un número válido.");
        }

        LocalDate fechaIngreso = null;
        try {
            fechaIngreso = LocalDate.parse(fechaIngresoStr);
        } catch (DateTimeParseException e) {
            errores.add("La fecha de ingreso debe tener el formato YYYY-MM-DD.");
        }

        if (errores.isEmpty()) {
            // Calcular años de servicio
            LocalDate fechaActual = LocalDate.now();
            Period periodo = Period.between(fechaIngreso, fechaActual);
            int añosServicio = periodo.getYears();

            // Calcular días de vacaciones
            int diasVacaciones = 0;
            if (añosServicio >= 1 && añosServicio <= 3) {
                diasVacaciones = 10;
            } else if (añosServicio > 3 && añosServicio <= 5) {
                diasVacaciones = 15;
            } else if (añosServicio > 5) {
                diasVacaciones = 21;
            }

            // Crear objeto Persona
            Persona persona = new Persona(nombre, apellidos, salario, fechaIngresoStr, diasVacaciones);

            // Enviar persona a la página de resultados
            request.setAttribute("persona", persona);
            request.getRequestDispatcher("vacacionesResultado.jsp").forward(request, response);

        } else {
            // Enviar errores a la página del formulario
            request.setAttribute("errores", errores);
            request.getRequestDispatcher("vacacionesForm.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //  Posiblemente redirigir a la página del formulario si se accede por GET
        response.sendRedirect("vacacionesForm.jsp");
    }
}
