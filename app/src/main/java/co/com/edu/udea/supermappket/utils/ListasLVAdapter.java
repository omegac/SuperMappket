package co.com.edu.udea.supermappket.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import co.com.edu.udea.supermappket.R;
import co.com.edu.udea.supermappket.dto.LisLista;

/**
 * Created by go on 11/01/2015.
 */
public class ListasLVAdapter extends ArrayAdapter<LisLista> {

    protected static final String LOG_TAG = ListasLVAdapter.class.getSimpleName();

    private ArrayList<LisLista> listas;
    private int layoutResourceId;
    private Context context;

    public ListasLVAdapter(Context context, int layoutResourceId, ArrayList<LisLista> listas) {
        super(context, layoutResourceId, listas);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.listas = listas;
    }

    @Override
    public View getView(int posicion, View convertView, ViewGroup parent) {
        View row = convertView;
        LisListaHolder holder = null;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);

        holder = new LisListaHolder();
        holder.lista = listas.get(posicion);
        holder.botonRemover = (ImageButton)row.findViewById(R.id.btnRemovList);
        holder.botonRemover.setTag(holder.lista);

        holder.nombre = (TextView)row.findViewById(R.id.nombreListaTV);

        row.setTag(holder);

        setupItem(holder);
        return row;
    }

    private void setupItem(LisListaHolder holder) {
        holder.nombre.setText(holder.lista.getLisNombre());
    }

    public static class LisListaHolder {
        LisLista lista;
        TextView nombre;
        ImageButton botonRemover;
    }
/*
    private void setNameTextChangeListener(final LisListaHolder holder) {
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
}
