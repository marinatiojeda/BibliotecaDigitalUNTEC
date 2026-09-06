<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Libros Disponibles</title>

    <style>
        body {
            font-family: Arial;
            padding: 20px;
            background: #f7f7f7;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background: white;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background: #1a73e8;
            color: white;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .salir {
            padding: 8px 16px;
            background: #d93025;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }

        .botones {
            margin-bottom: 20px;
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
        }

        .btn-agregar {
            background: #27ae60;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 4px;
            font-weight: bold;
        }

        .btn-prestamos {
            background: #2980b9;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 4px;
            font-weight: bold;
        }

        .btn-agregar:hover,
        .btn-prestamos:hover {
            opacity: 0.85;
        }
    </style>
</head>

<body>

    <div class="header">

        <h1>Catálogo de Libros — Biblioteca UNTEC</h1>

        <a href="LoginServlet?accion=salir" class="salir">
            Cerrar Sesión
        </a>

    </div>


    <!-- BOTONES PRINCIPALES -->

    <div class="botones">

        <a href="agregarLibro.jsp" class="btn-agregar">
            ➕ Agregar Nuevo Libro
        </a>

        <a href="PrestamoServlet" class="btn-prestamos">
            📚 Gestión de Préstamos
        </a>

    </div>


    <!-- BUSCADOR -->

    <div style="margin: 20px 0; padding: 15px; background: #f8f9fa; border-radius: 8px;">

        <form action="LibroServlet" method="get">

            <label style="font-weight: bold; color: #2c3e50;">
                🔍 Buscar libro por título o autor:
            </label>

            <input
                type="text"
                name="buscar"
                placeholder="Escribe aquí..."
                style="padding: 8px 12px; width: 300px; border: 1px solid #bdc3c7; border-radius: 4px; margin: 0 10px;"
            >

            <button
                type="submit"
                style="background: #3498db; color: white; padding: 8px 20px; border: none; border-radius: 4px; cursor: pointer;"
            >
                Buscar
            </button>

            <c:if test="${not empty palabraBuscada}">

                <a
                    href="LibroServlet"
                    style="margin-left: 10px; color: #e74c3c; text-decoration: none;"
                >
                    ✖ Limpiar
                </a>

            </c:if>

        </form>

    </div>


    <!-- ESTADÍSTICAS -->

    <div style="display: flex; gap: 20px; margin: 20px 0;">

        <div style="flex: 1; background: #eaf4ff; padding: 15px; border-radius: 8px; text-align: center;">

            <h3 style="margin: 0; color: #2c3e50;">
                📚 Total Libros
            </h3>

            <p style="font-size: 28px; font-weight: bold; color: #3498db; margin: 10px 0 0 0;">
                ${totalLibros}
            </p>

        </div>


        <div style="flex: 1; background: #eaffeF; padding: 15px; border-radius: 8px; text-align: center;">

            <h3 style="margin: 0; color: #2c3e50;">
                ✅ Disponibles
            </h3>

            <p style="font-size: 28px; font-weight: bold; color: #27ae60; margin: 10px 0 0 0;">
                ${disponibles}
            </p>

        </div>


        <div style="flex: 1; background: #fff3e0; padding: 15px; border-radius: 8px; text-align: center;">

            <h3 style="margin: 0; color: #2c3e50;">
                📖 Prestados
            </h3>

            <p style="font-size: 28px; font-weight: bold; color: #e67e22; margin: 10px 0 0 0;">
                ${prestados}
            </p>

        </div>

    </div>


    <!-- MENSAJE DE RESULTADOS -->

    <c:if test="${not empty palabraBuscada}">

        <p style="color: #7f8c8d; margin-top: 10px;">

            📄 Resultados para:
            "<strong>${palabraBuscada}</strong>"
            — Total: ${listaLibros.size()} libro(s)

        </p>

    </c:if>


    <!-- USUARIO -->

    <c:if test="${not empty usuario}">

        <p>
            Bienvenido,
            <strong>${usuario}</strong>!
        </p>

    </c:if>


    <!-- TABLA DE LIBROS -->

    <table>

        <tr>

            <th>ID</th>
            <th>Título</th>
            <th>Autor</th>
            <th>Editorial</th>
            <th>Año</th>
            <th>Estado</th>
            <th>Acciones</th>

        </tr>


        <c:forEach var="libro" items="${listaLibros}">

            <tr>

                <td>
                    <c:out value="${libro.id}"/>
                </td>

                <td>
                    <c:out value="${libro.titulo}"/>
                </td>

                <td>
                    <c:out value="${libro.autor}"/>
                </td>

                <td>
                    <c:out value="${libro.editorial}"/>
                </td>

                <td>
                    <c:out value="${libro.anio}"/>
                </td>


                <!-- ESTADO -->

                <td>

                    <c:choose>

                        <c:when test="${libro.disponible}">

                            <a
                                href="CambiarEstadoServlet?id=${libro.id}"
                                title="Clic para marcar como prestado"
                                style="text-decoration: none;"
                            >
                                ✅ Disponible
                            </a>

                        </c:when>


                        <c:otherwise>

                            <a
                                href="CambiarEstadoServlet?id=${libro.id}"
                                title="Clic para marcar como disponible"
                                style="text-decoration: none;"
                            >
                                📖 Prestado
                            </a>

                        </c:otherwise>

                    </c:choose>

                </td>


                <!-- ACCIONES -->

                <td>

                    <a
                        href="EditarLibroServlet?id=${libro.id}"
                        style="color: #f39c12; text-decoration: none; margin-right: 10px;"
                    >
                        ✏️ Editar
                    </a>


                    <a
                        href="EliminarLibroServlet?id=${libro.id}"
                        style="color: #e74c3c; text-decoration: none;"
                        onclick="return confirm('¿Seguro que quieres eliminar este libro?')"
                    >
                        🗑️ Eliminar
                    </a>

                </td>

            </tr>

        </c:forEach>

    </table>


    <!-- PAGINACIÓN -->

    <div style="text-align: center; margin-top: 25px; padding: 15px; background: #f8f9fa; border-radius: 8px;">

        <p style="margin: 0 0 10px 0; font-weight: bold;">

            Página ${paginaActual} de ${totalPaginas}

        </p>


        <!-- ANTERIOR -->

        <c:if test="${paginaActual > 1}">

            <a
                href="LibroServlet?pagina=${paginaActual - 1}"
                style="padding: 8px 15px; margin: 3px; background: #3498db; color: white; text-decoration: none; border-radius: 4px;"
            >
                ⬅ Anterior
            </a>

        </c:if>


        <!-- NÚMEROS -->

        <c:forEach var="i" begin="1" end="${totalPaginas}">

            <c:choose>

                <c:when test="${i == paginaActual}">

                    <span
                        style="padding: 8px 15px; margin: 3px; background: #2c3e50; color: white; border-radius: 4px; font-weight: bold;"
                    >
                        ${i}
                    </span>

                </c:when>


                <c:otherwise>

                    <a
                        href="LibroServlet?pagina=${i}"
                        style="padding: 8px 15px; margin: 3px; background: #ecf0f1; color: #333; text-decoration: none; border-radius: 4px;"
                    >
                        ${i}
                    </a>

                </c:otherwise>

            </c:choose>

        </c:forEach>


        <!-- SIGUIENTE -->

        <c:if test="${paginaActual < totalPaginas}">

            <a
                href="LibroServlet?pagina=${paginaActual + 1}"
                style="padding: 8px 15px; margin: 3px; background: #3498db; color: white; text-decoration: none; border-radius: 4px;"
            >
                Siguiente ➡
            </a>

        </c:if>

    </div>

</body>
</html>