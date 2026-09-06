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

@WebServlet("/EliminarLibroServlet")
public class EliminarLibroServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar sesión
        HttpSession sesion = request.getSession(false);

        if (sesion == null || sesion.getAttribute("usuario") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        try {

            int id = Integer.parseInt(request.getParameter("id"));

            LibroDAO dao = new LibroDAO();

            boolean eliminado = dao.eliminar(id);

            if (eliminado) {
                System.out.println("Libro eliminado correctamente. ID: " + id);
            }

            response.sendRedirect("LibroServlet");

        } catch (NumberFormatException e) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "ID de libro no válido."
            );

        } catch (SQLException e) {

            e.printStackTrace();

            response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "No se pudo eliminar el libro: " + e.getMessage()
            );
        }
    }
}