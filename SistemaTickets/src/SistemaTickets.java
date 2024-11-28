import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SistemaTickets {
    private List<Ticket> listaTickets;
    private List<Usuario> listaUsuarios;
    private Scanner scanner;

    public SistemaTickets() {
        this.listaTickets = new ArrayList<>();
        this.listaUsuarios = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        inicializarUsuarios();
    }

    public void iniciar() {
        while (true) {
            try {
                System.out.println("\n=== Sistema de Gestión de Tickets ===");
                System.out.println("1. Iniciar Sesión");
                System.out.println("2. Salir");
                System.out.print("Seleccione una opción: ");
                int opcion = scanner.nextInt();
                scanner.nextLine();

                if (opcion == 1) {
                    iniciarSesion();
                } else if (opcion == 2) {
                    System.out.println("Gracias por usar el sistema.");
                    break;
                } else {
                    System.out.println("Opción inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine();
            }
        }
    }

    private void iniciarSesion() {
        System.out.print("Ingrese su código de usuario: ");
        String codigoUsuario = scanner.nextLine();
        Usuario usuario = buscarUsuario(codigoUsuario);

        if (usuario != null) {
            if (usuario.getTipo().equalsIgnoreCase("Usuario")) {
                menuUsuario(usuario);
            } else if (usuario.getTipo().equalsIgnoreCase("Jefe de Proyecto")) {
                menuJefe(usuario);
            } else if (usuario.getTipo().equalsIgnoreCase("Coordinador")) {
                menuCoordinador(usuario);
            } else {
                System.out.println("Rol no reconocido.");
            }
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }

    private void menuUsuario(Usuario usuario) {
        while (true) {
            try {
                System.out.println("\n=== Menú Usuario ===");
                System.out.println("1. Registrar Ticket");
                System.out.println("2. Listar Mis Tickets");
                System.out.println("3. Salir");
                System.out.print("Seleccione una opción: ");
                int opcion = scanner.nextInt();
                scanner.nextLine();

                if (opcion == 1) {
                    registrarTicket(usuario);
                } else if (opcion == 2) {
                    listarTicketsPorUsuario(usuario.getCodigo());
                } else if (opcion == 3) {
                    break;
                } else {
                    System.out.println("Opción inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine();
            }
        }
    }

    private void menuJefe(Usuario usuario) {
        while (true) {
            try {
                System.out.println("\n=== Menú Jefe de Proyecto ===");
                System.out.println("1. Aprobar Ticket");
                System.out.println("2. Salir");
                System.out.print("Seleccione una opción: ");
                int opcion = scanner.nextInt();
                scanner.nextLine();

                if (opcion == 1) {
                    aprobarTicket();
                } else if (opcion == 2) {
                    break;
                } else {
                    System.out.println("Opción inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine();
            }
        }
    }

    private void menuCoordinador(Usuario usuario) {
        while (true) {
            try {
                System.out.println("\n=== Menú Coordinador ===");
                System.out.println("1. Iniciar Tratamiento");
                System.out.println("2. Salir");
                System.out.print("Seleccione una opción: ");
                int opcion = scanner.nextInt();
                scanner.nextLine();

                if (opcion == 1) {
                    iniciarTratamiento();
                } else if (opcion == 2) {
                    break;
                } else {
                    System.out.println("Opción inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine();
            }
        }
    }

    private void registrarTicket(Usuario usuario) {
        System.out.print("Ingrese el tipo de ticket (Problema/Solución): ");
        String tipo = scanner.nextLine();
        System.out.print("Ingrese el motivo del ticket: ");
        String motivo = scanner.nextLine();
        System.out.print("Ingrese el detalle del ticket: ");
        String detalle = scanner.nextLine();
        System.out.print("Ingrese la prioridad (Alta/Media/Baja): ");
        String prioridad = scanner.nextLine();
        String numero = "T" + (listaTickets.size() + 1);
        String fechaSolicitud = "2024-11-25";

        Ticket ticket = new Ticket(numero, tipo, motivo, detalle, "Pendiente", prioridad, usuario.getCodigo(), fechaSolicitud);
        listaTickets.add(ticket);

        System.out.println("Ticket registrado con éxito.");
    }

    private void aprobarTicket() {
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

    private void iniciarTratamiento() {
        System.out.print("Ingrese el número del ticket a iniciar tratamiento: ");
        String numeroTicket = scanner.nextLine();
        for (Ticket ticket : listaTickets) {
            if (ticket.getNumero().equalsIgnoreCase(numeroTicket) && ticket.getEstado().equalsIgnoreCase("Aprobado")) {
                ticket.setEstado("En Tratamiento");
                System.out.println("Tratamiento iniciado para el ticket: " + numeroTicket);
                return;
            }
        }
        System.out.println("No se pudo iniciar tratamiento para el ticket: " + numeroTicket);
    }

    private void listarTicketsPorUsuario(String codigoUsuario) {
        System.out.println("\nTickets del usuario " + codigoUsuario + ":");
        for (Ticket ticket : listaTickets) {
            if (ticket.getSolicitante().equalsIgnoreCase(codigoUsuario)) {
                System.out.println(ticket.getInfo());
            }
        }
    }

    private void inicializarUsuarios() {
        listaUsuarios.add(new Usuario("U001", "Omar Seminario", "Usuario"));
        listaUsuarios.add(new Usuario("J001", "Carlo Benito", "Jefe de Proyecto"));
        listaUsuarios.add(new Usuario("C001", "Braulio Sanguinetti", "Coordinador"));
    }

    private Usuario buscarUsuario(String codigoUsuario) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getCodigo().equalsIgnoreCase(codigoUsuario)) {
                return usuario;
            }
        }
        return null;
    }
}


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

    public String getEstado() {
        return estado;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public String getInfo() {
        return "Número: " + numero + ", Tipo: " + tipo + ", Motivo: " + motivo + ", Detalle: " + detalle + 
                ", Estado: " + estado + ", Prioridad: " + prioridad + ", Solicitante: " + solicitante +
                ", Fecha: " + fechaSolicitud + ", Coordinador: " + (coordinadorAsignado != null ? coordinadorAsignado : "N/A");
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

    public String getTipo() {
        return tipo;
    }
}
