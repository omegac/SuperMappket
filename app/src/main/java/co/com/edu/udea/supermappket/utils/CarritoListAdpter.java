package co.com.edu.udea.supermappket.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.com.edu.udea.supermappket.R;
import co.com.edu.udea.supermappket.dto.ProProducto;

/**
 * Created by Alan on 10/01/2015.
 */
public class CarritoListAdpter extends ArrayAdapter<ProProducto> {

    protected static final String LOG_TAG = CarritoListAdpter.class.getSimpleName();

    private ArrayList<ProProducto> productos;
    private int layoutResourceId;
    private Context context;

    public CarritoListAdpter(Context context, int layoutResourceId, ArrayList<ProProducto> productos) {
        super(context, layoutResourceId, productos);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.productos = productos;
    }

    @Override
    public View getView(int posicion, View convertView, ViewGroup parent) {
        View row = convertView;
        ProProductoHolder holder = null;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);

        holder = new ProProductoHolder();
        holder.producto = productos.get(posicion);
        holder.imagen = (ImageView)row.findViewById(R.id.carItemIV);
        holder.botonRemover = (ImageButton)row.findViewById(R.id.btnRemovProd);
        holder.botonRemover.setTag(holder.producto);

        holder.nombre = (TextView)row.findViewById(R.id.carNombreProd);
        holder.cantidad = (TextView)row.findViewById(R.id.carCantProd);
        //setValueTextListeners(holder);

        row.setTag(holder);

        setupItem(holder);
        return row;
    }

    private void setupItem(ProProductoHolder holder) {
        Resources resources = context.getResources();
        String nombreIcono = holder.producto.getProIcono();
        int resId = resources.getIdentifier(nombreIcono, "drawable", context.getPackageName());

        holder.imagen.setImageResource(resId);
        holder.nombre.setText(holder.producto.getProNombre());
        holder.cantidad.setText(" $" + String.format("%.2f",holder.producto.getProPrecio()) +
                " (x" + String.valueOf(holder.producto.getCantidad()) + ")");
    }

    public static class ProProductoHolder {
        ProProducto producto;
        ImageView imagen;
        TextView nombre;
        TextView cantidad;
        ImageButton botonRemover;
    }
/*
    private void setNameTextChangeListener(final ProProductoHolder holder) {
        holder.nombre.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                holder.producto.setProNombre(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }*/

    /*private void setValueTextListeners(final ProProductoHolder holder) {
        holder.cantidad.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    holder.producto.setCantidad(Integer.parseInt(s.toString()));
                }catch (NumberFormatException e) {
                    Log.e(LOG_TAG, "Error cargando la cantidad: " + s.toString());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }*/
}
