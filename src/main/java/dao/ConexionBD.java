package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static ConexionBD instancia;
    private Connection conexion;

    // ✅ URL MEJORADA — soluciona el 90% de los errores
    private String url = "jdbc:mysql://localhost:3306/biblioteca_untec?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private String usuarioBD = "root";
    private String claveBD = "220786"; 

    private ConexionBD() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, usuarioBD, claveBD);
            System.out.println("✅ ¡CONEXIÓN EXITOSA A LA BASE DE DATOS!");
        } catch (ClassNotFoundException e) {
            throw new SQLException("❌ Driver de MySQL no encontrado: " + e.getMessage());
        }
    }

    public static ConexionBD getInstancia() throws SQLException {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void cerrarConexion() throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
        }
    }
}