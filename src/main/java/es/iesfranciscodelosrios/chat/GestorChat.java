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

public class GestorChat {
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final GestorXML gestorXML = new GestorXML();

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

    public Map<String, Long> estadisticas(String usuarioA, String usuarioB) throws JAXBException {
        return consultarConversacion(usuarioA, usuarioB).stream()
                .filter(mensaje -> mensaje.getEmisor() != null)
                .collect(Collectors.groupingBy(
                        Mensaje::getEmisor,
                        LinkedHashMap::new,
                        Collectors.counting()
                ));
    }

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

    private String formatearMensaje(Mensaje mensaje) {
        String fecha = mensaje.getFecha();
        fecha = LocalDateTime.parse(fecha).format(FORMATO_FECHA);
        return "[" + fecha + "] " + mensaje.getEmisor()
                + ": " + mensaje.getContenido();
    }
}
