// Rafael Alejandro Escorcia Siliézar ES232712

package com.example.ejercicio3;

// Importaciones necesarias para JDBC y manejo de listas
import com.example.ejercicio3.Contacto; // Asegúrate que la clase Contacto esté en el mismo paquete o importa correctamente
import java.sql.*; // Para clases JDBC como Connection, PreparedStatement, ResultSet, SQLException
import java.util.ArrayList; // Para usar la implementación ArrayList de List
import java.util.List; // Para usar la interfaz List

/**
 * ContactoDAO (Data Access Object)
 * Clase responsable de manejar las operaciones de base de datos (CRUD)
 * para la entidad Contacto.
 */
public class ContactoDAO {
    // Constantes para la configuración de la conexión JDBC
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/agenda_contactos_jsp?serverTimezone=UTC"; // URL de la BD
    private static final String JDBC_USER = "root"; // Usuario de la BD
    private static final String JDBC_PASSWORD = ""; // Contraseña de la BD (dejar vacía si no tiene)

    // Constantes para las sentencias SQL (usar PreparedStatement previene inyección SQL)
    private static final String INSERT_CONTACTO = "INSERT INTO contactos (nombre, apellidos, telefono, email) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_CONTACTOS = "SELECT id, nombre, apellidos, telefono, email FROM contactos";
    private static final String SELECT_CONTACTO_POR_NOMBRE = "SELECT id, nombre, apellidos, telefono, email FROM contactos WHERE apellidos LIKE ?";


    /**
     * Establece y devuelve una conexión con la base de datos.
     * Carga el driver JDBC de MySQL.
     * @return Objeto Connection listo para usar.
     * @throws SQLException Si ocurre un error al cargar el driver o al conectar.
     */
    private Connection getConnection() throws SQLException {
        try {
            // Carga explícita del driver JDBC (necesario en entornos más antiguos o específicos)
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver de MySQL cargado correctamente."); // Mensaje de depuración
        } catch (ClassNotFoundException e) {
            // Error si el JAR del driver no está en el classpath
            System.err.println("Error al cargar el driver de MySQL:");
            e.printStackTrace();
            // Relanza como SQLException para que los métodos que llaman sepan que hubo un error de BD
            throw new SQLException("Error al cargar el driver de MySQL", e);
        }
        // Establece la conexión usando las constantes definidas
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    /**
     * Inserta un nuevo contacto en la base de datos.
     * @param contacto El objeto Contacto con la información a guardar.
     */
    public void agregarContacto(Contacto contacto) {
        // Usa try-with-resources para asegurar que Connection y PreparedStatement se cierren automáticamente
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CONTACTO)) {

            // Mensajes de depuración (útiles durante el desarrollo)
            System.out.println("agregarContacto() llamado");
            System.out.println("SQL a ejecutar: " + INSERT_CONTACTO);
            System.out.println("Nombre: " + contacto.getNombre());
            System.out.println("Apellidos: " + contacto.getApellidos());
            System.out.println("Teléfono: " + contacto.getTelefono());
            System.out.println("Email: " + contacto.getEmail());

            // Asigna los valores del objeto Contacto a los parámetros (?) de la sentencia SQL
            preparedStatement.setString(1, contacto.getNombre());
            preparedStatement.setString(2, contacto.getApellidos());
            preparedStatement.setString(3, contacto.getTelefono());
            preparedStatement.setString(4, contacto.getEmail());

            // Ejecuta la sentencia INSERT
            preparedStatement.executeUpdate(); // executeUpdate para INSERT, UPDATE, DELETE

        } catch (SQLException e) {
            // Captura y maneja (imprime) cualquier error SQL que ocurra
            System.err.println("Error SQL al agregar contacto:");
            e.printStackTrace();

        }
    }

    /**
     * Recupera todos los contactos de la base de datos.
     * @return Una lista (List) de objetos Contacto. La lista estará vacía si no hay contactos.
     */
    public List<Contacto> obtenerTodosContactos() {
        List<Contacto> contactos = new ArrayList<>(); // Inicializa la lista de resultados
        // Usa try-with-resources
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CONTACTOS);
             ResultSet resultSet = preparedStatement.executeQuery()) { // executeQuery para SELECT

            // Itera sobre cada fila devuelta por la consulta
            while (resultSet.next()) {
                Contacto contacto = new Contacto(); // Crea un nuevo objeto Contacto por cada fila
                // Extrae los datos de cada columna usando el nombre de la columna
                contacto.setId(resultSet.getInt("id"));
                contacto.setNombre(resultSet.getString("nombre"));
                contacto.setApellidos(resultSet.getString("apellidos"));
                contacto.setTelefono(resultSet.getString("telefono"));
                contacto.setEmail(resultSet.getString("email"));
                // Añade el objeto Contacto poblado a la lista
                contactos.add(contacto);
            }
        } catch (SQLException e) {
            // Manejo de errores SQL
            System.err.println("Error SQL al obtener todos los contactos:");
            e.printStackTrace();
        }
        // Devuelve la lista de contactos (puede estar vacía)
        return contactos;
    }

    /**
     * Busca contactos cuyo nombre contenga el término de búsqueda proporcionado (insensible a mayúsculas/minúsculas dependiendo de la configuración de la BD).
     * @param nombre El término de búsqueda para el nombre.
     * @return Una lista (List) de objetos Contacto que coinciden con la búsqueda. Lista vacía si no hay coincidencias.
     */
    public List<Contacto> buscarContactosPorNombre(String nombre) {
        List<Contacto> contactos = new ArrayList<>(); // Inicializa la lista
        // Usa try-with-resources
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CONTACTO_POR_NOMBRE)) {

            // Establece el parámetro para la cláusula LIKE, añadiendo comodines (%)
            preparedStatement.setString(1, "%" + nombre + "%");

            // Usa otro try-with-resources anidado para el ResultSet
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Itera sobre los resultados
                while (resultSet.next()) {
                    Contacto contacto = new Contacto(); // Crea objeto
                    // Extrae datos y los asigna al objeto
                    contacto.setId(resultSet.getInt("id"));
                    contacto.setNombre(resultSet.getString("nombre"));
                    contacto.setApellidos(resultSet.getString("apellidos"));
                    contacto.setTelefono(resultSet.getString("telefono"));
                    contacto.setEmail(resultSet.getString("email"));
                    // Añade a la lista
                    contactos.add(contacto);
                }
            }
        } catch (SQLException e) {
            // Manejo de errores SQL
            System.err.println("Error SQL al buscar contactos por nombre:");
            e.printStackTrace();
        }
        // Devuelve la lista de resultados
        return contactos;
    }


} // Fin de la clase ContactoDAO
