package es.iesfranciscodelosrios.chat;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Contenedor JAXB de los usuarios registrados en el chat.
 */
@XmlRootElement(name = "usuarios")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListaUsuarios {
    @XmlElement(name = "usuario")
    private List<Usuario> usuarios = new ArrayList<>();
    /**
     * Crea un contenedor vacío, necesario también para la deserialización de JAXB.
     */
    public ListaUsuarios() { }

    /**
     * Devuelve los usuarios del contenedor.
     *
     * @return lista de usuarios
     */
    public List<Usuario> getUsuarios() { return usuarios; }

    /**
     * Reemplaza los usuarios del contenedor.
     *
     * @param usuario nueva lista de usuarios
     */
    public void setUsuarios(List<Usuario> usuario) { this.usuarios = usuarios;
    }
}
