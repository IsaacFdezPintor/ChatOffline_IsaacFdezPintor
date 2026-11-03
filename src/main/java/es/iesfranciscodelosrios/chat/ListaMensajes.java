package es.iesfranciscodelosrios.chat;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Contenedor JAXB de los mensajes almacenados en el chat.
 */
@XmlRootElement(name = "mensajes")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListaMensajes {
    @XmlElement(name = "mensaje")
    private List<Mensaje> mensajes = new ArrayList<>();

    /**
     * Crea un contenedor vacío, necesario también para la deserialización de JAXB.
     */
    public ListaMensajes() { }

    /**
     * Devuelve los mensajes del contenedor.
     *
     * @return lista de mensajes
     */
    public List<Mensaje> getMensajes() { return mensajes; }

    /**
     * Reemplaza los mensajes del contenedor.
     *
     * @param mensajes nueva lista de mensajes
     */
    public void setMensajes(List<Mensaje> mensajes) { this.mensajes = mensajes; }
}
