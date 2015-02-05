package co.com.edu.udea.supermappket.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

/**
 * Created by Alan on 01/01/2015.
 */
public class CarCarrito {

    /**
     * Identificador del carrito
     */
    private int carId;
    /**
     * Usuario al que pertenece el carrito
     */
    private int idUsuario;
    /**
     * Precio total del carrito
     */
    private double carPrecioTotal;
    /**
     * Fecha de compra del carrito
     */
    private Date carFecha;

    private ArrayList<ProProducto> productos;

    public CarCarrito() {
    }


    public CarCarrito(int id, int idUsuario, double precioTotal, Date fecha, ArrayList<ProProducto> productos) {
        super();
        this.carId = id;
        this.idUsuario = idUsuario;
        this.carPrecioTotal = precioTotal;
        this.carFecha = fecha;
        this.productos = productos;
    }

    public int getCarId() {
        return this.carId;
    }

    public void setCarId(int id) {
        this.carId = id;
    }

    public int getIdUsuario() {
        return this.idUsuario;
    }

    public void setUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public double getCarPrecioTotal() {
        return this.carPrecioTotal;
    }

    public void setCarPrecioTotal(double precioTotal) {
        this.carPrecioTotal = precioTotal;
    }

    public Date getCarFecha() {
        return this.carFecha;
    }

    public void setCarFecha(Date fecha) {
        this.carFecha = fecha;
    }

    public ArrayList<ProProducto> getProductos() {
        return this.productos;
    }

    public void setProductoSet(ArrayList<ProProducto> productos) {
        this.productos = productos;
    }
}
