package modelo;

import java.sql.Date;

public class Prestamo {

    private int id;
    private int libroId;
    private String usuarioNombre;
    private Date fechaPrestamo;
    private Date fechaDevolucion;
    private String estado;

    // Constructor vacío
    public Prestamo() {
    }

    // Constructor completo
    public Prestamo(int id, int libroId, String usuarioNombre,
                    Date fechaPrestamo, Date fechaDevolucion, String estado) {

        this.id = id;
        this.libroId = libroId;
        this.usuarioNombre = usuarioNombre;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLibroId() {
        return libroId;
    }

    public void setLibroId(int libroId) {
        this.libroId = libroId;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}