package co.com.edu.udea.supermappket.dto;

import java.util.Set;

/**
 * Clase DTO para transportar la informacion del usuario
 * Created by Alan on 01/01/2015.
 */
public class UsuUsuario {

    /**
     * Identificador de la categoria
     */
    private int usId;
    /**
     * Nombre que identifica al usuario
     */
    private String usUser;
    /**
     * Contrasena con la cual ingresa al sistema el usuario
     */
    private String usPassword;
    /**
     *
     */
    private Set<CarCarrito> carCarritoSet;

    public UsuUsuario() {
    }


    public UsuUsuario(int id, String user,String password) {
        super();
        this.usId = id;
        this.usUser = user;
        this.usPassword = password;
    }

    public int getUsId() {
        return this.usId;
    }

    public void setUsId(int id){
        this.usId = id;
    }

    public String getUsUser() {
        return this.usUser;
    }

    public void setUsUser(String usUser) {
        this.usUser = usUser;
    }

    public String getUsPassword() {
        return this.usPassword;
    }

    public void setUsPassword(String usPassword) {
        this.usPassword = usPassword;
    }

    public Set<CarCarrito> getCarCarritoSet() {
        return carCarritoSet;
    }

    public void setCarCarritoSet(Set<CarCarrito> carCarritoSet) {
        this.carCarritoSet = carCarritoSet;
    }
}
