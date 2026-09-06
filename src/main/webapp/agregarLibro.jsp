<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Agregar Libro - Biblioteca UNTEC</title>
    <style>
        body { font-family: Arial; max-width: 600px; margin: 30px auto; padding: 20px; }
        h2 { color: #2c3e50; text-align: center; }
        .form-group { margin: 15px 0; }
        label { display: block; margin-bottom: 5px; font-weight: bold; color: #34495e; }
        input { width: 100%; padding: 10px; border: 1px solid #bdc3c7; border-radius: 4px; font-size: 14px; }
        button { background: #27ae60; color: white; padding: 12px 25px; border: none; border-radius: 4px; font-size: 16px; cursor: pointer; width: 100%; margin-top: 10px; }
        button:hover { background: #219653; }
        .volver { display: inline-block; margin-top: 20px; color: #3498db; text-decoration: none; }
        .exito { color: green; padding: 10px; background: #e8f8e8; border-radius: 4px; margin-bottom: 15px; }
        .error { color: red; padding: 10px; background: #fde8e8; border-radius: 4px; margin-bottom: 15px; }
    </style>
</head>
<body>
    <h2>📚 Agregar Nuevo Libro</h2>

    <% if (request.getAttribute("exito") != null) { %>
        <div class="exito">✅ Libro agregado correctamente</div>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
        <div class="error">❌ <%= request.getAttribute("error") %></div>
    <% } %>

    <form action="LibroServlet" method="post">
        <div class="form-group">
            <label>Título:</label>
            <input type="text" name="titulo" required>
        </div>
        <div class="form-group">
            <label>Autor:</label>
            <input type="text" name="autor" required>
        </div>
        <div class="form-group">
            <label>Editorial:</label>
            <input type="text" name="editorial">
        </div>
        <div class="form-group">
            <label>Año de publicación:</label>
            <input type="number" name="anio" min="1000" max="2100" required>
        </div>

        <button type="submit">➕ Guardar Libro</button>
    </form>

    <a href="LibroServlet" class="volver">← Volver al listado de libros</a>
</body>
</html>