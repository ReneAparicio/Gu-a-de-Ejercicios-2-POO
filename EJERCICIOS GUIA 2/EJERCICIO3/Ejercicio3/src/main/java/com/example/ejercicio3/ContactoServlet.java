package com.example.ejercicio3;

//Rafael Alejandro Escorcia Siliézar ES232712

// Importaciones necesarias
import com.example.ejercicio3.Contacto;
import com.example.ejercicio3.ContactoDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ContactoServlet")
public class ContactoServlet extends HttpServlet {

    private ContactoDAO contactoDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            contactoDAO = new ContactoDAO();
        } catch (Exception e) {
            throw new ServletException("Error inicializando ContactoDAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {
            action = "mostrarInicio";
        }

        try {
            switch (action) {
                case "buscar":
                    buscarContactos(request, response); // *** AHORA REDIRIGE A index.jsp ***
                    break;
                case "listar":
                    listarTodosLosContactos(request, response); // Sigue redirigiendo a lista_contactos.jsp
                    break;
                case "editar":
                    mostrarFormularioEdicion(request, response);
                    break;
                case "mostrarInicio":
                default:
                    System.out.println("Mostrando página de inicio (index.jsp)");
                    request.removeAttribute("resultados");
                    request.removeAttribute("busqueda");
                    request.removeAttribute("listaCompleta"); // Asegurarse de limpiar este también
                    request.removeAttribute("huboBusqueda"); // Limpiar indicador de búsqueda
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;
            }
        } catch (NumberFormatException e) {
            System.err.println("Error de formato de número (probablemente ID inválido): " + e.getMessage());
            e.printStackTrace();
            // Guardar error en sesión para mostrarlo después de redirigir a index.jsp
            HttpSession session = request.getSession();
            session.setAttribute("errores", List.of("ID inválido para la operación solicitada."));
            session.removeAttribute("mensaje"); // Limpiar mensaje de éxito si hubo error
            response.sendRedirect("index.jsp"); // Redirigir a la página principal en caso de error GET
        } catch (Exception e) {
            System.err.println("Error inesperado en doGet: " + e.getMessage());
            e.printStackTrace();
            // Guardar error en sesión
            HttpSession session = request.getSession();
            session.setAttribute("errores", List.of("Ocurrió un error inesperado al procesar la solicitud: " + e.getMessage()));
            session.removeAttribute("mensaje");
            response.sendRedirect("index.jsp"); // Redirigir a la página principal
        }
    }

    /**
     * Busca contactos y reenvía a index.jsp para mostrar resultados.
     */
    private void buscarContactos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombreBusqueda = request.getParameter("nombreBusqueda");
        System.out.println("Buscando contactos con nombre: " + nombreBusqueda);

        if (nombreBusqueda == null) {
            nombreBusqueda = "";
        }

        List<Contacto> resultados = contactoDAO.buscarContactosPorNombre(nombreBusqueda.trim());
        System.out.println("Contactos encontrados: " + resultados.size());

        // Pasa los resultados y el término de búsqueda a la petición
        request.setAttribute("busqueda", nombreBusqueda); // Para mostrar qué se buscó
        request.setAttribute("resultados", resultados);   // La lista de contactos encontrados
        request.setAttribute("huboBusqueda", true); // Indicador para que el JSP sepa que se hizo una búsqueda

        // *** CAMBIO: Reenviar a index.jsp ***
        System.out.println("Reenviando a index.jsp para mostrar resultados de búsqueda");
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Obtiene todos los contactos y reenvía a lista_contactos.jsp para mostrarlos.
     * (Esta función no cambia)
     */
    private void listarTodosLosContactos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("Listando todos los contactos...");

        List<Contacto> listaCompleta = contactoDAO.obtenerTodosContactos();
        System.out.println("Total de contactos obtenidos: " + listaCompleta.size());

        request.setAttribute("listaCompleta", listaCompleta);

        // Sigue reenviando a lista_contactos.jsp
        System.out.println("Reenviando a lista_contactos.jsp para mostrar lista completa");
        RequestDispatcher dispatcher = request.getRequestDispatcher("lista_contactos.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Muestra el formulario para editar un contacto existente.
     * (Requiere JSP de edición y método obtenerContactoPorId en DAO)
     */
    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("Mostrando formulario de edición para ID: " + id);
            // Contacto contacto = contactoDAO.obtenerContactoPorId(id); // Descomentar cuando exista en DAO
            Contacto contacto = null; // Temporal

            if (contacto != null) {
                request.setAttribute("contacto", contacto);
                // *** NECESARIO: Crear editar_contacto.jsp ***
                // RequestDispatcher dispatcher = request.getRequestDispatcher("editar_contacto.jsp");
                // dispatcher.forward(request, response);
                System.out.println("Funcionalidad de edición pendiente (DAO y JSP)");
                // Guardar mensaje en sesión y redirigir a index
                HttpSession session = request.getSession();
                session.setAttribute("errores", List.of("Funcionalidad de edición aún no implementada."));
                session.removeAttribute("mensaje");
                response.sendRedirect("index.jsp"); // Volver a index por ahora
            } else {
                System.err.println("No se encontró contacto para editar con ID: " + id);
                // Guardar error en sesión y redirigir a la lista
                HttpSession session = request.getSession();
                session.setAttribute("errores", List.of("No se encontró el contacto con ID " + id + " para editar."));
                session.removeAttribute("mensaje");
                response.sendRedirect("ContactoServlet?action=listar"); // Redirigir a la lista
            }
        } catch (NumberFormatException e) {
            System.err.println("ID inválido para editar: " + request.getParameter("id"));
            // Guardar error en sesión y redirigir a la lista
            HttpSession session = request.getSession();
            session.setAttribute("errores", List.of("ID inválido para editar."));
            session.removeAttribute("mensaje");
            response.sendRedirect("ContactoServlet?action=listar"); // Mostrar lista en caso de error
        }
        // Añadir catch para SQLException si obtenerContactoPorId la lanza y modificar manejo de errores
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        System.out.println("Acción recibida (POST): " + action);

        if (action == null || action.trim().isEmpty()) {
            System.out.println("Acción POST nula o vacía, redirigiendo a index.jsp");
            response.sendRedirect("index.jsp"); // Redirige a la página principal si no hay acción
            return;
        }

        String mensajeExito = null;
        List<String> errores = new ArrayList<>();
        String redirectTarget = "index.jsp"; // Por defecto redirige a index después de POST

        try {
            switch(action) {
                case "agregar":
                    // ... (lógica de agregar como estaba antes) ...
                    String nombre = request.getParameter("nombre");
                    String apellidos = request.getParameter("apellidos");
                    String telefono = request.getParameter("telefono");
                    String email = request.getParameter("email");

                    if (nombre == null || nombre.trim().isEmpty()) errores.add("El nombre es obligatorio.");
                    if (apellidos == null || apellidos.trim().isEmpty()) errores.add("Los apellidos son obligatorio.");
                    if (email == null || email.trim().isEmpty()) errores.add("El correo electrónico es obligatorio.");

                    if (errores.isEmpty()) {
                        Contacto nuevoContacto = new Contacto();
                        nuevoContacto.setNombre(nombre.trim());
                        nuevoContacto.setApellidos(apellidos.trim());
                        nuevoContacto.setTelefono((telefono != null && !telefono.trim().isEmpty()) ? telefono.trim() : null);
                        nuevoContacto.setEmail(email.trim());
                        contactoDAO.agregarContacto(nuevoContacto);
                        mensajeExito = "Contacto '" + nuevoContacto.getNombre() + "' agregado exitosamente.";
                    }
                    // Después de agregar (éxito o error de validación), volver a index.jsp
                    redirectTarget = "index.jsp";
                    break;

                case "eliminar":
                    // ... (lógica de eliminar como estaba antes) ...
                    try {
                        int idParaEliminar = Integer.parseInt(request.getParameter("id"));
                        // boolean eliminado = contactoDAO.eliminarContacto(idParaEliminar); // Descomentar cuando exista
                        boolean eliminado = false; // Temporal
                        if (eliminado) {
                            mensajeExito = "Contacto eliminado exitosamente.";
                        } else {
                            errores.add("No se pudo eliminar el contacto (ID: " + idParaEliminar + ").");
                        }
                    } catch (NumberFormatException e) {
                        errores.add("ID inválido para eliminar.");
                    }
                    // Después de intentar eliminar, redirigir a la lista completa para ver el resultado
                    redirectTarget = "ContactoServlet?action=listar";
                    break;

                case "actualizar":
                    // ... (lógica de actualizar como estaba antes) ...
                    try {
                        int idActualizar = Integer.parseInt(request.getParameter("id"));
                        String nombreAct = request.getParameter("nombre");
                        String telefonoAct = request.getParameter("telefono");
                        String emailAct = request.getParameter("email");

                        if (nombreAct == null || nombreAct.trim().isEmpty()) errores.add("El nombre es obligatorio.");
                        if (emailAct == null || emailAct.trim().isEmpty()) errores.add("El correo electrónico es obligatorio.");

                        if(errores.isEmpty()){
                            Contacto contactoActualizado = new Contacto();
                            contactoActualizado.setId(idActualizar);
                            contactoActualizado.setNombre(nombreAct.trim());
                            contactoActualizado.setTelefono((telefonoAct != null && !telefonoAct.trim().isEmpty()) ? telefonoAct.trim() : null);
                            contactoActualizado.setEmail(emailAct.trim());
                            // boolean actualizado = contactoDAO.actualizarContacto(contactoActualizado); // Descomentar cuando exista
                            boolean actualizado = false; // Temporal
                            if(actualizado){
                                mensajeExito = "Contacto '" + contactoActualizado.getNombre() + "' actualizado exitosamente.";
                            } else {
                                errores.add("No se pudo actualizar el contacto (ID: " + idActualizar + ").");
                            }
                        }
                    } catch (NumberFormatException e) {
                        errores.add("ID inválido para actualizar.");
                    }
                    // Después de intentar actualizar, redirigir a la lista completa
                    redirectTarget = "ContactoServlet?action=listar";
                    break;

                default:
                    errores.add("Acción POST desconocida: " + action);
                    redirectTarget = "index.jsp"; // Volver a index si la acción es desconocida
                    break;
            }

            // Guardar mensajes en sesión antes de redirigir
            HttpSession session = request.getSession();
            if (!errores.isEmpty()) {
                session.setAttribute("errores", errores);
                session.removeAttribute("mensaje");
            }
            if (mensajeExito != null) {
                session.setAttribute("mensaje", mensajeExito);
                session.removeAttribute("errores");
            }

            // Redirigir al destino determinado (index.jsp o lista)
            System.out.println("Redirigiendo a " + redirectTarget + " después de POST");
            response.sendRedirect(redirectTarget);


        } catch (Exception e) { // Captura general para errores inesperados en POST
            System.err.println("Error inesperado en doPost: " + e.getMessage());
            e.printStackTrace();
            // Guardar error en sesión y redirigir a index.jsp en caso de error grave
            HttpSession session = request.getSession();
            session.setAttribute("errores", List.of("Ocurrió un error inesperado al procesar la operación: " + e.getMessage()));
            session.removeAttribute("mensaje");
            response.sendRedirect("index.jsp"); // Redirigir a index en caso de error general
        }
    }

    @Override
    public void destroy() {
        System.out.println("Servlet ContactoServlet destruido.");
        super.destroy();
    }
}
