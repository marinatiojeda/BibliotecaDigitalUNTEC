package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Prestamo;

public class PrestamoDAO {

    private Connection conexion;

    public PrestamoDAO() throws SQLException {
        this.conexion = ConexionBD.getInstancia().getConexion();
    }

    // =====================================================
    // LISTAR TODOS LOS PRÉSTAMOS
    // =====================================================
    public List<Prestamo> listarTodos() throws SQLException {

        List<Prestamo> lista = new ArrayList<>();

        String sql = "SELECT * FROM prestamos ORDER BY id DESC";

        try (PreparedStatement consulta = conexion.prepareStatement(sql);
             ResultSet resultado = consulta.executeQuery()) {

            while (resultado.next()) {

                Prestamo prestamo = new Prestamo(
                    resultado.getInt("id"),
                    resultado.getInt("libro_id"),
                    resultado.getString("usuario_nombre"),
                    resultado.getDate("fecha_prestamo"),
                    resultado.getDate("fecha_devolucion"),
                    resultado.getString("estado")
                );

                lista.add(prestamo);
            }
        }

        return lista;
    }

    // =====================================================
    // BUSCAR PRÉSTAMO POR ID
    // =====================================================
    public Prestamo buscarPorId(int id) throws SQLException {

        String sql = "SELECT * FROM prestamos WHERE id = ?";

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, id);

            try (ResultSet resultado = consulta.executeQuery()) {

                if (resultado.next()) {

                    return new Prestamo(
                        resultado.getInt("id"),
                        resultado.getInt("libro_id"),
                        resultado.getString("usuario_nombre"),
                        resultado.getDate("fecha_prestamo"),
                        resultado.getDate("fecha_devolucion"),
                        resultado.getString("estado")
                    );
                }
            }
        }

        return null;
    }

    // =====================================================
    // AGREGAR PRÉSTAMO
    // =====================================================
    public boolean agregar(Prestamo prestamo) throws SQLException {

        String sql = "INSERT INTO prestamos "
                   + "(libro_id, usuario_nombre, fecha_prestamo, "
                   + "fecha_devolucion, estado) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, prestamo.getLibroId());
            consulta.setString(2, prestamo.getUsuarioNombre());
            consulta.setDate(3, prestamo.getFechaPrestamo());
            consulta.setDate(4, prestamo.getFechaDevolucion());
            consulta.setString(5, prestamo.getEstado());

            int filas = consulta.executeUpdate();

            System.out.println("FILAS INSERTADAS: " + filas);

            return filas > 0;
        }
    }

    // =====================================================
    // EDITAR PRÉSTAMO
    // =====================================================
    public boolean editar(Prestamo prestamo) throws SQLException {

        String sql = "UPDATE prestamos SET "
                   + "libro_id=?, "
                   + "usuario_nombre=?, "
                   + "fecha_prestamo=?, "
                   + "fecha_devolucion=?, "
                   + "estado=? "
                   + "WHERE id=?";

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, prestamo.getLibroId());
            consulta.setString(2, prestamo.getUsuarioNombre());
            consulta.setDate(3, prestamo.getFechaPrestamo());
            consulta.setDate(4, prestamo.getFechaDevolucion());
            consulta.setString(5, prestamo.getEstado());
            consulta.setInt(6, prestamo.getId());

            int filas = consulta.executeUpdate();

            System.out.println("FILAS EDITADAS: " + filas);

            return filas > 0;
        }
    }

    // =====================================================
    // ELIMINAR PRÉSTAMO
    // =====================================================
    public boolean eliminar(int id) throws SQLException {

        String sql = "DELETE FROM prestamos WHERE id = ?";

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, id);

            int filas = consulta.executeUpdate();

            System.out.println("FILAS ELIMINADAS: " + filas);

            return filas > 0;
        }
    }

    // =====================================================
    // DEVOLVER PRÉSTAMO
    // =====================================================
    public boolean devolver(int id) throws SQLException {

        String sql = "UPDATE prestamos "
                   + "SET fecha_devolucion = CURRENT_DATE, "
                   + "estado = 'DEVUELTO' "
                   + "WHERE id = ? "
                   + "AND estado = 'PRESTADO'";

        System.out.println("=================================");
        System.out.println("INTENTANDO DEVOLVER PRÉSTAMO");
        System.out.println("ID RECIBIDO: " + id);

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, id);

            int filas = consulta.executeUpdate();

            System.out.println("FILAS ACTUALIZADAS: " + filas);

            if (filas > 0) {
                System.out.println("DEVOLUCIÓN REALIZADA CORRECTAMENTE");
                return true;
            } else {
                System.out.println("NO SE ACTUALIZÓ NINGÚN PRÉSTAMO");
                return false;
            }
        }
    }
}