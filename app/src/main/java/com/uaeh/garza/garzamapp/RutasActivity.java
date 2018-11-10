package com.uaeh.garza.garzamapp;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.uaeh.garza.garzamapp.Adapters.RutasAdapter;

import java.util.ArrayList;


public class RutasActivity extends AppCompatActivity {

    //widgets
    Toolbar toolbar;
    RecyclerView rv_rutas;


    //variables
    ArrayList<Integer> img_rutas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutas);

        toolbar = (Toolbar) findViewById(R.id.toolbar_detalle);
        rv_rutas = findViewById(R.id.rv_rutas);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        loadData();

        RutasAdapter adapter = new RutasAdapter(this, img_rutas);
        rv_rutas.setAdapter(adapter);
        rv_rutas.setLayoutManager(new LinearLayoutManager(this));



    }

    private void loadData()
    {
        img_rutas = new ArrayList<>();
        img_rutas.add(new Integer(R.drawable.ceuni_img));
        img_rutas.add(new Integer(R.drawable.ceuni_img));
        img_rutas.add(new Integer(R.drawable.ceuni_img));
        img_rutas.add(new Integer(R.drawable.ceuni_img));
        img_rutas.add(new Integer(R.drawable.ceuni_img));
        img_rutas.add(new Integer(R.drawable.ceuni_img));
        img_rutas.add(new Integer(R.drawable.ceuni_img));
        img_rutas.add(new Integer(R.drawable.ceuni_img));

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
