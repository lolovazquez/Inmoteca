package com.example.inmoteca.ui;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.inmoteca.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PropertyDetailActivity extends AppCompatActivity /*implements OnMapReadyCallback*/{

    private GoogleMap mMap;
    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvRoomsDetail;
    private TextView tvSizeDetail;
    private TextView tvCity;
    private ImageView ivPropertyDetailPhoto;
    private ImageView ivMap;
    private ImageView ivEmptyFav;
    private Object ViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail);

       /* SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/

        setupActionBar();

        findViews();

        ViewPager viewPager = findViewById(R.id.viewPager);
        ImageAdapter adapter = new ImageAdapter(this);
        viewPager.setAdapter(adapter);

        Intent intent = getIntent();
        Bundle bd = ((Intent) intent).getExtras();
        if(bd!=null) {
            String propertyTitle = (String) bd.get("título");
            tvTitle.setText(propertyTitle);

            String propertyDescription = (String) bd.get("descripción");
            tvDescription.setText(propertyDescription);

            int propertyRooms = (int) bd.get("habitaciones");
            tvRoomsDetail.setText(String.valueOf(propertyRooms)+" hab.");

            int propertySize = (int) bd.get("dimensión");
            tvSizeDetail.setText(String.valueOf(propertySize)+" metros cuadrados");

            String propertyCity = (String) bd.get("ciudad");
            tvCity.setText(propertyCity);
        }

        String idProperty = intent.getStringExtra("id");
        String propertyTitle = intent.getStringExtra("título");
        String propertyDescription = intent.getStringExtra("descripción");
        String propertyRooms = intent.getStringExtra("habitaciones");
        String propertyCity = intent.getStringExtra("ciudad");
        String propertyLocation = intent.getStringExtra("localización");

    }

    private void findViews() {
        tvTitle = findViewById(R.id.textViewTitle);
        tvDescription = findViewById(R.id.textViewDescription);
        tvRoomsDetail = findViewById(R.id.textViewRoomsDetail);
        ivPropertyDetailPhoto = findViewById(R.id.imageViewPropertyDetailPhoto);
        ivEmptyFav = findViewById(R.id.imageViewEmptyFav);
        tvSizeDetail = findViewById(R.id.textViewSize);
        tvCity = findViewById(R.id.textViewCity);
    }

    //Flecha atrás
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Propiedad");
        }
    }


 /*   @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        String propertyLocation = intent.getStringExtra("localización");

        String[] latlong =  propertyLocation.split(",");
        double latitude = Double.parseDouble(latlong[0]);
        double longitude = Double.parseDouble(latlong[1]);

        LatLng loc = new LatLng(latitude,longitude);

        mMap.addMarker(new MarkerOptions()
                .position(loc)
                .title("título")
                .snippet("triana.salesianos.edu")
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map))
        );

        // end for


        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 13));
    }*/
}


