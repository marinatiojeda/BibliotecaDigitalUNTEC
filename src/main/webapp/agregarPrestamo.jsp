<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">

    <title>Registrar Préstamo - Biblioteca UNTEC</title>

    <style>

        body {
            font-family: Arial;
            max-width: 600px;
            margin: 30px auto;
            padding: 20px;
            background: #f7f7f7;
        }

        .contenedor {
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        h2 {
            color: #2c3e50;
            text-align: center;
        }

        .form-group {
            margin: 20px 0;
        }

        label {
            display: block;
            margin-bottom: 7px;
            font-weight: bold;
        }

        input {
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
            border: 1px solid #bdc3c7;
            border-radius: 4px;
        }

        button {
            width: 100%;
            padding: 12px;
            background: #3498db;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }

        button:hover {
            background: #2980b9;
        }

        .exito {
            color: green;
            background: #e8f8e8;
            padding: 10px;
            margin-bottom: 15px;
        }

        .error {
            color: red;
            background: #fde8e8;
            padding: 10px;
            margin-bottom: 15px;
        }

        .volver {
            display: inline-block;
            margin-top: 20px;
            color: #3498db;
            text-decoration: none;
        }

    </style>

</head>

<body>

<div class="contenedor">

    <h2>📚 Registrar Préstamo</h2>

    <% if (request.getAttribute("exito") != null) { %>

        <div class="exito">
            ✅ <%= request.getAttribute("exito") %>
        </div>

    <% } %>


    <% if (request.getAttribute("error") != null) { %>

        <div class="error">
            ❌ <%= request.getAttribute("error") %>
        </div>

    <% } %>


    <!-- FORMULARIO -->

    <form action="PrestamoServlet" method="post">

        <div class="form-group">

            <label for="libroId">
                ID del libro:
            </label>

            <input
                type="number"
                id="libroId"
                name="libroId"
                min="1"
                required>

        </div>


        <div class="form-group">

            <label for="usuarioNombre">
                Nombre del usuario:
            </label>

            <input
                type="text"
                id="usuarioNombre"
                name="usuarioNombre"
                required>

        </div>


        <button type="submit">
            📖 Registrar Préstamo
        </button>

    </form>


    <a href="${pageContext.request.contextPath}/PrestamoServlet"
       class="volver">
        ← Ver préstamos
    </a>

    <br>

    <a href="${pageContext.request.contextPath}/LibroServlet"
       class="volver">
        ← Volver al catálogo
    </a>

</div>

</body>
</html>