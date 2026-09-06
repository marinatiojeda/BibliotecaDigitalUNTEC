<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">

    <title>Préstamos - Biblioteca UNTEC</title>

    <style>

        body {
            font-family: Arial, sans-serif;
            padding: 20px;
            background: #f7f7f7;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        h1 {
            color: #2c3e50;
        }

        .boton {
            display: inline-block;
            padding: 10px 18px;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin-right: 8px;
        }

        .nuevo {
            background: #27ae60;
        }

        .catalogo {
            background: #3498db;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 25px;
            background: white;
        }

        th {
            background: #1a73e8;
            color: white;
            padding: 12px;
            text-align: left;
        }

        td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
        }

        .prestado {
            color: #e67e22;
            font-weight: bold;
        }

        .devuelto {
            color: #27ae60;
            font-weight: bold;
        }

        .devolver {
            background: #27ae60;
            color: white;
            padding: 7px 12px;
            text-decoration: none;
            border-radius: 4px;
        }

        .sin-datos {
            margin-top: 25px;
            padding: 20px;
            background: white;
            text-align: center;
            border-radius: 6px;
        }

        .mensaje {
            margin-top: 20px;
            padding: 15px;
            background: #d4edda;
            color: #155724;
            border-radius: 5px;
        }

        .error {
            margin-top: 20px;
            padding: 15px;
            background: #f8d7da;
            color: #721c24;
            border-radius: 5px;
        }

    </style>

</head>

<body>

    <div class="header">

        <h1>📖 Gestión de Préstamos</h1>

        <div>

            <a href="agregarPrestamo.jsp"
               class="boton nuevo">

                ➕ Nuevo Préstamo

            </a>

            <a href="LibroServlet"
               class="boton catalogo">

                📚 Catálogo

            </a>

        </div>

    </div>


    <!-- MENSAJE DE DEVOLUCIÓN -->

    <c:if test="${param.mensaje == 'devuelto'}">

        <div class="mensaje">

            ✅ El libro fue devuelto correctamente.

        </div>

    </c:if>


    <!-- MENSAJE DE ERROR -->

    <c:if test="${param.mensaje == 'error'}">

        <div class="error">

            ❌ No se pudo realizar la devolución.

        </div>

    </c:if>


    <c:choose>

        <c:when test="${not empty listaPrestamos}">

            <table>

                <tr>

                    <th>ID</th>

                    <th>ID Libro</th>

                    <th>Usuario</th>

                    <th>Fecha Préstamo</th>

                    <th>Fecha Devolución</th>

                    <th>Estado</th>

                    <th>Acciones</th>

                </tr>


                <c:forEach var="prestamo"
                           items="${listaPrestamos}">

                    <tr>

                        <!-- ID -->

                        <td>

                            <c:out value="${prestamo.id}"/>

                        </td>


                        <!-- ID LIBRO -->

                        <td>

                            <c:out value="${prestamo.libroId}"/>

                        </td>


                        <!-- USUARIO -->

                        <td>

                            <c:out value="${prestamo.usuarioNombre}"/>

                        </td>


                        <!-- FECHA PRÉSTAMO -->

                        <td>

                            <c:out value="${prestamo.fechaPrestamo}"/>

                        </td>


                        <!-- FECHA DEVOLUCIÓN -->

                        <td>

                            <c:choose>

                                <c:when test="${not empty prestamo.fechaDevolucion}">

                                    <c:out value="${prestamo.fechaDevolucion}"/>

                                </c:when>

                                <c:otherwise>

                                    -

                                </c:otherwise>

                            </c:choose>

                        </td>


                        <!-- ESTADO -->

                        <td>

                            <c:choose>

                                <c:when test="${prestamo.estado == 'PRESTADO'}">

                                    <span class="prestado">

                                        📖 PRESTADO

                                    </span>

                                </c:when>

                                <c:when test="${prestamo.estado == 'DEVUELTO'}">

                                    <span class="devuelto">

                                        ✅ DEVUELTO

                                    </span>

                                </c:when>

                                <c:otherwise>

                                    <span>

                                        <c:out value="${prestamo.estado}"/>

                                    </span>

                                </c:otherwise>

                            </c:choose>

                        </td>


                        <!-- ACCIONES -->

                        <td>

                            <c:if test="${prestamo.estado == 'PRESTADO'}">

                                <a
                                    href="PrestamoServlet?accion=devolver&id=${prestamo.id}"
                                    class="devolver"
                                    onclick="return confirm('¿Confirmas la devolución de este libro?');">

                                    ↩ Devolver

                                </a>

                            </c:if>


                            <c:if test="${prestamo.estado == 'DEVUELTO'}">

                                <span>

                                    ✔ Finalizado

                                </span>

                            </c:if>

                        </td>

                    </tr>

                </c:forEach>

            </table>

        </c:when>


        <c:otherwise>

            <div class="sin-datos">

                <h3>📭 No hay préstamos registrados</h3>

                <p>
                    Todavía no se ha registrado ningún préstamo.
                </p>

                <a href="agregarPrestamo.jsp"
                   class="boton nuevo">

                    ➕ Registrar primer préstamo

                </a>

            </div>

        </c:otherwise>

    </c:choose>

</body>

</html>
}