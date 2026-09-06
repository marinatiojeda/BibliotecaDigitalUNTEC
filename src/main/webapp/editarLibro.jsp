<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Libro - Biblioteca UNTEC</title>

    <style>
        body {
            font-family: Arial;
            background: #f0f2f5;
            max-width: 600px;
            margin: 30px auto;
            padding: 20px;
        }

        .formulario {
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            color: #1a73e8;
        }

        .form-group {
            margin: 15px 0;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #34495e;
        }

        input, select {
            width: 100%;
            padding: 10px;
            border: 1px solid #bdc3c7;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 14px;
        }

        button {
            width: 100%;
            padding: 12px;
            background: #f39c12;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            margin-top: 10px;
        }

        button:hover {
            background: #e67e22;
        }

        .volver {
            display: inline-block;
            margin-top: 20px;
            color: #3498db;
            text-decoration: none;
        }

        .error {
            color: red;
            padding: 10px;
            background: #fde8e8;
            border-radius: 4px;
            margin-bottom: 15px;
        }
    </style>
</head>

<body>

<div class="formulario">

    <h2>✏️ Editar Libro</h2>

    <% if (request.getAttribute("error") != null) { %>
        <div class="error">
            ❌ <%= request.getAttribute("error") %>
        </div>
    <% } %>

    <form action="EditarLibroServlet" method="post">

        <input type="hidden" name="id" value="${libro.id}">

        <div class="form-group">
            <label>Título:</label>
            <input type="text"
                   name="titulo"
                   value="${libro.titulo}"
                   required>
        </div>

        <div class="form-group">
            <label>Autor:</label>
            <input type="text"
                   name="autor"
                   value="${libro.autor}"
                   required>
        </div>

        <div class="form-group">
            <label>Editorial:</label>
            <input type="text"
                   name="editorial"
                   value="${libro.editorial}">
        </div>

        <div class="form-group">
            <label>Año de publicación:</label>
            <input type="number"
                   name="anio"
                   value="${libro.anio}"
                   min="1000"
                   max="2100"
                   required>
        </div>

        <div class="form-group">
            <label>Estado:</label>

            <select name="disponible">

                <option value="true"
                    ${libro.disponible ? 'selected' : ''}>
                    Disponible
                </option>

                <option value="false"
                    ${!libro.disponible ? 'selected' : ''}>
                    Prestado
                </option>

            </select>
        </div>

        <button type="submit">
            💾 Guardar Cambios
        </button>

    </form>

    <a href="LibroServlet" class="volver">
        ← Volver al listado de libros
    </a>

</div>

</body>
</html>