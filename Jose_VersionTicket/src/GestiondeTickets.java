// Versión 1: Gestor de Tickets Sin Herencia o Polimorfismo

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class GestiondeTickets {
    List<Ticket> listaTickets;
    private List<Usuario> listaUsuarios;
    private Scanner scanner;

    public GestiondeTickets() {
        this.listaTickets = new ArrayList<>();
        this.listaUsuarios = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        inicializarUsuarios(); // Inicializa los usuarios de manera simple
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
        for (Ticket ticket : listaTickets) {
            System.out.println(ticket.getInfo());
        }
    }

    public void aprobarTicket() {
        System.out.print("Ingrese el número del ticket a aprobar: ");
        String numeroTicket = scanner.nextLine();
        System.out.print("Ingrese el nombre del coordinador asignado: ");
        String coordinador = scanner.nextLine();
        for (Ticket ticket : listaTickets) {
            if (ticket.getNumero().equalsIgnoreCase(numeroTicket)) {
                ticket.setEstado("Aprobado");
                ticket.setCoordinadorAsignado(coordinador);
                System.out.println("Ticket aprobado: " + numeroTicket);
                return;
            }
        }
        System.out.println("No se encontró el ticket: " + numeroTicket);
    }

    private void inicializarUsuarios() {
        registrarUsuario(new Usuario("U001", "Omar Seminario", "Usuario"));
        registrarUsuario(new Usuario("J001", "Carlo Benito", "Jefe de Proyecto"));
        registrarUsuario(new Usuario("C001", "Braulio Sanguinetti", "Coordinador"));
    }
}

// Clases simples sin herencia
class Ticket {
    private String numero;
    private String tipo;
    private String motivo;
    private String detalle;
    private String estado;
    private String prioridad;
    private String solicitante;
    private String fechaSolicitud;
    private String coordinadorAsignado;

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

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setCoordinadorAsignado(String coordinador) {
        this.coordinadorAsignado = coordinador;
    }

    public String getInfo() {
        return "Número: " + numero + ", Tipo: " + tipo + ", Motivo: " + motivo + ", Estado: " + estado + ", Prioridad: " + prioridad + ", Solicitante: " + solicitante + ", Coordinador: " + (coordinadorAsignado != null ? coordinadorAsignado : "N/A");
    }
}

class Usuario {
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
}
