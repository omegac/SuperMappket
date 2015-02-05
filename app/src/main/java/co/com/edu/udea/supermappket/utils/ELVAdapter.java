package co.com.edu.udea.supermappket.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import co.com.edu.udea.supermappket.R;
import co.com.edu.udea.supermappket.dto.CatCategoria;
import co.com.edu.udea.supermappket.dto.ProProducto;

/**
 * Created by Alan on 03/01/2015.
 */
public class ELVAdapter extends BaseExpandableListAdapter{

    private Context context;
    private ArrayList<CatCategoria> listaCategorias;
    private ArrayList<CatCategoria> listaOriginal;

    public ELVAdapter(Context context, ArrayList<CatCategoria> categorias) {
        this.context = context;
        this.listaCategorias = new ArrayList<CatCategoria>();
        this.listaCategorias.addAll(categorias);
        this.listaOriginal = new ArrayList<CatCategoria>();
        this.listaOriginal.addAll(categorias);
    }

    /**
     * Gets the number of groups.
     *
     * @return the number of groups
     */
    @Override
    public int getGroupCount() {
        return listaCategorias.size();
    }

    /**
     * Gets the number of children in a specified group.
     *
     * @param groupPosition the position of the group for which the children
     *                      count should be returned
     * @return the children count in the specified group
     */
    @Override
    public int getChildrenCount(int groupPosition) {

        ArrayList<ProProducto> listProductos = listaCategorias.get(groupPosition).getListaProductos();
        return listProductos.size();
    }

    /**
     * Gets the data associated with the given group.
     *
     * @param groupPosition the position of the group
     * @return the data child for the specified group
     */
    @Override
    public Object getGroup(int groupPosition) {
        return listaCategorias.get(groupPosition);
    }

    /**
     * Gets the data associated with the given child within the given group.
     *
     * @param groupPosition the position of the group that the child resides in
     * @param childPosition the position of the child with respect to other
     *                      children in the group
     * @return the data of the child
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {

        ArrayList<ProProducto> listaProductos = listaCategorias.get(groupPosition).getListaProductos();
        return listaProductos.get(childPosition);
    }

    /**
     * Gets the ID for the group at the given position. This group ID must be
     * unique across groups. The combined ID (see
     * {@link #getCombinedGroupId(long)}) must be unique across ALL items
     * (groups and all children).
     *
     * @param groupPosition the position of the group for which the ID is wanted
     * @return the ID associated with the group
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * Gets the ID for the given child within the given group. This ID must be
     * unique across all children within the group. The combined ID (see
     * {@link #getCombinedChildId(long, long)}) must be unique across ALL items
     * (groups and all children).
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child within the group for which
     *                      the ID is wanted
     * @return the ID associated with the child
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * Indicates whether the child and group IDs are stable across changes to the
     * underlying data.
     *
     * @return whether or not the same ID always refers to the same object
     * @see android.widget.Adapter#hasStableIds()
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * Gets a View that displays the given group. This View is only for the
     * group--the Views for the group's children will be fetched using
     * {@link #getChildView(int, int, boolean, android.view.View, android.view.ViewGroup)}.
     *
     * @param groupPosition the position of the group for which the View is
     *                      returned
     * @param isExpanded    whether the group is expanded or collapsed
     * @param convertView   the old view to reuse, if possible. You should check
     *                      that this view is non-null and of an appropriate type before
     *                      using. If it is not possible to convert this view to display
     *                      the correct data, this method can create a new view. It is not
     *                      guaranteed that the convertView will have been previously
     *                      created by
     *                      {@link #getGroupView(int, boolean, android.view.View, android.view.ViewGroup)}.
     * @param parent        the parent that this view will eventually be attached to
     * @return the View corresponding to the group at the specified position
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        CatCategoria categoria = (CatCategoria) getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.fila_grupo, null);
        }

        TextView nombreCategoria = (TextView) convertView.findViewById(R.id.nombreGrupoTV);
        nombreCategoria.setText(categoria.getcatNombre());
        return convertView;
    }

    /**
     * Gets a View that displays the data for the given child within the given
     * group.
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child (for which the View is
     *                      returned) within the group
     * @param isLastChild   Whether the child is the last child within the group
     * @param convertView   the old view to reuse, if possible. You should check
     *                      that this view is non-null and of an appropriate type before
     *                      using. If it is not possible to convert this view to display
     *                      the correct data, this method can create a new view. It is not
     *                      guaranteed that the convertView will have been previously
     *                      created by
     *                      {@link #getChildView(int, int, boolean, android.view.View, android.view.ViewGroup)}.
     * @param parent        the parent that this view will eventually be attached to
     * @return the View corresponding to the child at the specified position
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ProProducto producto = (ProProducto) getChild(groupPosition,childPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.fila_hijo, null);
        }

        ImageView imagen = (ImageView) convertView.findViewById(R.id.childIV);
        TextView nombre = (TextView) convertView.findViewById(R.id.nombreTV);
        TextView precio = (TextView) convertView.findViewById(R.id.precioTV);
        Resources resources = context.getResources();
        String nombreIcono = producto.getProIcono();
        int resId = resources.getIdentifier(nombreIcono, "drawable", context.getPackageName());

        imagen.setImageResource(resId);
        nombre.setText(producto.getProNombre());
        precio.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(producto.getProPrecio()));

        // set the ClickListener to handle the click event on child item
        /*convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //CategoriasActivity.show();

            }
        });*/
        return convertView;
    }

    /**
     * Whether the child at the specified position is selectable.
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child within the group
     * @return whether the child is selectable.
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void filtrarInfo(String query){

        query = query.toLowerCase();
        Log.i("ELVAdapter", String.valueOf(listaCategorias.size()));
        listaCategorias.clear();

        if(query.isEmpty()){
            listaCategorias.addAll(listaOriginal);
        }else {
            for(CatCategoria categoria: listaOriginal){
                ArrayList<ProProducto> listaProductos = categoria.getListaProductos();
                ArrayList<ProProducto> nuevaLista = new ArrayList<ProProducto>();
                for(ProProducto producto: listaProductos){
                    if(producto.getProNombre().toLowerCase().contains(query))
                        nuevaLista.add(producto);
                }
                if(nuevaLista.size()>0){
                    CatCategoria nCategoria = new CatCategoria(categoria.getCatId(), categoria.getcatNombre(),
                            categoria.getCatDescripcion(), nuevaLista);
                    listaCategorias.add(nCategoria);
                }
            }
        }

        Log.i("ELVAdapter", String.valueOf(listaCategorias.size()));
        notifyDataSetChanged();
    }
}
