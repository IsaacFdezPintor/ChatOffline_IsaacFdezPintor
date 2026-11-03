package es.iesfranciscodelosrios.chat;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

/**
 * Representa un mensaje enviado entre dos usuarios.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Mensaje {
    private String id;
    private String emisor;
    private String destinatario;
    private String contenido;
    private String fecha; // ISO-8601, por ejemplo: 2026-10-26T13:45:00

    /**
     * Crea un mensaje vacío para que JAXB pueda deserializarlo.
     */
    public Mensaje() {
    } // JAXB necesita un constructor sin argumentos.

    /**
     * Crea un mensaje y le asigna un identificador único.
     *
     * @param emisor nombre del usuario que envía el mensaje
     * @param destinatario nombre del usuario que recibe el mensaje
     * @param contenido texto del mensaje
     * @param fecha fecha y hora del mensaje en formato ISO-8601
     */
    public Mensaje(String emisor, String destinatario, String contenido, String fecha) {
        this.id = java.util.UUID.randomUUID().toString();
        this.emisor = emisor;
        this.destinatario = destinatario;
        this.contenido = contenido;
        this.fecha = fecha;
    }

    /**
     * Devuelve el identificador único del mensaje.
     *
     * @return identificador del mensaje
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador del mensaje.
     *
     * @param id identificador que se asignará
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Devuelve el emisor del mensaje.
     *
     * @return nombre del emisor
     */
    public String getEmisor() {
        return emisor;
    }

    /**
     * Establece el emisor del mensaje.
     *
     * @param emisor nombre del emisor
     */
    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    /**
     * Devuelve el destinatario del mensaje.
     *
     * @return nombre del destinatario
     */
    public String getDestinatario() {
        return destinatario;
    }

    /**
     * Establece el destinatario del mensaje.
     *
     * @param destinatario nombre del destinatario
     */
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    /**
     * Devuelve el contenido del mensaje.
     *
     * @return texto del mensaje
     */
    public String getContenido() {
        return contenido;
    }

    /**
     * Establece el contenido del mensaje.
     *
     * @param contenido texto que se asignará al mensaje
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    /**
     * Devuelve la fecha del mensaje en formato ISO-8601.
     *
     * @return fecha y hora del mensaje
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha del mensaje en formato ISO-8601.
     *
     * @param fecha fecha y hora que se asignará
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
