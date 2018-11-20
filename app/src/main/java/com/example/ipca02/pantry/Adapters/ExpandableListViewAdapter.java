package com.example.ipca02.pantry.Adapters;

import java.util.HashMap;
import java.util.List;

import com.example.ipca02.pantry.Database.DatabaseContract;
import com.example.ipca02.pantry.Database.DatabaseHelper;
import com.example.ipca02.pantry.Models.Categorias;
import com.example.ipca02.pantry.Models.Produto;
import com.example.ipca02.pantry.R;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private List<Categorias> listCategorias;
    private HashMap<Categorias, List<Produto>> listProducts;
    private LayoutInflater inflater;
    SQLiteOpenHelper db_h;
    SQLiteDatabase db;

    public ExpandableListViewAdapter(Context context, List<Categorias> listCategorias, HashMap<Categorias, List<Produto>> listFilms) {
        this.listCategorias = listCategorias;
        this.listProducts = listFilms;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        db_h = new DatabaseHelper(context);
        db = db_h.getWritableDatabase();
    }

    public ExpandableListViewAdapter() {

    }

    @Override
    public int getGroupCount() {
        return listCategorias.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listProducts.get(listCategorias.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listCategorias.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listProducts.get(listCategorias.get(groupPosition)).get(childPosition).getNome_produto();
    }

    public Object getChild_id_supermercado(int groupPosition, int childPosition) {
        return listProducts.get(listCategorias.get(groupPosition)).get(childPosition).getSupermercado_id();
    }

    public Object getChild_ID(int groupPosition, int childPosition) {
        return listProducts.get(listCategorias.get(groupPosition)).get(childPosition).getId_produto();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderGroup holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.parent_layout, null);
            holder = new ViewHolderGroup();
            convertView.setTag(holder);

            holder.tvGroup = (TextView) convertView.findViewById(R.id.parent_layout);
        } else {
            holder = (ViewHolderGroup) convertView.getTag();
        }

        holder.tvGroup.setText(listCategorias.get(groupPosition).getCategorias_nome());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ViewHolderItem holder;
        String val = getChild(groupPosition, childPosition).toString();
        String id = String.valueOf(getChild_ID(groupPosition, childPosition));
        String id_supermercado = String.valueOf(getChild_id_supermercado(groupPosition, childPosition));

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_layout, null);
            holder = new ViewHolderItem();
            convertView.setTag(holder);

            holder.tvItem = (TextView) convertView.findViewById(R.id.child_layout);
            holder.tvID = (TextView) convertView.findViewById(R.id.child_layout_id);
            holder.tvID_supermercado = (TextView) convertView.findViewById(R.id.child_layout_supermercado);
        } else {
            holder = (ViewHolderItem) convertView.getTag();
        }
        holder.tvItem.setText(val);
        holder.tvID.setText(id);
        String tv_nome_supermercado = selectAllSupermercados(id_supermercado);
        holder.tvID_supermercado.setText(tv_nome_supermercado);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ViewHolderGroup {
        TextView tvGroup;
    }

    class ViewHolderItem {
        TextView tvItem;
        TextView tvID;
        TextView tvID_supermercado;
    }

    public String selectAllSupermercados(String id_supermecado) {

        String supermercado = null;
        Cursor cursor;
        String sql = "SELECT *FROM " + DatabaseContract.Supermercado.TB_NAME_SUPERMERCADO + " WHERE " + DatabaseContract.Supermercado.COL_SUPERMERCADO_ID + " = " + id_supermecado + ";";
        cursor = db.rawQuery(sql, null);

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();
            do {

                supermercado = cursor.getString(cursor.getColumnIndex(DatabaseContract.Supermercado.COL_SUPERMERCADO_NOME));
                //categorias.setCategorias_id(cursor.getInt(cursor.getColumnIndex(DatabaseContract.Categorias.COL_CATEGORIA_ID)));
            } while (cursor.moveToNext());
        }
        return supermercado;
    }
}
