package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Libro;

public class LibroDAO {
    private Connection conexion;

    public LibroDAO() throws SQLException {
        this.conexion = ConexionBD.getInstancia().getConexion();
    }

    // ✅ LISTAR TODOS los libros
    public List<Libro> listarTodos() throws SQLException {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT * FROM libros";
        PreparedStatement consulta = conexion.prepareStatement(sql);
        ResultSet resultado = consulta.executeQuery();

        while (resultado.next()) {
            Libro libro = new Libro(
                resultado.getInt("id"),
                resultado.getString("titulo"),
                resultado.getString("autor"),
                resultado.getString("editorial"),
                resultado.getInt("anio"),
                resultado.getBoolean("disponible")
            );
            lista.add(libro);
        }
        return lista;
    }

    // ✅ AGREGAR un libro
    public boolean agregar(Libro libro) throws SQLException {
        String sql = "INSERT INTO libros (titulo, autor, editorial, anio, disponible) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement consulta = conexion.prepareStatement(sql);
        consulta.setString(1, libro.getTitulo());
        consulta.setString(2, libro.getAutor());
        consulta.setString(3, libro.getEditorial());
        consulta.setInt(4, libro.getAnio());
        consulta.setBoolean(5, libro.isDisponible());
        return consulta.executeUpdate() > 0;
    }

    // ✅ BUSCAR por título o autor
    public List<Libro> buscar(String palabraClave) throws SQLException {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT * FROM libros WHERE titulo LIKE ? OR autor LIKE ?";
        PreparedStatement consulta = conexion.prepareStatement(sql);
        String busqueda = "%" + palabraClave + "%";
        consulta.setString(1, busqueda);
        consulta.setString(2, busqueda);
        ResultSet resultado = consulta.executeQuery();

        while (resultado.next()) {
            lista.add(new Libro(
                resultado.getInt("id"),
                resultado.getString("titulo"),
                resultado.getString("autor"),
                resultado.getString("editorial"),
                resultado.getInt("anio"),
                resultado.getBoolean("disponible")
            ));
        }
        return lista;
    }

    // ✅ BUSCAR por ID
    public Libro buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM libros WHERE id = ?";
        PreparedStatement consulta = conexion.prepareStatement(sql);
        consulta.setInt(1, id);
        ResultSet resultado = consulta.executeQuery();

        if (resultado.next()) {
            return new Libro(
                resultado.getInt("id"),
                resultado.getString("titulo"),
                resultado.getString("autor"),
                resultado.getString("editorial"),
                resultado.getInt("anio"),
                resultado.getBoolean("disponible")
            );
        }
        return null;
    }

    // ✅ EDITAR un libro (UNA SOLA COPIA)
    public boolean editar(Libro libro) throws SQLException {
        String sql = "UPDATE libros SET titulo=?, autor=?, editorial=?, anio=?, disponible=? WHERE id=?";
        PreparedStatement consulta = conexion.prepareStatement(sql);
        consulta.setString(1, libro.getTitulo());
        consulta.setString(2, libro.getAutor());
        consulta.setString(3, libro.getEditorial());
        consulta.setInt(4, libro.getAnio());
        consulta.setBoolean(5, libro.isDisponible());
        consulta.setInt(6, libro.getId());
        return consulta.executeUpdate() > 0;
    }

    // ✅ ELIMINAR un libro
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM libros WHERE id = ?";
        PreparedStatement consulta = conexion.prepareStatement(sql);
        consulta.setInt(1, id);
        return consulta.executeUpdate() > 0;
    }
    // ✅ CONTAR: Total, Disponibles y Prestados
    public int contarTotal() throws SQLException {
        String sql = "SELECT COUNT(*) FROM libros";
        PreparedStatement consulta = conexion.prepareStatement(sql);
        ResultSet resultado = consulta.executeQuery();
        if (resultado.next()) {
            return resultado.getInt(1);
        }
        return 0;
    }

    public int contarDisponibles() throws SQLException {
        String sql = "SELECT COUNT(*) FROM libros WHERE disponible = true";
        PreparedStatement consulta = conexion.prepareStatement(sql);
        ResultSet resultado = consulta.executeQuery();
        if (resultado.next()) {
            return resultado.getInt(1);
        }
        return 0;
    }

    public int contarPrestados() throws SQLException {
        String sql = "SELECT COUNT(*) FROM libros WHERE disponible = false";
        PreparedStatement consulta = conexion.prepareStatement(sql);
        ResultSet resultado = consulta.executeQuery();
        if (resultado.next()) {
            return resultado.getInt(1);
        }
        return 0;
    }
    
}