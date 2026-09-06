package controlador;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.LibroDAO;
import modelo.Libro;

@WebServlet("/LibroServlet")
public class LibroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        if (sesion == null || sesion.getAttribute("usuario") == null) {
            request.setAttribute("error", "Debes iniciar sesión primero");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        try {
            LibroDAO dao = new LibroDAO();
            List<Libro> listaCompleta;

            // ✅ SIEMPRE calculamos estadísticas
            request.setAttribute("totalLibros", dao.contarTotal());
            request.setAttribute("disponibles", dao.contarDisponibles());
            request.setAttribute("prestados", dao.contarPrestados());

            // ✅ BUSCAR o MOSTRAR TODOS
            String buscar = request.getParameter("buscar");
            if (buscar != null && !buscar.trim().isEmpty()) {
                String palabra = buscar.trim();
                listaCompleta = dao.buscar(palabra);
                request.setAttribute("palabraBuscada", palabra);
            } else {
                listaCompleta = dao.listarTodos();
            }

            // ✅ LÓGICA DE PAGINACIÓN
            int pagina = 1;
            int porPagina = 10; // Mostramos 10 libros por página

            if (request.getParameter("pagina") != null) {
                pagina = Integer.parseInt(request.getParameter("pagina"));
            }

            int totalPaginas = (int) Math.ceil((double) listaCompleta.size() / porPagina);
            int inicio = (pagina - 1) * porPagina;

            List<Libro> listaPagina;
            if (inicio + porPagina > listaCompleta.size()) {
                listaPagina = listaCompleta.subList(inicio, listaCompleta.size());
            } else {
                listaPagina = listaCompleta.subList(inicio, inicio + porPagina);
            }

            request.setAttribute("listaLibros", listaPagina);
            request.setAttribute("paginaActual", pagina);
            request.setAttribute("totalPaginas", totalPaginas);

            request.getRequestDispatcher("listarLibros.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500, "Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    // ✅ Guardar libro nuevo
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        if (sesion == null || sesion.getAttribute("usuario") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");
        String editorial = request.getParameter("editorial");
        int anio = Integer.parseInt(request.getParameter("anio"));

        Libro libro = new Libro(0, titulo, autor, editorial, anio, true);

        try {
            LibroDAO dao = new LibroDAO();
            dao.agregar(libro);
            request.setAttribute("exito", "si");
            request.getRequestDispatcher("agregarLibro.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "No se pudo guardar: " + e.getMessage());
            request.getRequestDispatcher("agregarLibro.jsp").forward(request, response);
        }
    }
}