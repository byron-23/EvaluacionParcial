package com.example.practicapaises;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdaptadorPaises extends ArrayAdapter<Pais> {
    public AdaptadorPaises(Context context, List<Pais> datos) {
        super(context, R.layout.ly_noticia, datos);

    }
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.ly_noticia, null);
        TextView lblTitulo = (TextView)item.findViewById(R.id.LblTitulo);
        lblTitulo.setText(getItem(position).getNombre());
        ImageView imageView = (ImageView)item.findViewById(R.id.imgNoticia);
                Glide.with(this.getContext())
                .load(getItem(position).getUrl())
                //.error(R.drawable.imgnotfound)
                .into(imageView);
        return(item);
    }


}
