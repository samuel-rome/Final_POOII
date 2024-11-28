import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private GestorTickets gestor;
    private Scanner scanner;

    public Menu() {
        gestor = new GestorTickets();
        scanner = new Scanner(System.in);
    }

    public void iniciar() {
        inicializarDatos();
        while (true) {
            try {
                System.out.println("\n=== Sistema de Gestión de Tickets ===");
                System.out.println("1. Iniciar Sesión");
                System.out.println("2. Salir");
                System.out.print("Seleccione una opción: ");
                int opcion = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcion) {
                    case 1 -> iniciarSesion();
                    case 2 -> {
                        System.out.println("¡Gracias por usar el sistema!");
                        return;
                    }
                    default -> System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
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
        Usuario usuario = gestor.buscarUsuario(codigoUsuario);

        if (usuario != null) {
            switch (usuario.getTipo().toLowerCase()) {
                case "usuario" -> menuUsuario(usuario);
                case "jefe de proyecto" -> menuJefe(usuario);
                case "coordinador" -> menuCoordinador(usuario);
                default -> System.out.println("Rol no reconocido.");
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
                System.out.println("3. Cerrar Ticket");
                System.out.println("4. Rechazar Solución de Ticket");
                System.out.println("5. Ver Ticket");
                System.out.println("6. Ver Estadísticas");
                System.out.println("7. Salir");
                System.out.print("Seleccione una opción: ");
                int opcion = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcion) {
                    case 1 -> registrarTicket(usuario);
                    case 2 -> gestor.listarTicketsPorUsuario(usuario.getCodigo());
                    case 3 -> cerrarTicket(usuario);
                    case 4 -> rechazarSolucionTicket(usuario);
                    case 5 -> verTicket(usuario);
                    case 6 -> verEstadisticasUsuario(usuario);
                    case 7 -> {
                        return;
                    }
                    default -> System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
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
                System.out.println("2. Listar Tickets Asignados");
                System.out.println("3. Salir");
                System.out.print("Seleccione una opción: ");
                int opcion = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcion) {
                    case 1 -> iniciarTratamiento(usuario);
                    case 2 -> gestor.listarTicketsPorUsuario(usuario.getCodigo());
                    case 3 -> {
                        return;
                    }
                    default -> System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
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
                System.out.println("2. Rechazar Ticket");
                System.out.println("3. Listar Tickets Pendientes");
                System.out.println("4. Salir");
                System.out.print("Seleccione una opción: ");
                int opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> aprobarTicket();
                    case 2 -> rechazarTicket();
                    case 3 -> gestor.listarTicketsPorEstado("Pendiente");
                    case 4 -> {
                        return;
                    }
                    default -> System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine(); 
            }
        }
    }

    private void iniciarTratamiento(Usuario usuario) {
        System.out.print("Ingrese el número del ticket a iniciar tratamiento: ");
        String numero = scanner.nextLine();
        gestor.iniciarTratamiento(numero, usuario.getCodigo());
    }

    private void aprobarTicket() {
        System.out.print("Ingrese el número del ticket a aprobar: ");
        String numero = scanner.nextLine();
        System.out.print("Ingrese el nombre del coordinador asignado: ");
        String coordinador = scanner.nextLine();
        System.out.print("Ingrese una observación para la aprobación: ");
        String observacion = scanner.nextLine();

        gestor.aprobarTicket(numero, observacion, coordinador);
    }

    private void rechazarTicket() {
        System.out.print("Ingrese el número del ticket a rechazar: ");
        String numero = scanner.nextLine();
        System.out.print("Ingrese una observación para el rechazo: ");
        String observacion = scanner.nextLine();

        gestor.rechazarTicket(numero, observacion);
    }

    private void registrarTicket(Usuario usuario) {
        System.out.println("\n=== Registrar Ticket ===");
        System.out.println("Seleccione el tipo de ticket:");
        System.out.println("1. Problema");
        System.out.println("2. Solución");
        System.out.print("Ingrese su opción: ");
        int tipoSeleccionado = scanner.nextInt();
        scanner.nextLine();

        String tipo;
        String[] motivos;
        if (tipoSeleccionado == 1) {
            tipo = "Problema";
            motivos = Ticket.motivosProblema;
        } else if (tipoSeleccionado == 2) {
            tipo = "Solución";
            motivos = Ticket.motivosSolucion;
        } else {
            System.out.println("Opción inválida. Regresando al menú...");
            return;
        }

        System.out.println("\nSeleccione motivo de ticket:");
        for (int i = 0; i < motivos.length; i++) {
            System.out.println((i + 1) + " | " + motivos[i]);
        }
        System.out.print("Ingrese el número del motivo: ");
        int motivoSeleccionado = scanner.nextInt();
        scanner.nextLine();

        if (motivoSeleccionado < 1 || motivoSeleccionado > motivos.length) {
            System.out.println("Motivo inválido. Regresando al menú...");
            return;
        }

        String motivo = motivos[motivoSeleccionado - 1];
        System.out.print("Ingrese el detalle del ticket: ");
        String detalle = scanner.nextLine();

        
        String prioridad = "";
        boolean prioridadValida = false;

        while (!prioridadValida) {
            System.out.println("\nSeleccione la prioridad del ticket:");
            System.out.println("1. Alta");
            System.out.println("2. Media");
            System.out.println("3. Baja");
            System.out.print("Ingrese su opción: ");
            
            int prioridadSeleccionada;
            try {
                prioridadSeleccionada = scanner.nextInt();
                scanner.nextLine();

                switch (prioridadSeleccionada) {
                    case 1 -> {
                        prioridad = "Alta";
                        prioridadValida = true;
                    }
                    case 2 -> {
                        prioridad = "Media";
                        prioridadValida = true;
                    }
                    case 3 -> {
                        prioridad = "Baja";
                        prioridadValida = true;
                    }
                    default -> System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine(); 
            }
        }

        String fecha = "2024-11-25";
        String numero = "T" + (gestor.listaTickets.size() + 1);

        Ticket ticket = new Ticket(numero, tipo, motivo, detalle, "Pendiente", prioridad, usuario.getCodigo(), fecha);
        gestor.registrarTicket(ticket);

        System.out.println("Ticket registrado con éxito como: " + tipo);
    }


    private void cerrarTicket(Usuario usuario) {
        System.out.print("Ingrese el número del ticket a cerrar: ");
        String numero = scanner.nextLine();
        gestor.cerrarTicket(numero, usuario.getCodigo());
    }

    
    private void rechazarSolucionTicket(Usuario usuario) {
        System.out.print("Ingrese el número del ticket a rechazar la solución: ");
        String numero = scanner.nextLine();
        
        Ticket ticket = gestor.buscarTicketPorNumero(numero);
        if (ticket != null && ticket.getSolicitante().equalsIgnoreCase(usuario.getCodigo()) && ticket.getEstado().equalsIgnoreCase("En Tratamiento")) {
            System.out.print("Ingrese una observación para el rechazo de la solución: ");
            String observacion = scanner.nextLine();
            
            gestor.rechazarSolucionTicket(ticket.getNumero(), observacion);
            System.out.println("La solución del ticket ha sido rechazada correctamente y el estado ha vuelto a 'Tratamiento'.");
        } else {
            System.out.println("El ticket no se encuentra en estado 'En Tratamiento' o no pertenece al usuario.");
        }
    }


    private void verTicket(Usuario usuario) {
        System.out.print("Ingrese el número del ticket que desea ver: ");
        String numero = scanner.nextLine();
        Ticket ticket = gestor.buscarTicketPorNumero(numero);

        if (ticket != null && ticket.getSolicitante().equalsIgnoreCase(usuario.getCodigo())) {
            System.out.println("\nDetalles del Ticket:");
            System.out.println(ticket.getInfo());
        } else {
            System.out.println("Ticket no encontrado o no pertenece al usuario.");
        }
    }

    private void verEstadisticasUsuario(Usuario usuario) {
        gestor.mostrarEstadisticas();
    }

    private void inicializarDatos() {
        gestor.registrarUsuario(new Usuario("U001", "Omar Seminario", "Usuario"));
        gestor.registrarUsuario(new Usuario("J001", "Carlo Benito", "Jefe de Proyecto"));
        gestor.registrarUsuario(new Usuario("C001", "Braulio Sanguinetti", "Coordinador"));

        gestor.registrarTicket(new Ticket("T001", "Problema", "FALLA REPORTE", "El reporte no carga", "Pendiente", "Alta", "U001", "2024-11-25"));
        gestor.registrarTicket(new Ticket("T002", "Solución", "NUEVO CAMPO", "Agregar columna a reporte", "Pendiente", "Media", "U001", "2024-11-25"));
        gestor.registrarTicket(new Ticket("T003", "Problema", "FALLA PROCESO", "El sistema no procesa correctamente las facturas.", "Pendiente", "Alta", "U001", "2024-11-25"));
        gestor.registrarTicket(new Ticket("T004", "Solución", "NUEVO MENU", "Agregar un reporte mensual de inventarios.", "Pendiente", "Media", "U001", "2024-11-26"));
        gestor.registrarTicket(new Ticket("T005", "Problema", "ERROR CONEXION", "Los usuarios no pueden iniciar sesión.", "Pendiente", "Alta", "U001", "2024-11-26"));
        gestor.registrarTicket(new Ticket("T006", "Problema", "ERROR IMPRESION", "El sistema es lento al cargar datos.", "Pendiente", "Baja", "U001", "2024-11-27"));
        gestor.registrarTicket(new Ticket("T007", "Solución", "NUEVO ACCESO", "Solicito una capacitación .", "Pendiente", "Media", "U001", "2024-11-27"));
        gestor.registrarTicket(new Ticket("T008", "Problema", "FALLA REPORTE", "Los reportes no se generan .", "Pendiente", "Alta", "U001", "2024-11-28"));
        gestor.registrarTicket(new Ticket("T009", "Solución", "REACT. USUARIO", "Actualizar la interfaz gráfica para facilitar la navegación.", "Pendiente", "Baja", "U001", "2024-11-28"));
        gestor.registrarTicket(new Ticket("T010", "Problema", "FALLA REPORTE", "El sistema se congela al intentar guardar datos grandes.", "Pendiente", "Alta", "U001", "2024-11-29"));
    }

}
