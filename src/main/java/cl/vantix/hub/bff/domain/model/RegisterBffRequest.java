package cl.vantix.hub.bff.domain.model;
public record RegisterBffRequest(
    String nombre, String apellidoPaterno, String apellidoMaterno,
    String email, String passwordEncrypted, String telefono,
    String empresaNombre, String empresaRut, String empresaDireccion,
    String empresaTelefono, String empresaEmail
) {}
