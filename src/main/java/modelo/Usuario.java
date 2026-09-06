package modelo;

public class Usuario {
    private int id;
    private String nombre;
    private String clave;
    private String tipo;

    // ✅ Constructor con 4 parámetros (EL QUE NECESITAMOS)
    public Usuario(int id, String nombre, String clave, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.clave = clave;
        this.tipo = tipo;
    }

    // ✅ Métodos que faltaban
    public String getNombre() {
        return nombre;
    }

    public String getClave() {
        return clave;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean esAdmin() {
        return "admin".equals(this.tipo);
    }
}