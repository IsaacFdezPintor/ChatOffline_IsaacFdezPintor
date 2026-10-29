package es.iesfranciscodelosrios.chat;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

public class GestorXML {
    public void guardarUsuarios(ListaUsuarios usuarios) throws JAXBException {
        JAXBContext contexto = JAXBContext.newInstance(ListaUsuarios.class);
        Marshaller marshaller = contexto.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(usuarios, new File("usuarios.xml"));
    }

    public ListaUsuarios leerUsuarios() throws JAXBException {
        File archivo = new File("usuarios.xml");
        if (!archivo.exists() || archivo.length() == 0) {
            return new ListaUsuarios();
        }

        JAXBContext contexto = JAXBContext.newInstance(ListaUsuarios.class);
        Unmarshaller unmarshaller = contexto.createUnmarshaller();
        return (ListaUsuarios) unmarshaller.unmarshal(archivo);
    }

    public void guardarMensajes(ListaMensajes mensajes) throws JAXBException {
        JAXBContext contexto = JAXBContext.newInstance(ListaMensajes.class);
        Marshaller marshaller = contexto.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(mensajes, new File("mensajes.xml"));
    }

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
