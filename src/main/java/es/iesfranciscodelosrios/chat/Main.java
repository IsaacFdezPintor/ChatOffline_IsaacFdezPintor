package es.iesfranciscodelosrios.chat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Scanner teclado = new Scanner(System.in);
    private static final GestorChat chat = new GestorChat();

    public static void main(String[] args) {
        int opcionPrincipal = -1;

        do {
            System.out.println("\n=== CHAT OFFLINE - MENÚ PRINCIPAL ===");
            System.out.println("1. Registrar nuevo usuario");
            System.out.println("2. Iniciar sesión");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                String linea = teclado.nextLine().trim();
                opcionPrincipal = Integer.parseInt(linea);
                switch (opcionPrincipal) {
                    case 1:
                        registrarUsuario();
                        break;
                    case 2:
                        iniciarSesion();
                        break;
                    case 0:
                        System.out.println("¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, ingrese un número entero válido.");
            } catch (Exception e) {
                System.out.println("Error en la aplicación: " + e.getMessage());
            }

        } while (opcionPrincipal != 0);

        teclado.close();
    }

    private static void registrarUsuario() {
        System.out.print("\nIngrese el nombre del nuevo usuario: ");
        String nuevoUsuario = teclado.nextLine().trim();
        if (!nuevoUsuario.isEmpty()) {
            try {
                chat.registrarUsuario(nuevoUsuario);
                mostrarSubmenuUsuario(nuevoUsuario);
            } catch (Exception e) {
                System.out.println("No se pudo registrar el usuario: " + e.getMessage());
            }
        } else {
            System.out.println("El nombre de usuario no puede estar vacío.");
        }
    }

    private static void iniciarSesion() {
        System.out.print("\nIngrese su nombre de usuario: ");
        String usuarioActivo = teclado.nextLine().trim();
        if (usuarioActivo.isEmpty()) {
            System.out.println("El nombre de usuario no puede estar vacío.");
            return;
        }
        try {
            if (!chat.estaRegistrado(usuarioActivo)) {
                System.out.println("El usuario no está registrado.");
                return;
            }
            System.out.println("Sesión iniciada como: " + usuarioActivo);
            mostrarSubmenuUsuario(usuarioActivo);
        } catch (Exception e) {
            System.out.println("No se pudo iniciar sesión: " + e.getMessage());
        }
    }

    private static void mostrarSubmenuUsuario(String usuarioActivo) {
        int opcionSubmenu = -1;

        do {
            System.out.println("\n--- MENÚ DE USUARIO (" + usuarioActivo + ") ---");
            System.out.println("1. Enviar mensaje");
            System.out.println("2. Consultar conversación");
            System.out.println("3. Ver estadísticas de una conversación");
            System.out.println("4. Exportar conversación a TXT");
            System.out.println("0. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            try {
                String linea = teclado.nextLine().trim();
                opcionSubmenu = Integer.parseInt(linea);

                switch (opcionSubmenu) {
                    case 1:
                        System.out.print("Destinatario: ");
                        String destinatario = teclado.nextLine().trim();
                        System.out.print("Mensaje: ");
                        String contenido = teclado.nextLine().trim();
                        chat.enviarMensaje(usuarioActivo, destinatario, contenido);
                        break;

                    case 2:
                        System.out.print("¿Con qué usuario desea ver la conversación?: ");
                        String otroUsuario = teclado.nextLine().trim();
                        DateTimeFormatter formatoSalida = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                        List<Mensaje> mensajes = chat.consultarConversacion(usuarioActivo, otroUsuario);
                        if (mensajes.isEmpty()) {
                            System.out.println("No hay mensajes en esta conversación.");
                        } else {
                            System.out.println("\n--- Conversación ---");
                            for (int i = 0; i < mensajes.size(); i++) {
                                Mensaje mensaje = mensajes.get(i);
                                LocalDateTime fechaObjeto = LocalDateTime.parse(mensaje.getFecha());
                                String fechaLimpia = fechaObjeto.format(formatoSalida);
                                System.out.println((i + 1) + ". [" + fechaLimpia + "] " + mensaje.getEmisor()
                                        + ": " + mensaje.getContenido());
                            }
                            break;
                        }

                    case 3:
                        System.out.print("Indique el otro usuario de la conversación: ");
                        String usuarioEst = teclado.nextLine().trim();
                        Map<String, Long> stats = chat.estadisticas(usuarioActivo, usuarioEst);
                        System.out.println("\n--- Mensajes por emisor ---");
                        stats.forEach((emisor, total) -> System.out.println(emisor + ": " + total));
                        break;

                    case 4:
                        System.out.print("Indique el otro usuario de la conversación: ");
                        String usuarioExp = teclado.nextLine().trim();
                        chat.exportarConversacion(usuarioActivo, usuarioExp);
                        System.out.println("Conversación exportada exitosamente.");
                        break;
                    case 0:
                        System.out.println("Cerrando sesión de " + usuarioActivo + "...");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Introduzca un número entero válido.");
            } catch (Exception e) {
                System.out.println("Error al ejecutar la acción: " + e.getMessage());
            }

        } while (opcionSubmenu != 0);
    }

}