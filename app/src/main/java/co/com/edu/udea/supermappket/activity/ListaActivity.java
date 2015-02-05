package co.com.edu.udea.supermappket.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import co.com.edu.udea.supermappket.R;
import co.com.edu.udea.supermappket.dto.LisLista;
import co.com.edu.udea.supermappket.utils.ListasLVAdapter;

/**
 * Created by Alan on 11/01/2015.
 */
public class ListaActivity extends FragmentActivity {

    private ListasLVAdapter listAdpter;
    private static TextView listVacias;
    private static ArrayList<LisLista> misListas = new ArrayList<LisLista>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        setTitle(getResources().getString(R.string.title_activity_lista));
        getActionBar().setIcon(R.drawable.list);

        Button btnCrearLista = (Button) findViewById(R.id.insrtListBTN);
        listVacias = (TextView) findViewById(R.id.noListasTV);

        misListas = MainActivity.manager.obtenerListas(CategoriasActivity.identificadorUser);
        veriricarNoListasTV();

        btnCrearLista.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                //Aquí va redireccion a intent de crear lista
            }
        });

        setupListViewListAdapter();
    }

    private void setupListViewListAdapter() {
        listAdpter = new ListasLVAdapter(ListaActivity.this, R.layout.listas_item, misListas);
        ListView listaListas = (ListView)findViewById(R.id.misListasLV);

        listaListas.setAdapter(listAdpter);
    }

    public void removListOnClickHandler(final View v) {
        final LisLista listaRemover = (LisLista) v.getTag();
        new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK)
                .setTitle(R.string.titulo_dialog_lista)
                .setMessage("Desea eliminar la lista: " + listaRemover.getLisNombre() + "?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(R.string.aceptar_dialog_lista, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        removerLista(v, listaRemover);
                    }})
                .setNegativeButton(R.string.cancelar_dialog, null).show();
    }

    public void removerLista(View v, LisLista listaRemover){
        listAdpter.remove(listaRemover);
        //MainActivity.manager.
        veriricarNoListasTV();
        Toast.makeText(getBaseContext(), "Lista '" + listaRemover.getLisNombre() +
                "' eliminada.", Toast.LENGTH_SHORT).show();
    }

    private void veriricarNoListasTV(){
        if (misListas.isEmpty()){
            listVacias.setVisibility(View.VISIBLE);
        } else listVacias.setVisibility(View.INVISIBLE);
    }
}
