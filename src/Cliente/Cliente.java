package Cliente;
/**
 * @author Lasso
 * @version 1.0
 */
 public class Cliente {
    int id_cliente; String cedula, nombre, telefono, correo, direccion;

    public Cliente(int id_cliente, String cedula, String nombre, String telefono, String correo, String direccion) {
        this.id_cliente = id_cliente;
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
    }

    /**
     * Returns the id of the cliente.
     *
     * @return the id of the cliente
     */
    public int getId_cliente() {
        return id_cliente;
    }

    /**
     * Sets the cliente id.
     *
     * @param id_cliente The cliente id
     */
    /**
     * Sets the id of the cliente.
     *
     * @param id_cliente The id
     */
    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    /**
     * Returns the cedula.
     *
     * @return the cedula
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * Sets the cedula.
     *
     * @param cedula the cedula
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    /**
     * Gets the nombre.
     *
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the nombre.
     *
     * @param nombre the nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Returns the telefono.
     *
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Sets the telefono.
     *
     * @param telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Returns the correo.
     *
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Sets the correo.
     *
     * @param correo the correo
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Returns the direccion of the current user.
     *
     * @return the dire
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Sets the direccion of the user.
     *
     * @param direccion
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
