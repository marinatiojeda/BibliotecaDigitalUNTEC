package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static ConexionBD instancia;

    private Connection conexion;

    private String url = System.getenv("DB_URL");
    private String usuarioBD = System.getenv("DB_USER");
    private String claveBD = System.getenv("DB_PASSWORD");

    private ConexionBD() throws SQLException {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            conexion = DriverManager.getConnection(
                url,
                usuarioBD,
                claveBD
            );

            System.out.println("CONEXION EXITOSA A LA BASE DE DATOS!");

        } catch (ClassNotFoundException e) {

            throw new SQLException(
                "Driver de MySQL no encontrado: " + e.getMessage()
            );

        } catch (SQLException e) {

            throw new SQLException(
                "Error al conectar con la base de datos: " + e.getMessage()
            );
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