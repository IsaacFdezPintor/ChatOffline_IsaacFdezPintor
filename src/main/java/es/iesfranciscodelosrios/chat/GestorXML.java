package es.iesfranciscodelosrios.chat;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

/**
 * Lee y escribe en XML las listas de usuarios y mensajes mediante JAXB.
 */
public class GestorXML {
    /**
     * Guarda la lista de usuarios en el archivo {@code usuarios.xml}.
     *
     * @param usuarios lista de usuarios que se desea persistir
     * @throws JAXBException si JAXB no puede convertir la lista a XML
     */
    public void guardarUsuarios(ListaUsuarios usuarios) throws JAXBException {
        JAXBContext contexto = JAXBContext.newInstance(ListaUsuarios.class);
        Marshaller marshaller = contexto.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(usuarios, new File("usuarios.xml"));
    }

    /**
     * Lee los usuarios del archivo {@code usuarios.xml}.
     *
     * @return usuarios leídos, o una lista vacía si el archivo no existe o está vacío
     * @throws JAXBException si JAXB no puede convertir el XML a una lista de usuarios
     */
    public ListaUsuarios leerUsuarios() throws JAXBException {
        File archivo = new File("usuarios.xml");
        if (!archivo.exists() || archivo.length() == 0) {
            return new ListaUsuarios();
        }

        JAXBContext contexto = JAXBContext.newInstance(ListaUsuarios.class);
        Unmarshaller unmarshaller = contexto.createUnmarshaller();
        return (ListaUsuarios) unmarshaller.unmarshal(archivo);
    }

    /**
     * Guarda la lista de mensajes en el archivo {@code mensajes.xml}.
     *
     * @param mensajes lista de mensajes que se desea persistir
     * @throws JAXBException si JAXB no puede convertir la lista a XML
     */
    public void guardarMensajes(ListaMensajes mensajes) throws JAXBException {
        JAXBContext contexto = JAXBContext.newInstance(ListaMensajes.class);
        Marshaller marshaller = contexto.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(mensajes, new File("mensajes.xml"));
    }

    /**
     * Lee los mensajes del archivo {@code mensajes.xml}.
     *
     * @return mensajes leídos, o una lista vacía si el archivo no existe o está vacío
     * @throws JAXBException si JAXB no puede convertir el XML a una lista de mensajes
     */
    public ListaMensajes leerMensajes() throws JAXBException {
        File archivo = new File("mensajes.xml");
        if (!archivo.exists() || archivo.length() == 0) {
            return new ListaMensajes();
        }
        JAXBContext contexto = JAXBContext.newInstance(ListaMensajes.class);
        Unmarshaller unmarshaller = contexto.createUnmarshaller();
        return (ListaMensajes) unmarshaller.unmarshal(archivo);
    }
}
