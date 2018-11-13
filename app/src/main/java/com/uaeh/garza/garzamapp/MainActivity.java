package com.uaeh.garza.garzamapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.uaeh.garza.garzamapp.Dialogs.DialogAcercaDe;
import com.uaeh.garza.garzamapp.Fragments.FlotillasFragment;
import com.uaeh.garza.garzamapp.Fragments.GarzaMappFragment;
import com.uaeh.garza.garzamapp.Fragments.ObjetivosFragment;
import com.uaeh.garza.garzamapp.Fragments.PoliticasFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    //widgets

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //configurar el ViewPager y el TabLayout

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // configurar  el ViewPager con sectionAdapter .
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //mostrar dialog de acerca_de
        if (id == R.id.action_about) {

            DialogAcercaDe dialog = new DialogAcercaDe();
            dialog.show(getSupportFragmentManager(),"Acerca_de");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent i = new Intent(MainActivity.this,RutasActivity.class);

        if (id == R.id.nav_camp) {
            // CC-campestre

            i.putExtra("cod_ruta",1);
            startActivity(i);

        }else if(id == R.id.nav_pal)
        {
            //cc_pal
            i.putExtra("cod_ruta",1);
            startActivity(i);
        }else if(id == R.id.nav_vill)
        {
            //cc_villas
            i.putExtra("cod_ruta",1);
            startActivity(i);
        }else if(id == R.id.nav_ics)
        {
            //cc_icsa
            i.putExtra("cod_ruta",4);
            startActivity(i);
        }else if(id == R.id.nav_icshu)
        {
            //cc_icshu
            i.putExtra("cod_ruta",3);
            startActivity(i);
        }else if(id == R.id.nav_icea_col)
        {
            //cc_pal
            i.putExtra("cod_ruta",1);
            startActivity(i);
        }else if(id == R.id.nav_icea)
        {
            //cc_pal
            i.putExtra("cod_ruta",1);
            startActivity(i);
        }else if(id == R.id.nav_ceuni_cent)
        {
            //ceuni-central
            i.putExtra("cod_ruta",1);
            startActivity(i);
        }else if(id == R.id.nav_icea_tu)
        {
            //icea-tuzos
            i.putExtra("cod_ruta",2);
            startActivity(i);
        }
        else if(id == R.id.nav_icea_cen)
        {
            //icea-central
            i.putExtra("cod_ruta",2);
            startActivity(i);
        }else if(id == R.id.nav_pres_ida)
        {
            //presidencia -ida
            i.putExtra("cod_ruta",5);
            startActivity(i);
        }else if(id == R.id.nav_cc_ceuni_plaz)
        {
            //CC-CEUNI-Plaza Juarez
            i.putExtra("cod_ruta",1);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // logica del TabLayout
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position)
            {
                case 0:
                    ObjetivosFragment obj = new ObjetivosFragment();
                    return obj;
                case 1:
                    GarzaMappFragment garzamap = new GarzaMappFragment();
                    return garzamap;
                case 2:
                    PoliticasFragment politicas = new PoliticasFragment();
                    return politicas;
                case 3:
                    FlotillasFragment flotillas = new FlotillasFragment();
                    return  flotillas;

                default:
                    return null;
            }


        }

        @Override
        public int getCount() {
            // 4 paginas a mostrar
            return 3;
        }
    }


}
