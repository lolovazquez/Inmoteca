package com.example.inmoteca.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.inmoteca.R;
import com.example.inmoteca.dto.PropertyDto;
import com.example.inmoteca.model.Property;
import com.example.inmoteca.model.ResponseContainer;
import com.example.inmoteca.retrofit.generator.ServiceGenerator;
import com.example.inmoteca.retrofit.generator.TipoAutenticacion;
import com.example.inmoteca.retrofit.services.PropertyService;
import com.example.inmoteca.util.UtilToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPropertyActivity extends AppCompatActivity {

    EditText etTitle, etDescription, etPrice, etRooms, etSize, etAddress, etCod, etCity, etProvince, etLoc;
    Button btn_addProperty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);
        getSupportActionBar().hide();

        findViews();

        btn_addProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProperty();
                Intent login = new Intent(AddPropertyActivity.this, DashboardActivity.class);
            }
        });
    }

    private void addProperty() {
        PropertyDto property = new PropertyDto();
        property.setTitle(etTitle.getText().toString().trim());
        property.setDescription(etDescription.getText().toString().trim());
        property.setPrice(Integer.parseInt(etPrice.getText().toString().trim()));
        property.setRooms(Integer.parseInt(etRooms.getText().toString().trim()));
        property.setSize(Integer.parseInt(etSize.getText().toString().trim()));
        property.setAddress(etAddress.getText().toString().trim());
        property.setZipcode(etCod.getText().toString().trim());
        property.setCity(etCity.getText().toString().trim());
        property.setProvince(etProvince.getText().toString().trim());
        property.setLoc(etLoc.getText().toString().trim());

        PropertyService service = ServiceGenerator.createService(PropertyService.class, UtilToken.getToken(this), TipoAutenticacion.JWT);
        Call<ResponseContainer<PropertyDto>> call = service.addProperty(property);

        call.enqueue(new Callback<ResponseContainer<PropertyDto>>() {
            @Override
            public void onResponse(Call<ResponseContainer<PropertyDto>> call, Response<ResponseContainer<PropertyDto>> response) {
                if (response.isSuccessful()) {
                    Log.d("Uploaded", "Éxito");
                    Log.d("Uploaded", response.body().toString());
                    //propertyIdAdd = response.body().getId();
                } else {
                    Log.e("Upload error", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<PropertyDto>> call, Throwable t) {
                Log.e("Upload error", t.getMessage());
            }
        });
    }
    private void findViews() {
        etTitle = findViewById(R.id.editTextTitleProp);
        etDescription = findViewById(R.id.editTextDescriptionProp);
        etPrice = findViewById(R.id.editTextPriceProp);
        etRooms = findViewById(R.id.editTextRoomsProp);
        etSize = findViewById(R.id.editTextSizeProp);
        etAddress = findViewById(R.id.editTextDirectionProp);
        etCod = findViewById(R.id.editTextZipcodeProp);
        etCity = findViewById(R.id.editTextCityProp);
        etProvince = findViewById(R.id.editTextProvinceProp);
        etLoc = findViewById(R.id.editTextLocationProp);
        btn_addProperty = findViewById(R.id.btn_addProperty);

    }
}
