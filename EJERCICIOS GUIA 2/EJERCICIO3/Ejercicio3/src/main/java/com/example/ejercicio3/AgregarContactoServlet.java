package com.example.ejercicio3;

// Rafael Alejandro Escorcia Siliézar ES232712

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/agregarContacto")
public class AgregarContactoServlet extends HttpServlet {
    private ContactoDAO contactoDAO;

    @Override
    public void init() throws ServletException {
        contactoDAO = new ContactoDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String telefono = request.getParameter("telefono");
        String email = request.getParameter("email");

        List<String> errores = new ArrayList<>();
        if (nombre == null || nombre.trim().isEmpty()) {
            errores.add("El nombre es obligatorio.");
        }
        if (apellidos == null || nombre.trim().isEmpty()) {
            errores.add("Los Apellidos es obligatorio.");
        }
        if (telefono != null && !telefono.trim().isEmpty() && !telefono.matches("\\d+")) {
            errores.add("El teléfono debe contener solo números.");
        }
        if (email == null || email.trim().isEmpty()) {
            errores.add("El correo electrónico es obligatorio.");
        } else if (!email.matches("[^@]+@[^@]+\\.[^@]+")) {
            errores.add("El correo electrónico no tiene un formato válido.");
        }

        if (errores.isEmpty()) {
            Contacto nuevoContacto = new Contacto(nombre, apellidos, telefono, email);
            contactoDAO.agregarContacto(nuevoContacto);

            // Pasar los datos a la página de confirmación
            request.setAttribute("nombre", nombre);
            request.setAttribute("apellidos", apellidos);
            request.setAttribute("telefono", telefono);
            request.setAttribute("email", email);

            request.getRequestDispatcher("confirmacion.jsp").forward(request, response);
        } else {
            request.setAttribute("errores", errores);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}