package co.com.edu.udea.supermappket.dto;

import java.util.ArrayList;

/**
 * Created by Alan on 01/01/2015.
 */
public class CatCategoria {

    /**
     * Identificador de la categoria
     */
    private int catId;
    /**
     * Nombre de la categoria
     */
    private String catNombre;
    /**
     * Descripcion de la categoria
     */
    private String catDescripcion;
    /**
     * Productos asociados a la categoria
     */
    private ArrayList<ProProducto> listaProductos = new ArrayList<ProProducto>();

    public CatCategoria() {
    }


    public CatCategoria(int id, String nombre, String descripcion, ArrayList<ProProducto> productos) {
        super();
        this.catId = id;
        this.catNombre = nombre;
        this.catDescripcion = descripcion;
        this.listaProductos = productos;
    }

    public int getCatId() {
        return this.catId;
    }

    public void setCatId(int id) {
        this.catId = id;
    }

    public String getcatNombre() {
        return this.catNombre;
    }

    public void setCatNombre(String nombre) {
        this.catNombre = nombre;
    }

    public String getCatDescripcion() {
        return this.catDescripcion;
    }

    public void setCatDescripcion(String descripcion) {
        this.catDescripcion = descripcion;
    }

    public ArrayList<ProProducto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(ArrayList<ProProducto> listaProductos) {
        this.listaProductos = listaProductos;
    }
}
