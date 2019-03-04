package com.example.inmoteca.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inmoteca.R;
import com.example.inmoteca.fragments.EditProfileFragment;
import com.example.inmoteca.fragments.MyPropertiesFragment;
import com.example.inmoteca.fragments.PropertyFavFragment;
import com.example.inmoteca.fragments.PropertyListFragment;
import com.example.inmoteca.interfaces.PropertyInteractionListener;
import com.example.inmoteca.retrofit.generator.ServiceGenerator;
import com.example.inmoteca.retrofit.generator.TipoAutenticacion;
import com.example.inmoteca.retrofit.services.PropertyService;
import com.example.inmoteca.util.UtilToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PropertyInteractionListener {

    MenuItem itemFav;
    MenuItem itemOwns;
    MenuItem itemProfile;
    MenuItem itemLogout;
    MenuItem itemLogin;
    MenuItem itemProperties;
    Fragment f;
    private Toolbar toolbar;
    FloatingActionButton fab;
    TextView tvUserName;
    TextView tvUserEmail;
    ImageView ivFav;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViews();

        if (UtilToken.getToken(this)!=null){


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addProperty = new Intent(DashboardActivity.this, AddPropertyActivity.class);
                startActivity(addProperty);
            }
        });
        } else {
            fab.hide();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        //Declaro el acceso al elemento que quieroc ocultar
        itemFav = navigationView.getMenu().findItem(R.id.nav_fav);
        itemOwns = navigationView.getMenu().findItem(R.id.nav_owns);
        itemProfile = navigationView.getMenu().findItem(R.id.nav_settings);
        itemLogout = navigationView.getMenu().findItem(R.id.nav_logout);
        itemLogin = navigationView.getMenu().findItem(R.id.nav_login);
        itemProperties = navigationView.getMenu().findItem(R.id.nav_real_state);



        //Aqui se decide si se oculta o no, comprobando si hay token en el sharedPreferences

        if(UtilToken.getToken(this)==null){
            itemFav.setVisible(false);
            itemOwns.setVisible(false);
            itemProfile.setVisible(false);
            itemLogout.setVisible(false);
        }else{
            itemProperties.setVisible(true);
            itemLogin.setVisible(false);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentsContainer, new PropertyListFragment(), "property")
                .commit();


    }

    private void findViews() {
        tvUserName = findViewById(R.id.textViewNameUser);
        tvUserEmail = findViewById(R.id.textViewEmailLogueado);
        ivFav = findViewById(R.id.imageViewEmptyFav);
        fab = findViewById(R.id.fab);

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
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        String tag = "";

        f = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_real_state) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentsContainer, new PropertyListFragment())
                    .commit();
        } else if (id == R.id.nav_fav) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentsContainer, new PropertyFavFragment())
                    .commit();

        } else if (id == R.id.nav_owns) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentsContainer, new MyPropertiesFragment())
                    .commit();

        } else if (id == R.id.nav_settings) {
            f = new EditProfileFragment();
            tag = "profile";
//          fab.show();
//          toolbar.setTitle("Mostrar perfil");

        } else if (id == R.id.nav_logout) {
            SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            finish();
            startActivity(new Intent(DashboardActivity.this, DashboardActivity.class));

        } else if (id == R.id.nav_login) {
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if(f != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentsContainer, f, tag).commit();
            return true;
        }
        return true;

    }

        @Override
    public void verDetalles(String idProperty, String propertyTitle, String propertyDescription, int propertyRooms, int propertySize, String propertyCity) {
            UtilToken.setIdProperty(DashboardActivity.this, propertyTitle);
            Intent intentInfo = new Intent(DashboardActivity.this, PropertyDetailActivity.class);
            intentInfo.putExtra("id", idProperty);
            intentInfo.putExtra("título", propertyTitle);
            intentInfo.putExtra("descripción", propertyDescription);
            intentInfo.putExtra("habitaciones", propertyRooms);
            intentInfo.putExtra("dimensión", propertySize);
            intentInfo.putExtra("ciudad", propertyCity);

            startActivity(intentInfo);
        }

    @Override
    public void favOn(String id, final ImageView imagen) {
        PropertyService service = ServiceGenerator.createService(PropertyService.class,
                UtilToken.getToken(DashboardActivity.this), TipoAutenticacion.JWT);
        Call call = service.favOn(id);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    imagen.setImageResource(R.drawable.ic_filledfav);
                    Toast.makeText(DashboardActivity.this, "Añadido a favoritos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(DashboardActivity.this, "Fallo", Toast.LENGTH_SHORT);
                }


            }

            @Override
            public void onFailure(Call call, Throwable t) {
                // Toast
                Log.i("onFailure", "error en retrofit");
            }
        });
    }

    @Override
    public void favOff(String id, final ImageView imagen) {
        PropertyService service = ServiceGenerator.createService(PropertyService.class,
                UtilToken.getToken(DashboardActivity.this), TipoAutenticacion.JWT);
        Call call = service.favOff(id);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    imagen.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                    Toast.makeText(DashboardActivity.this, "Quitado de favoritos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DashboardActivity.this, "Fallo", Toast.LENGTH_SHORT);
                }


            }

            @Override
            public void onFailure(Call call, Throwable t) {
                // Toast
                Log.i("onFailure", "error en retrofit");
            }
        });
    }
}
