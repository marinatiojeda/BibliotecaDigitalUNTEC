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

@WebServlet("/EditarLibroServlet")
public class EditarLibroServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);

        if (sesion == null || sesion.getAttribute("usuario") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            LibroDAO dao = new LibroDAO();
            Libro libro = dao.buscarPorId(id);

            if (libro == null) {
                response.sendError(404, "El libro no existe.");
                return;
            }

            request.setAttribute("libro", libro);

            request.getRequestDispatcher("editarLibro.jsp")
                   .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(400, "ID de libro no válido.");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500, "Error con la base de datos: " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);

        if (sesion == null || sesion.getAttribute("usuario") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String titulo = request.getParameter("titulo");
            String autor = request.getParameter("autor");
            String editorial = request.getParameter("editorial");
            int anio = Integer.parseInt(request.getParameter("anio"));
            boolean disponible = Boolean.parseBoolean(
                    request.getParameter("disponible")
            );

            Libro libro = new Libro(
                    id,
                    titulo,
                    autor,
                    editorial,
                    anio,
                    disponible
            );

            LibroDAO dao = new LibroDAO();
            dao.editar(libro);

            response.sendRedirect("LibroServlet");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Los datos numéricos no son válidos.");
            request.getRequestDispatcher("editarLibro.jsp")
                   .forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute(
                    "error",
                    "No se pudo actualizar el libro: " + e.getMessage()
            );

            request.getRequestDispatcher("editarLibro.jsp")
                   .forward(request, response);
        }
    }
}
