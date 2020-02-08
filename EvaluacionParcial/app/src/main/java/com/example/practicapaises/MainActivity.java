package com.example.practicapaises;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import WebServices.Asynchtask;
import WebServices.WebService;

public class MainActivity extends AppCompatActivity implements Asynchtask,AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws = new WebService("http://www.geognos.com/api/en/countries/info/all.json", datos,
                MainActivity.this, MainActivity.this);
        ws.execute("");
        getPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        getPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

        ListView list =(ListView)findViewById(R.id.lvnoticias);
        list.setOnItemClickListener(this);
    }

    @Override
    public void processFinish(String result) throws JSONException {


        ArrayList<Pais> Listapais = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(result);
        JSONObject jresults = jsonObject.getJSONObject("Results");
        Iterator<?> iterator = jresults.keys();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            JSONObject paisJson = jresults.getJSONObject(key);
            Pais pais=new Pais();
            pais.setNombre(paisJson.getString("Name"));
            JSONObject georectangle = paisJson.getJSONObject("GeoRectangle");
            pais.setNorte(georectangle.getString("North"));
            pais.setSur(georectangle.getString("South"));
            pais.setEste(georectangle.getString("East"));
            pais.setOeste(georectangle.getString("West"));

            JSONObject countryCodes = paisJson.getJSONObject("CountryCodes");
            pais.setUrl("http://www.geognos.com/api/en/countries/flag/"+countryCodes.getString("iso2")+".png");
            Listapais.add(pais);

        }
        //adaptar lisview
        AdaptadorPaises adaptadornoticias = new AdaptadorPaises(this, Listapais);

        ListView lstPaises = (ListView) findViewById(R.id.lvnoticias);

        lstPaises.setAdapter(adaptadornoticias);
    }
    private DownloadManager.Request request;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent miIntent=new Intent(MainActivity.this, MapsActivity.class);
        miIntent.putExtra("Norte",((Pais)adapterView.getItemAtPosition(position)).getNorte());
        miIntent.putExtra("Sur",((Pais)adapterView.getItemAtPosition(position)).getSur());
        miIntent.putExtra("Este",((Pais)adapterView.getItemAtPosition(position)).getEste());
        miIntent.putExtra("Oeste",((Pais)adapterView.getItemAtPosition(position)).getOeste());
        miIntent.putExtra("Url",((Pais)adapterView.getItemAtPosition(position)).getUrl());
        startActivity(miIntent);


    }

    public void getPermission(String permission) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!(checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED))
                ActivityCompat.requestPermissions(this, new String[]{permission}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            Toast.makeText(this.getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
        }
    }
}
