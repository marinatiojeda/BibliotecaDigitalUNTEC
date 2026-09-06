# Biblioteca Digital UNTEC

Sistema web para la gestión de una biblioteca, desarrollado en Java utilizando JSP, Servlets, JDBC y MySQL.

## Descripción

Biblioteca Digital UNTEC permite administrar libros, usuarios y préstamos de una biblioteca desde una aplicación web.

El sistema permite registrar libros, consultar el catálogo, buscar libros, modificar información, eliminar registros y gestionar el estado de los libros.

También cuenta con un módulo de préstamos que permite registrar préstamos y realizar devoluciones.

## Funcionalidades

### Gestión de libros
- Agregar nuevos libros.
- Editar información de los libros.
- Eliminar libros.
- Consultar el catálogo.
- Buscar libros por título o autor.
- Mostrar libros disponibles y prestados.
- Cambiar el estado de disponibilidad de los libros.
- Paginación del catálogo.

### Gestión de préstamos
- Registrar nuevos préstamos.
- Consultar préstamos registrados.
- Editar préstamos.
- Eliminar préstamos.
- Registrar devolución de libros.
- Cambiar automáticamente el estado del préstamo entre `PRESTADO` y `DEVUELTO`.
- Mostrar la fecha de devolución.

### Usuarios
- Inicio de sesión.
- Control de sesión del usuario.
- Cierre de sesión.

## Tecnologías utilizadas

- Java
- JSP
- Servlets
- JDBC
- MySQL
- HTML
- CSS
- JSTL
- Apache Tomcat
- Eclipse
- Git y GitHub

## Base de datos

El sistema utiliza una base de datos MySQL llamada:

`biblioteca_untec`

Entre las tablas principales se encuentran:

- `libros`
- `prestamos`
- `usuarios`

La tabla `prestamos` se relaciona con `libros` y `usuarios mediante claves foráneas.

## Estructura del proyecto

```text
BibliotecaDigitalUNTEC
│
├── src
│   └── main
│       ├── java
│       │   ├── controlador
│       │   ├── dao
│       │   └── modelo
│       │
│       └── webapp
│           ├── WEB-INF
│           ├── agregarLibro.jsp
│           ├── agregarPrestamo.jsp
│           └── ListarPrestamos.jsp
│
└── .gitignore
