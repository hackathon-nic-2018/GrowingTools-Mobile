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
import android.widget.Spinner;
import android.widget.Toast;

import com.devstorm.growingtools.adapterss.SlidingMenuAdapter;
import com.devstorm.growingtools.fragments.BarFragment;
import com.devstorm.growingtools.fragments.LinearchartFragment;
import com.devstorm.growingtools.fragments.PieFragment;
import com.devstorm.growingtools.models.SlidingMenuModel;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;
import java.util.List;

public class PlusActivity extends AppCompatActivity {
    private Intent puente;
    private DrawerLayout drawerLayout;
    private ListView listView;
    private List<SlidingMenuModel> list;
    private SlidingMenuAdapter slidingMenuAdapter;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Spinner spinner1;
    private Spinner spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);
        getSupportActionBar().setTitle("Graficos");

        //Valores de las variables para las vistas
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayoutplus);
        listView = (ListView) findViewById(R.id.listviewPlus);
        //Action bar toggle
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Menues
        list = new ArrayList<SlidingMenuModel>();
        //Valores de los menues
        list.add(new SlidingMenuModel("Barras",R.drawable.barra));
        list.add(new SlidingMenuModel("Linea",R.drawable.linea));
        list.add(new SlidingMenuModel("Pastel",R.drawable.icons8_waning_crescent_48));
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
                        //Vista de Barras
                        addFragmentBar("Acetilenglicol","C");
                        drawerLayout.closeDrawer(listView);
                        break;
                    }
                    case 1 : {
                        //vista de lineas
                        //addFragmentLinear(spinner1.getSelectedItem().toString(),spinner2.getSelectedItem().toString() );
                        addFragmentLinear("Acetilenglicol","C");
                        drawerLayout.closeDrawer(listView);
                        break;
                    }

                    case 2:{
                        //vista de pie
                        //addFragmentLinear(spinner1.getSelectedItem().toString(),spinner2.getSelectedItem().toString() );
                        addFragmentPie("Acetilenglicol","C");
                        drawerLayout.closeDrawer(listView);
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

    private void addFragmentLinear(String producto,String producto2){
        LinearchartFragment linearchartFragment = new LinearchartFragment();
        linearchartFragment.GetObjects(producto,producto2,getApplicationContext());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.graficosContainer,linearchartFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null)
                .commit();
    }

    private void addFragmentBar(String producto,String producto2){
        BarFragment linearchartFragment = new BarFragment();
        linearchartFragment.GetObjects(producto,producto2,getApplicationContext());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.graficosContainer,linearchartFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null)
                .commit();
    }

    private void addFragmentPie(String producto,String producto2){
        PieFragment linearchartFragment = new PieFragment();
        linearchartFragment.GetObjects(producto,"",getApplicationContext());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.graficosContainer, linearchartFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null)
                .commit();
    }
}
