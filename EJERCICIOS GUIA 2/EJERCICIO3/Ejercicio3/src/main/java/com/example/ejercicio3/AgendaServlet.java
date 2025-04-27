package com.example.ejercicio3;
// Rafael Alejandro Escorcia Siliézar ES232712

// src/main/java/com/example/agenda/controller/AgendaServlet.java


import com.example.ejercicio1.ContactoDAO;
import com.example.ejercicio1.Contacto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

@WebServlet("/agenda")
public class AgendaServlet extends HttpServlet {
    private ContactoDAO contactoDAO;

    @Override
    public void init() throws ServletException {
        contactoDAO = new ContactoDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        System.out.println("doPost() llamado");
        System.out.println("Parámetro action: " + action);
        System.out.println("Parámetro nombre: " + request.getParameter("nombre"));
        System.out.println("Parámetro apellidos: " + request.getParameter("apellidos"));
        System.out.println("Parámetro telefono: " + request.getParameter("telefono"));
        System.out.println("Parámetro email: " + request.getParameter("email"));

        if ("agregar".equals(action)) {
            String nombre = request.getParameter("nombre");
            String apellidos = request.getParameter("apellidos");
            String telefono = request.getParameter("telefono");
            String email = request.getParameter("email");

            List<String> errores = validarContacto(nombre, apellidos, telefono, email);

            if (errores.isEmpty()) {
                Contacto nuevoContacto = new Contacto(nombre, apellidos, telefono, email);

                System.out.println("Datos del nuevoContacto antes de guardar:");
                System.out.println("Nombre: " + nuevoContacto.getNombre());
                System.out.println("Apellidos: " + nuevoContacto.getApellidos());
                System.out.println("Teléfono: " + nuevoContacto.getTelefono());
                System.out.println("Email: " + nuevoContacto.getEmail());

                System.out.println("Llamando a contactoDAO.agregarContacto()");
                contactoDAO.agregarContacto(nuevoContacto);

                request.setAttribute("mensaje", "Contacto agregado exitosamente.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else {
                System.out.println("Errores de validación:");
                for (String error : errores) {
                    System.out.println("- " + error);
                }
                request.setAttribute("errores", errores);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } else if ("buscar".equals(action)) {
            String nombreBusqueda = request.getParameter("nombreBusqueda");
            if (nombreBusqueda != null && !nombreBusqueda.trim().isEmpty()) {
                List<Contacto> resultados = contactoDAO.buscarContactosPorNombre(nombreBusqueda);
                request.setAttribute("resultados", resultados);
                request.setAttribute("busqueda", nombreBusqueda);
            } else {
                request.setAttribute("mensaje", "Por favor, ingrese un nombre para buscar.");
            }
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("listar".equals(action)) {
            List<Contacto> contactos = contactoDAO.obtenerTodosContactos();
            request.setAttribute("contactos", contactos);
            request.getRequestDispatcher("lista_contactos.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    private List<String> validarContacto(String nombre, String apellidos, String telefono, String email) {
        List<String> errores = new ArrayList<>();
        if (nombre == null || nombre.trim().isEmpty()) {
            errores.add("El nombre es obligatorio.");
        }
        if (apellidos == null || apellidos.trim().isEmpty()) {
            errores.add("Los apellidos son obligatorios.");
        }
        if (telefono != null && !telefono.trim().isEmpty() && !telefono.matches("\\d+")) {
            errores.add("El teléfono debe contener solo números.");
        }
        if (email == null || email.trim().isEmpty()) {
            errores.add("El correo electrónico es obligatorio.");
        } else if (!email.matches("[^@]+@[^@]+\\.[^@]+")) {
            errores.add("El correo electrónico no tiene un formato válido.");
        }
        return errores;
    }
}