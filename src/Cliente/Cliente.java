package Cliente;

/**
 * La clase `Cliente` representa la información de un cliente.
 *
 * @author Lasso
 */
public class Cliente {
    /** El ID único del cliente. */
    int id_cliente;
    /** La cédula del cliente. */
    String cedula;
    /** El nombre del cliente. */
    String nombre;
    /** El número de teléfono del cliente. */
    String telefono;
    /** La dirección de correo electrónico del cliente. */
    String correo;
    /** La dirección física del cliente. */
    String direccion;

    /**
     * Constructor para crear un objeto `Cliente` con todos los detalles.
     *
     * @param id_cliente El ID único del cliente.
     * @param cedula La cédula del cliente.
     * @param nombre El nombre del cliente.
     * @param telefono El número de teléfono del cliente.
     * @param correo La dirección de correo electrónico del cliente.
     * @param direccion La dirección física del cliente.
     */
    public Cliente(int id_cliente, String cedula, String nombre, String telefono, String correo, String direccion) {
        this.id_cliente = id_cliente;
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
    }

    /**
     * Constructor vacío para crear un objeto `Cliente` sin detalles iniciales.
     */
    public Cliente() {

    }

    /**
     * Obtiene el ID del cliente.
     *
     * @return El ID del cliente.
     */
    public int getId_cliente() {
        return id_cliente;
    }

    /**
     * Establece el ID del cliente.
     *
     * @param id_cliente El nuevo ID del cliente.
     */
    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    /**
     * Obtiene la cédula del cliente.
     *
     * @return La cédula del cliente.
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * Establece la cédula del cliente.
     *
     * @param cedula La nueva cédula del cliente.
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    /**
     * Obtiene el nombre del cliente.
     *
     * @return El nombre del cliente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente.
     *
     * @param nombre El nuevo nombre del cliente.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el número de teléfono del cliente.
     *
     * @return El número de teléfono del cliente.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono del cliente.
     *
     * @param telefono El nuevo número de teléfono del cliente.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la dirección de correo electrónico del cliente.
     *
     * @return La dirección de correo electrónico del cliente.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece la dirección de correo electrónico del cliente.
     *
     * @param correo La nueva dirección de correo electrónico del cliente.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene la dirección física del cliente.
     *
     * @return La dirección física del cliente.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección física del cliente.
     *
     * @param direccion La nueva dirección física del cliente.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}