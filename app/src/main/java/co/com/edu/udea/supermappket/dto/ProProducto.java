package co.com.edu.udea.supermappket.dto;

/**
 * Created by Alan on 01/01/2015.
 */
public class ProProducto {

    /**
     * Identificador del producto
     */
    private int proId;
    /**
     * Nombre del producto
     */
    private String proNombre;
    /**
     * Precio del producto
     */
    private double proPrecio;
    /**
     * Descripcion del producto
     */
    private String proDescripcion;

    /**
     * Numero de productos ingresados en un carrito
     */
    private int cantidad;
    /**

     * Id de la categoria a la que pertenece el producto
     */
    private int idCategoria;
    /**
     * Nombre del icono asociado al producto
     */
    private String proIcono;

    public ProProducto() {
    }


    public ProProducto(int id, String nombre, double precio, String descripcion, int cantidad, int idCategoria, String icono) {
        super();
        this.proId = id;
        this.proNombre = nombre;
        this.proPrecio = precio;
        this.proDescripcion = descripcion;
        this.cantidad = cantidad;
        this.idCategoria = idCategoria;
        this.proIcono = icono;
    }

    public int getProId() {
        return this.proId;
    }

    public void setProId(int id){
        this.proId = id;
    }

    public String getProNombre() {
        return this.proNombre;
    }

    public void setProNombre(String nombre) {
        this.proNombre = nombre;
    }

    public double getProPrecio() {
        return this.proPrecio;
    }

    public void setProPrecio(double precio) {
        this.proPrecio = precio;
    }

    public String getProDescripcion() {
        return this.proDescripcion;
    }

    public void setProDescripcion(String descripcion) {
        this.proDescripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCategoria() {
        return this.idCategoria;
    }

    public void setCategoria(int categoria) {
        this.idCategoria  = categoria;
    }

    public String getProIcono() {
        return proIcono;
    }

    public void setProIcono(String proIcono) {
        this.proIcono = proIcono;
    }
}
