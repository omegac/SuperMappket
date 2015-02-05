package co.com.edu.udea.supermappket.dto;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Alan on 01/01/2015.
 */
public class LisLista {

    /**
     * Identificador de la lista
     */
    private int lisId;
    /**
     * Nombre de la lista
     */
    private String lisNombre;
    /**
     * Usuario al que pertenece la lista
     */
    private int idUsuario;
    /**
     * Lista de productos
     */
    private ArrayList<ProProducto> productos;

    public LisLista() {
    }


    public LisLista(int id, String nombre, int idUsuario, ArrayList<ProProducto> productos) {
        super();
        this.lisId = id;
        this.lisNombre = nombre;
        this.idUsuario = idUsuario;
        this.productos = productos;
    }

    public int getLisId() {
        return this.lisId;
    }

    public void setLisId(int id) {
        this.lisId = id;
    }

    public String getLisNombre() {
        return this.lisNombre;
    }

    public void setLisNombre(String nombre) {
        this.lisNombre = nombre;
    }

    public int getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public ArrayList<ProProducto> getProductos() {
        return this.productos;
    }

    public void setProductos(ArrayList<ProProducto> productoSet) {
        this.productos = productoSet;
    }
}
