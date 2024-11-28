import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class GestorTickets {
    List<Ticket> listaTickets;
    private List<Usuario> listaUsuarios;

    public GestorTickets() {
        this.listaTickets = new ArrayList<>();
        this.listaUsuarios = new ArrayList<>();
        inicializarUsuarios(); 
    }

    public void registrarUsuario(Usuario usuario) {
        listaUsuarios.add(usuario);
    }

    public Usuario buscarUsuario(String codigoUsuario) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getCodigo().equalsIgnoreCase(codigoUsuario)) {
                return usuario;
            }
        }
        return null;
    }

    public void registrarTicket(Ticket ticket) {
        listaTickets.add(ticket);
    }

    public void listarTickets() {
        System.out.println("\n=== Listado de Tickets ===");
        System.out.printf("%-10s %-15s %-20s %-60s %-15s %-10s %-15s %-15s %-20s%n",
                "Número", "Tipo", "Motivo", "Detalle", "Estado", "Prioridad", "Solicitante", "Fecha", "Coordinador");
        System.out.println("====================================================================================================================================");
        for (Ticket ticket : listaTickets) {
            System.out.printf("%-10s %-15s %-20s %-60s %-15s %-10s %-15s %-15s %-20s%n",
                    ticket.getNumero(), ticket.getTipo(), ticket.getMotivo(), ticket.getDetalle(), ticket.getEstado(),
                    ticket.getPrioridad(), ticket.getSolicitante(), ticket.getFechaSolicitud(),
                    ticket.getCoordinadorAsignado() != null ? ticket.getCoordinadorAsignado() : "N/A");
        }
    }

    public void listarTicketsPorEstado(String estado) {
        System.out.println("\nTickets con estado " + estado + ":");
        System.out.printf("%-10s %-15s %-20s %-60s %-15s %-10s %-15s %-15s %-20s%n",
                "Número", "Tipo", "Motivo", "Detalle", "Estado", "Prioridad", "Solicitante", "Fecha", "Coordinador");
        System.out.println("====================================================================================================================================");
        for (Ticket ticket : listaTickets) {
            if (ticket.getEstado().equalsIgnoreCase(estado)) {
                System.out.printf("%-10s %-15s %-20s %-60s %-15s %-10s %-15s %-15s %-20s%n",
                        ticket.getNumero(), ticket.getTipo(), ticket.getMotivo(), ticket.getDetalle(), ticket.getEstado(),
                        ticket.getPrioridad(), ticket.getSolicitante(), ticket.getFechaSolicitud(),
                        ticket.getCoordinadorAsignado() != null ? ticket.getCoordinadorAsignado() : "N/A");
            }
        }
    }

    public void listarTicketsPorUsuario(String codigoUsuario) {
        System.out.println("\nTickets del usuario " + codigoUsuario + ":");
        System.out.printf("%-10s %-15s %-20s %-60s %-15s %-10s %-15s %-15s %-20s%n",
                "Número", "Tipo", "Motivo", "Detalle", "Estado", "Prioridad", "Solicitante", "Fecha", "Coordinador");
        System.out.println("====================================================================================================================================");
        for (Ticket ticket : listaTickets) {
            if (ticket.getSolicitante().equalsIgnoreCase(codigoUsuario)) {
                System.out.printf("%-10s %-15s %-20s %-60s %-15s %-10s %-15s %-15s %-20s%n",
                        ticket.getNumero(), ticket.getTipo(), ticket.getMotivo(), ticket.getDetalle(), ticket.getEstado(),
                        ticket.getPrioridad(), ticket.getSolicitante(), ticket.getFechaSolicitud(),
                        ticket.getCoordinadorAsignado() != null ? ticket.getCoordinadorAsignado() : "N/A");
            }
        }
    }

    public void aprobarTicket(String numeroTicket, String observacion, String coordinador) {
        for (Ticket ticket : listaTickets) {
            if (ticket.getNumero().equalsIgnoreCase(numeroTicket)) {
                ticket.actualizarEstado("Aprobado");
                ticket.asignarCoordinador(coordinador);
                System.out.println("Ticket aprobado: " + numeroTicket + " - Observación: " + observacion + " - Coordinador Asignado: " + coordinador);
                return;
            }
        }
        System.out.println("No se encontró el ticket: " + numeroTicket);
    }

    public void rechazarTicket(String numeroTicket, String observacion) {
        for (Ticket ticket : listaTickets) {
            if (ticket.getNumero().equalsIgnoreCase(numeroTicket)) {
                ticket.actualizarEstado("Rechazado");
                System.out.println("Ticket rechazado: " + numeroTicket + " - Observación: " + observacion);
                return;
            }
        }
        System.out.println("No se encontró el ticket: " + numeroTicket);
    }

    public void iniciarTratamiento(String numeroTicket, String coordinador) {
        for (Ticket ticket : listaTickets) {
            if (ticket.getNumero().equalsIgnoreCase(numeroTicket) && ticket.getEstado().equalsIgnoreCase("Aprobado")) {
                ticket.actualizarEstado("En Tratamiento");
                System.out.println("Tratamiento iniciado para el ticket " + numeroTicket + " por el coordinador " + coordinador);
                return;
            }
        }
        System.out.println("No se pudo iniciar tratamiento para el ticket: " + numeroTicket);
    }

    public void cerrarTicket(String numeroTicket, String usuarioCierre) {
        for (Ticket ticket : listaTickets) {
            if (ticket.getNumero().equalsIgnoreCase(numeroTicket)) {
                ticket.actualizarEstado("Cerrado");
                System.out.println("Ticket cerrado: " + numeroTicket + " - Cerrado por: " + usuarioCierre);
                return;
            }
        }
        System.out.println("No se encontró el ticket: " + numeroTicket);
    }

    public void rechazarSolucionTicket(String numeroTicket, String observacion) {
        for (Ticket ticket : listaTickets) {
            if (ticket.getNumero().equalsIgnoreCase(numeroTicket) && ticket.getEstado().equalsIgnoreCase("En Tratamiento")) {
                ticket.actualizarEstado("Tratamiento");
                ticket.setObservacionRechazo(observacion);
                System.out.println("Solución rechazada y el ticket vuelve a estado 'Tratamiento'. Observación: " + observacion);
                return;
            }
        }
        System.out.println("No se encontró el ticket en estado 'En Tratamiento': " + numeroTicket);
    }

    public void mostrarEstadisticas() {
        int pendientes = 0, aprobados = 0, rechazados = 0, enTratamiento = 0, cerrados = 0;
        for (Ticket ticket : listaTickets) {
            switch (ticket.getEstado()) {
                case "Pendiente" -> pendientes++;
                case "Aprobado" -> aprobados++;
                case "Rechazado" -> rechazados++;
                case "En Tratamiento" -> enTratamiento++;
                case "Cerrado" -> cerrados++;
            }
        }

        int total = listaTickets.size();
        System.out.println("\nEstadísticas de Tickets:");
        System.out.printf("Pendientes: %d (%.2f%%)\n", pendientes, (pendientes * 100.0) / total);
        System.out.printf("Aprobados: %d (%.2f%%)\n", aprobados, (aprobados * 100.0) / total);
        System.out.printf("Rechazados: %d (%.2f%%)\n", rechazados, (rechazados * 100.0) / total);
        System.out.printf("En Tratamiento: %d (%.2f%%)\n", enTratamiento, (enTratamiento * 100.0) / total);
        System.out.printf("Cerrados: %d (%.2f%%)\n", cerrados, (cerrados * 100.0) / total);
    }

    public Ticket buscarTicketPorNumero(String numeroTicket) {
        for (Ticket ticket : listaTickets) {
            if (ticket.getNumero().equalsIgnoreCase(numeroTicket)) {
                return ticket;
            }
        }
        return null;
    }

    private void inicializarUsuarios() {
        registrarUsuario(new UsuarioNormal("U001", "Omar Seminario"));
        registrarUsuario(new JefeDeProyecto("J001", "Carlo Benito"));
        registrarUsuario(new Coordinador("C001", "Braulio Sanguinetti"));
    }

    public void iniciar() {
        try {
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número.");
        }
    }
}
