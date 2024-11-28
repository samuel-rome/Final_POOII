public class Ticket {

    public static final String[] motivosProblema = {
        "FALLA REPORTE",
        "FALLA PROGRAMA",
        "FALLA PROCESO",
        "ERROR ACCESOS",
        "ERROR CONEXION",
        "ERROR IMPRESION"
    };

    public static final String[] motivosSolucion = {
        "NUEVO REPORTE",
        "NUEVO CAMPO",
        "NUEVO PROGRAMA",
        "NUEVO MENU",
        "NUEVO ACCESO",
        "NUEVO USUARIO",
        "REACT. USUARIO",
        "BAJA DE USUARIO"
    };

    private String numero;
    private String tipo;
    private String motivo;
    private String detalle;
    private String estado;
    private String prioridad;
    private String solicitante;
    private String fechaSolicitud;
    private String coordinadorAsignado;
    private String observacionRechazo; 

    public Ticket(String numero, String tipo, String motivo, String detalle, String estado, String prioridad, String solicitante, String fechaSolicitud) {
        this.numero = numero;
        this.tipo = tipo;
        this.motivo = motivo;
        this.detalle = detalle;
        this.estado = estado;
        this.prioridad = prioridad;
        this.solicitante = solicitante;
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getNumero() {
        return numero;
    }

    public String getTipo() {
        return tipo;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getDetalle() {
        return detalle;
    }

    public String getEstado() {
        return estado;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    public String getCoordinadorAsignado() {
        return coordinadorAsignado;
    }

    public String getObservacionRechazo() {
        return observacionRechazo;
    }

    public void setObservacionRechazo(String observacionRechazo) {
        this.observacionRechazo = observacionRechazo;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void asignarCoordinador(String coordinador) {
        this.coordinadorAsignado = coordinador;
    }

    public void actualizarEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public String getInfo() {
        return "Número: " + numero + ", Tipo: " + tipo + ", Motivo: " + motivo + ", Detalle: " + detalle +
                ", Estado: " + estado + ", Prioridad: " + prioridad + ", Solicitante: " + solicitante +
                ", Fecha: " + fechaSolicitud + ", Coordinador: " + (coordinadorAsignado != null ? coordinadorAsignado : "N/A") +
                ", Observación Rechazo: " + (observacionRechazo != null ? observacionRechazo : "N/A");
    }
}
