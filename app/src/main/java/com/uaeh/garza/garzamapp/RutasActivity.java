package com.uaeh.garza.garzamapp;


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
        getSupportActionBar().setTitle(R.string.str_nav_group_title);


        int cod_ruta = getIntent().getIntExtra("cod_ruta",1);

        loadData(cod_ruta);

        RutasAdapter adapter = new RutasAdapter(this, img_rutas);
        rv_rutas.setAdapter(adapter);
        rv_rutas.setLayoutManager(new LinearLayoutManager(this));



    }

    private void loadData(int cod)
    {

        if(cod == 1) {
            //ciudad del conocimiento
            img_rutas = new ArrayList<>();
            img_rutas.add(new Integer(R.drawable.img_cccc));
            img_rutas.add(new Integer(R.drawable.img_c_cc));
            img_rutas.add(new Integer(R.drawable.img_c_v));
            img_rutas.add(new Integer(R.drawable.img_v_c));
            img_rutas.add(new Integer(R.drawable.img_v_c_dos));
            img_rutas.add(new Integer(R.drawable.img_cccp));
            img_rutas.add(new Integer(R.drawable.img_pcc));
            img_rutas.add(new Integer(R.drawable.img_cp));
            img_rutas.add(new Integer(R.drawable.img_ccc_a));

        } else if(cod == 2)
        {
            //ICEA_ICSa_ICSHu
            img_rutas = new ArrayList<>();
            img_rutas.add(new Integer(R.drawable.icea_tuzos));
            img_rutas.add(new Integer(R.drawable.tuzos_icea));
            img_rutas.add(new Integer(R.drawable.icea_ciudad));
            img_rutas.add(new Integer(R.drawable.ciudad_icea));
            img_rutas.add(new Integer(R.drawable.icea_central));
            img_rutas.add(new Integer(R.drawable.central_icea));
            img_rutas.add(new Integer(R.drawable.icea_ciudad_por_col));
            img_rutas.add(new Integer(R.drawable.ciudad_icea_por_col));
        }
        else if(cod == 3)
        {
            //ICSHu
            img_rutas = new ArrayList<>();
            img_rutas.add(new Integer(R.drawable.icea_tuzos));
            img_rutas.add(new Integer(R.drawable.tuzos_icea));
            img_rutas.add(new Integer(R.drawable.icea_central));
            img_rutas.add(new Integer(R.drawable.central_icea));
            img_rutas.add(new Integer(R.drawable.icea_ciudad_por_col));
            img_rutas.add(new Integer(R.drawable.ciudad_icea_por_col));
            img_rutas.add(new Integer(R.drawable.ciudad_icea));
            img_rutas.add(new Integer(R.drawable.icea_ciudad));
        }else if(cod == 4)
        {
            //ICSa
            img_rutas = new ArrayList<>();
            img_rutas.add(new Integer(R.drawable.icsa_ciudad));
            img_rutas.add(new Integer(R.drawable.ciudad_ics));
            img_rutas.add(new Integer(R.drawable.icea_tuzos));
            img_rutas.add(new Integer(R.drawable.tuzos_icea));
            img_rutas.add(new Integer(R.drawable.icea_ciudad));
            img_rutas.add(new Integer(R.drawable.ciudad_icea));
            img_rutas.add(new Integer(R.drawable.icea_central));
            img_rutas.add(new Integer(R.drawable.central_icea));
            img_rutas.add(new Integer(R.drawable.icea_ciudad));
            img_rutas.add(new Integer(R.drawable.ciudad_icea_por_col));
        }else if(cod == 5)
        {
            //IDA
            img_rutas = new ArrayList<>();
            img_rutas.add(new Integer(R.drawable.pres_ida));
            img_rutas.add(new Integer(R.drawable.ida_pres));
            img_rutas.add(new Integer(R.drawable.pres_m_ida));

        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
