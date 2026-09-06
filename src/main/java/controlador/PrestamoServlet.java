package controlador;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.PrestamoDAO;
import modelo.Prestamo;

@WebServlet("/PrestamoServlet")
public class PrestamoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
        System.out.println("******** PRESTAMOSERVLET CARGADO ********");
    }

    // ==========================================================
    // GET
    // LISTAR PRÉSTAMOS / DEVOLVER LIBRO
    // ==========================================================

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);

        // Comprobar sesión
        if (sesion == null || sesion.getAttribute("usuario") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String accion = request.getParameter("accion");

        System.out.println("ACCION RECIBIDA: " + accion);

        // ======================================================
        // DEVOLVER LIBRO
        // ======================================================

        if ("devolver".equals(accion)) {

            String idTexto = request.getParameter("id");

            System.out.println("ID RECIBIDO: " + idTexto);

            if (idTexto == null || idTexto.isEmpty()) {

                System.out.println("❌ NO SE RECIBIO EL ID");

                response.sendRedirect("PrestamoServlet?mensaje=error");
                return;
            }

            try {

                int id = Integer.parseInt(idTexto);

                PrestamoDAO prestamoDAO = new PrestamoDAO();

                boolean devuelto = prestamoDAO.devolver(id);

                if (devuelto) {

                    System.out.println(
                        "✅ LIBRO DEVUELTO CORRECTAMENTE. ID: " + id
                    );

                    response.sendRedirect(
                        "PrestamoServlet?mensaje=devuelto"
                    );

                } else {

                    System.out.println(
                        "❌ NO SE ACTUALIZO EL PRESTAMO. ID: " + id
                    );

                    response.sendRedirect(
                        "PrestamoServlet?mensaje=error"
                    );
                }

                return;

            } catch (NumberFormatException e) {

                System.out.println("❌ EL ID NO ES NUMERICO");
                e.printStackTrace();

                response.sendRedirect(
                    "PrestamoServlet?mensaje=error"
                );

                return;

            } catch (SQLException e) {

                System.out.println(
                    "❌ ERROR SQL AL DEVOLVER EL LIBRO"
                );

                e.printStackTrace();

                response.sendRedirect(
                    "PrestamoServlet?mensaje=error"
                );

                return;
            }
        }

        // ======================================================
        // LISTAR TODOS LOS PRÉSTAMOS
        // ======================================================

        try {

            PrestamoDAO prestamoDAO = new PrestamoDAO();

            List<Prestamo> listaPrestamos =
                    prestamoDAO.listarTodos();

            request.setAttribute(
                "listaPrestamos",
                listaPrestamos
            );

            // Mensajes
            String mensaje = request.getParameter("mensaje");

            if ("devuelto".equals(mensaje)) {

                request.setAttribute(
                    "exito",
                    "El libro fue devuelto correctamente."
                );

            } else if ("error".equals(mensaje)) {

                request.setAttribute(
                    "error",
                    "No se pudo realizar la operación."
                );
            }

            request.getRequestDispatcher(
                "ListarPrestamos.jsp"
            ).forward(request, response);

        } catch (SQLException e) {

            e.printStackTrace();

            response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Error con la base de datos: " + e.getMessage()
            );
        }
    }

    // ==========================================================
    // POST
    // REGISTRAR PRÉSTAMO
    // ==========================================================

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);

        // Comprobar sesión
        if (sesion == null || sesion.getAttribute("usuario") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        try {

            // Obtener datos del formulario
            String libroIdTexto = request.getParameter("libroId");

            if (libroIdTexto == null || libroIdTexto.isEmpty()) {

                request.setAttribute(
                    "error",
                    "Debes ingresar el ID del libro."
                );

                request.getRequestDispatcher(
                    "agregarPrestamo.jsp"
                ).forward(request, response);

                return;
            }

            int libroId = Integer.parseInt(libroIdTexto);

            // Usuario de la sesión
            String usuarioNombre =
                    String.valueOf(sesion.getAttribute("usuario"));

            // Fecha actual
            Date fechaPrestamo =
                    new Date(System.currentTimeMillis());

            // Mientras está prestado no tiene fecha de devolución
            Date fechaDevolucion = null;

            // Estado inicial
            String estado = "PRESTADO";

            // Crear objeto préstamo
            Prestamo prestamo = new Prestamo(
                0,
                libroId,
                usuarioNombre,
                fechaPrestamo,
                fechaDevolucion,
                estado
            );

            // Guardar en BD
            PrestamoDAO prestamoDAO = new PrestamoDAO();

            boolean agregado =
                    prestamoDAO.agregar(prestamo);

            if (agregado) {

                System.out.println(
                    "✅ PRESTAMO REGISTRADO CORRECTAMENTE. LIBRO ID: "
                    + libroId
                );

                response.sendRedirect(
                    "PrestamoServlet?mensaje=agregado"
                );

            } else {

                System.out.println(
                    "❌ NO SE PUDO REGISTRAR EL PRESTAMO"
                );

                request.setAttribute(
                    "error",
                    "No se pudo registrar el préstamo."
                );

                request.getRequestDispatcher(
                    "agregarPrestamo.jsp"
                ).forward(request, response);
            }

        } catch (NumberFormatException e) {

            System.out.println(
                "❌ EL ID DEL LIBRO NO ES NUMERICO"
            );

            request.setAttribute(
                "error",
                "El ID del libro debe ser un número."
            );

            request.getRequestDispatcher(
                "agregarPrestamo.jsp"
            ).forward(request, response);

        } catch (SQLException e) {

            System.out.println(
                "❌ ERROR SQL AL REGISTRAR EL PRESTAMO"
            );

            e.printStackTrace();

            request.setAttribute(
                "error",
                "Error con la base de datos: "
                + e.getMessage()
            );

            request.getRequestDispatcher(
                "agregarPrestamo.jsp"
            ).forward(request, response);
        }
    }
}