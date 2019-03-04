package com.example.inmoteca.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.inmoteca.R;
import com.example.inmoteca.model.LoginResponse;
import com.example.inmoteca.retrofit.generator.ServiceGenerator;
import com.example.inmoteca.retrofit.services.LoginService;
import com.example.inmoteca.util.UtilToken;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView ivLogo;
    EditText etEmail;
    EditText etPass;
    Button btn_login;
    Button btn_irRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();
        events();

    }

    private void events() {
        btn_login.setOnClickListener(this);
        btn_irRegistro.setOnClickListener(this);
    }

    private void findViews() {
        ivLogo = findViewById(R.id.imageViewLogo);
        etEmail = findViewById(R.id.editTextEmail);
        etPass = findViewById(R.id.editTextPassword);
        btn_login = findViewById(R.id.btn_login);
        btn_irRegistro = findViewById(R.id.btn_irRegistro);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                doLogin();
                break;
            case R.id.btn_irRegistro:
                irRegistro();
                break;
        }
    }

    private void irRegistro() {
        Intent irRegistro = new Intent(this, RegisterActivity.class);
        startActivity(irRegistro);
        finish();
    }

    private void doLogin() {

        String email_txt = etEmail.getText().toString();
        String password_txt = etPass.getText().toString();

        String credentials = Credentials.basic(email_txt,password_txt);


        LoginService service = ServiceGenerator.createService(LoginService.class, email_txt, password_txt);
        Call<LoginResponse> call = service.doLogin(credentials);

        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autenticando...");
        progressDialog.show();

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (!response.isSuccessful()) {
                    Log.i("AAAAAAAAAAAAAAAA", response.message());
                    Toast.makeText(LoginActivity.this, "Error de petición", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(LoginActivity.this, "LOGIN CORRECTO", Toast.LENGTH_SHORT).show();
                    UtilToken.setToken(LoginActivity.this, response.body().getToken());
                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(LoginActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
