package com.devstorm.growingtools;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.devstorm.growingtools.adapterss.SlidingMenuAdapter;
import com.devstorm.growingtools.models.SlidingMenuModel;

import java.util.ArrayList;
import java.util.List;

public class LauncherActivity extends AppCompatActivity {

    //Variables para control de vistas
    private Intent puente;
    private DrawerLayout drawerLayout;
    private ListView listView;
    private List<SlidingMenuModel> list;
    private SlidingMenuAdapter slidingMenuAdapter;
    //control de cierre de sesion desde el shared preferences
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        //Valores de las variables para las vistas
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayoutlauncher);
        listView = (ListView) findViewById(R.id.listviewlauncher);
        //Action bar toggle
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Menues
        list = new ArrayList<SlidingMenuModel>();
        //Valores de los menues
        list.add(new SlidingMenuModel("Inventario",R.drawable.inventory));
        list.add(new SlidingMenuModel("Resumen",R.drawable.proyecciones));
        //Rellenando el adaptador
        slidingMenuAdapter =  new SlidingMenuAdapter(list,getApplicationContext(),R.layout.listviewlayoutmodel);
        //Llamando al list view para setear el adapter
        listView.setAdapter(slidingMenuAdapter);
        drawerLayout.closeDrawer(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){

                    case 0 :{
                        //Vista de inventario
                        puente = new Intent(getApplicationContext(),InventoryActivity.class);
                        startActivity(puente);
                        break;
                    }
                    case 1 : {
                        puente = new Intent(getApplicationContext(),ResultadoActivity.class);
                        startActivity(puente);
                        break;
                    }

                }
            }
        });

        actionBarDrawerToggle  = new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        //Listener drawe layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_launcher,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){

        }else{
                switch (item.getItemId()){
                    case R.id.closeSession :{
                       //Aca borro del sharedPreferences los datos guardados
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("Empresa","");
                        editor.putString("User","");
                        editor.commit();
                        Toast.makeText(getApplicationContext(),"Sesion finalizada",Toast.LENGTH_LONG).show();
                        puente = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(puente);
                    }
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    private void addFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.graficosContainer,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null)
                .commit();

    }
}
