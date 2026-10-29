# Chat Offline

**2.º DAM · Acceso a Datos · UD1**

Consola de mensajería offline que persiste usuarios y mensajes en XML mediante JAXB.

## Requisitos y ejecución

- JDK 17 
- Maven

Desde la carpeta del proyecto:

```bash
mvn compile exec:java
```

Los archivos `usuarios.xml` y `mensajes.xml` se crean automáticamente en el directorio desde el que se ejecuta la aplicación.

## Funcionalidades

- Registro de usuarios y acceso solo para usuarios registrados.
- Envío de mensajes persistentes entre usuarios registrados; no hay opción para borrarlos.
- Consulta de conversaciones en ambos sentidos, ordenadas por fecha.
- Estadísticas con Streams: total de mensajes por emisor.
- Exportación de conversaciones a texto UTF-8.
