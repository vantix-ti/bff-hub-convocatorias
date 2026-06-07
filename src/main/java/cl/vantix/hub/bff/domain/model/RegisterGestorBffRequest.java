package cl.vantix.hub.bff.domain.model;
public record RegisterGestorBffRequest(
    String nombre, String apellidoPaterno, String apellidoMaterno,
    String email, String passwordEncrypted, String telefono,
    String instNombre, String instRut, String instDireccion,
    String instTelefono, String instEmail
) {}
