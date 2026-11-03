package es.iesfranciscodelosrios.chat;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

/**
 * Representa a un usuario registrado en el chat.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Usuario {
    private String nombre;

    /**
     * Crea un usuario vacío para que JAXB pueda deserializarlo.
     */
    public Usuario() { }

    /**
     * Crea un usuario con el nombre indicado.
     *
     * @param nombre nombre del usuario
     */
    public Usuario(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el nombre del usuario.
     *
     * @return nombre del usuario
     */
    public String getNombre() { return nombre; }

    /**
     * Establece el nombre del usuario.
     *
     * @param nombre nombre que se asignará
     */
    public void setNombre(String nombre) { this.nombre = nombre; }
}
