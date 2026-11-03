package es.iesfranciscodelosrios.chat;

import jakarta.xml.bind.JAXBException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Coordina las operaciones de usuarios, mensajes y conversaciones del chat.
 */
public class GestorChat {
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final GestorXML gestorXML = new GestorXML();

    /**
     * Registra un usuario si todavía no existe y persiste la lista actualizada.
     *
     * @param nombre nombre del usuario que se desea registrar
     * @throws JAXBException si se produce un error al leer o guardar los usuarios
     */
    public void registrarUsuario(String nombre) throws JAXBException {
        ListaUsuarios usuarios = gestorXML.leerUsuarios();
        for (Usuario usuario : usuarios.getUsuarios()) {
            if (usuario.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println("El usuario '" + nombre + "' ya está registrado.");
                return;
            }
        }
        // Registrar el nuevo usuario y guardar en XML
        usuarios.getUsuarios().add(new Usuario(nombre));
        gestorXML.guardarUsuarios(usuarios);
        System.out.println("El usuario '" + nombre + "' se ha registrado con éxito.");
    }

    /**
     * Comprueba si existe un usuario registrado con el nombre indicado.
     *
     * @param nombre nombre que se desea buscar
     * @return {@code true} si el usuario está registrado; {@code false} en caso contrario
     * @throws JAXBException si se produce un error al leer los usuarios
     */
    public boolean estaRegistrado(String nombre) throws JAXBException {
        if (nombre == null || nombre.isBlank()) {
            return false;
        }

        String nombreBuscado = nombre.trim();
        ListaUsuarios usuarios = gestorXML.leerUsuarios();

        for (Usuario usuario : usuarios.getUsuarios()) {
            String registrado = usuario.getNombre();
            if (registrado != null && registrado.equalsIgnoreCase(nombreBuscado)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Crea y guarda un mensaje entre dos usuarios registrados.
     *
     * @param emisor nombre del usuario que envía el mensaje
     * @param destinatario nombre del usuario que recibe el mensaje
     * @param contenido texto del mensaje
     * @return el mensaje creado y persistido
     * @throws JAXBException si se produce un error al leer o guardar los mensajes
     * @throws IllegalArgumentException si alguno de los usuarios no está registrado o el mensaje está vacío
     */
    public Mensaje enviarMensaje(String emisor, String destinatario, String contenido) throws JAXBException {
        if (!estaRegistrado(emisor) || !estaRegistrado(destinatario)){
            throw new IllegalArgumentException("El emisor y el destinatario deben estar registrados.");

        }
        if (contenido.isEmpty()) {
            throw new IllegalArgumentException("El mensaje no puede estar vacío.");
        }

        Mensaje nuevoMensaje = new Mensaje(emisor, destinatario, contenido, LocalDateTime.now().toString());
        ListaMensajes mensajes = gestorXML.leerMensajes();
        mensajes.getMensajes().add(nuevoMensaje);
        gestorXML.guardarMensajes(mensajes);
        System.out.println("Mensaje enviado de " + emisor + " a " + destinatario + ".");
        return nuevoMensaje;
    }

    /**
     * Obtiene los mensajes intercambiados entre dos usuarios, en cualquiera de los sentidos.
     *
     * @param usuarioA primer usuario de la conversación
     * @param usuarioB segundo usuario de la conversación
     * @return lista de mensajes de la conversación, o {@code null} si algún nombre o dato de mensaje es nulo
     * @throws JAXBException si se produce un error al leer los mensajes
     */
    public List<Mensaje>consultarConversacion(String usuarioA, String usuarioB) throws JAXBException {
        List<Mensaje> conversacion = new ArrayList<>();
        for (Mensaje mensaje : gestorXML.leerMensajes().getMensajes()) {
            if (mensaje.getEmisor() == null || mensaje.getDestinatario() == null || usuarioA == null || usuarioB == null) {
                return null;
            }
            if (mensaje.getEmisor().equalsIgnoreCase(usuarioA) && mensaje.getDestinatario().equalsIgnoreCase(usuarioB) ||
            mensaje.getEmisor().equalsIgnoreCase(usuarioB) && mensaje.getDestinatario().equalsIgnoreCase(usuarioA)) {

                conversacion.add(mensaje);
            }
        }
        return conversacion;
    }

    /**
     * Cuenta cuántos mensajes de la conversación ha enviado cada usuario.
     *
     * @param usuarioA primer usuario de la conversación
     * @param usuarioB segundo usuario de la conversación
     * @return mapa con cada emisor y el número de mensajes enviados
     * @throws JAXBException si se produce un error al leer los mensajes
     */
    public Map<String, Long> estadisticas(String usuarioA, String usuarioB) throws JAXBException {
        return consultarConversacion(usuarioA, usuarioB).stream()
                .filter(mensaje -> mensaje.getEmisor() != null)
                .collect(Collectors.groupingBy(
                        Mensaje::getEmisor,
                        LinkedHashMap::new,
                        Collectors.counting()
                ));
    }

    /**
     * Exporta los mensajes intercambiados entre dos usuarios al archivo {@code Conversación}.
     *
     * @param usuarioA primer usuario de la conversación
     * @param usuarioB segundo usuario de la conversación
     * @throws JAXBException si se produce un error al leer los mensajes
     * @throws IOException si se produce un error al escribir el archivo
     */
    public void exportarConversacion(String usuarioA, String usuarioB) throws JAXBException, IOException {
        List<Mensaje> conversacion = consultarConversacion(usuarioA, usuarioB);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Conversación", StandardCharsets.UTF_8))) {
            for (Mensaje mensaje : conversacion) {
                String linea = formatearMensaje(mensaje);
                bw.write(linea);
                bw.newLine();
            }
        }
    }

    /**
     * Convierte un mensaje en una línea legible con fecha, emisor y contenido.
     *
     * @param mensaje mensaje que se desea formatear
     * @return representación textual del mensaje
     */
    private String formatearMensaje(Mensaje mensaje) {
        String fecha = mensaje.getFecha();
        fecha = LocalDateTime.parse(fecha).format(FORMATO_FECHA);
        return "[" + fecha + "] " + mensaje.getEmisor()
                + ": " + mensaje.getContenido();
    }
}
