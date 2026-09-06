package controlador;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.LibroDAO;
import modelo.Libro;

@WebServlet("/CambiarEstadoServlet")
public class CambiarEstadoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        if (sesion == null || sesion.getAttribute("usuario") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        int id = Integer.parseInt(request.getParameter("id"));

        try {
            LibroDAO dao = new LibroDAO();
            Libro libro = dao.buscarPorId(id); // ✅ Busca el libro por ID
            
            if (libro != null) { // ✅ Verifica que exista
                libro.setDisponible(!libro.isDisponible()); // ✅ Cambia el estado
                dao.editar(libro); // ✅ Guarda los cambios
            }
            
            response.sendRedirect("LibroServlet"); // ✅ Vuelve a la lista
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500, "Error: " + e.getMessage());
        }
    }
}
