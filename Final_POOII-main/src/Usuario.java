public class Usuario {
    private String codigo;
    private String nombre;
    private String tipo;

    public Usuario(String codigo, String nombre, String tipo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getInfo() {
        return "Usuario: " + nombre + " (" + tipo + ")";
    }
}
