package com.example.practicapaises;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

Double  nor;
    Double sur;
    Double este;
    Double oeste;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        url = getIntent().getExtras().getString("Url");
        nor = Double.valueOf(getIntent().getExtras().getString("Norte"));
        sur = Double.valueOf(getIntent().getExtras().getString("Sur"));
        este = Double.valueOf(getIntent().getExtras().getString("Este"));
        oeste = Double.valueOf(getIntent().getExtras().getString("Oeste"));

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ImageView imageView = (ImageView)findViewById(R.id.imabandera);
        Glide.with(this.getApplicationContext())
                .load(url)
                .into(imageView);

    }
    GoogleMap mapa;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;

        mapa.getUiSettings().setZoomControlsEnabled(true);
        CameraUpdate camUpd1 = CameraUpdateFactory.newLatLngZoom(new LatLng(nor,oeste), 5);
        mapa.moveCamera(camUpd1);
        PolylineOptions lineas = new PolylineOptions()
                .add(new LatLng(nor,oeste))
                .add(new LatLng(nor,este))
                .add(new LatLng(sur, este))
                .add(new LatLng(sur, oeste))
                .add(new LatLng(nor,oeste));
        lineas.width(8);
        lineas.color(Color.RED);
        mapa.addPolyline(lineas);


            }


}
