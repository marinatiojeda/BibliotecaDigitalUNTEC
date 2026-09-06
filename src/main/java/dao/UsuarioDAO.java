package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Usuario;

public class UsuarioDAO {
    private Connection conexion;

    public UsuarioDAO() throws SQLException {
        this.conexion = ConexionBD.getInstancia().getConexion();
    }

    // ✅ VALIDAR con los nombres REALES de tu tabla
    public Usuario validarLogin(String nombre, String clave) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE nombre = ? AND clave = ?";
        PreparedStatement consulta = conexion.prepareStatement(sql);
        consulta.setString(1, nombre);
        consulta.setString(2, clave);
        ResultSet resultado = consulta.executeQuery();

        if (resultado.next()) {
            return new Usuario(
                0,  // ✅ Tu tabla NO tiene columna "id" → ponemos 0
                resultado.getString("nombre"),
                resultado.getString("clave"),
                resultado.getString("rol")  // ✅ Tu columna se llama "rol", NO "tipo"
            );
        }
        return null;
    }
}