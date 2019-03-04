package com.example.inmoteca.ui;

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
import com.example.inmoteca.model.Registro;
import com.example.inmoteca.retrofit.generator.ServiceGenerator;
import com.example.inmoteca.retrofit.services.LoginService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivLogo;
    EditText etUsername;
    EditText etEmailSignUp;
    EditText etPassSignUp;
    EditText etRepeatPass;
    Button btn_register;
    Button btn_irLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViews();
        events();
    }

    private void events() {
        btn_register.setOnClickListener(this);
        btn_irLogin.setOnClickListener(this);
    }

    private void findViews() {
        ivLogo = findViewById(R.id.imageViewLogo);
        etUsername = findViewById(R.id.editTextUsername);
        etEmailSignUp = findViewById(R.id.editTextEmailSignUp);
        etPassSignUp = findViewById(R.id.editTextPassSignUp);
        etRepeatPass = findViewById(R.id.editTextRepeatPass);
        btn_register = findViewById(R.id.btn_register);
        btn_irLogin = findViewById(R.id.btn_irLogin);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                doRegister();
                break;
            case R.id.btn_irLogin:
                irLogin();
                break;
        }
    }

    private void irLogin() {
        Intent irLogin = new Intent(this, LoginActivity.class);
        startActivity(irLogin);
        finish();
    }

    private void doRegister() {
        // Recoger los datos del formulario
        String username = etUsername.getText().toString().trim();
        String email = etEmailSignUp.getText().toString().trim();
        String password = etPassSignUp.getText().toString().trim();

        Registro registro = new Registro(username, email, password);

        LoginService service = ServiceGenerator.createService(LoginService.class);

        Call<LoginResponse> loginReponseCall = service.doRegister(registro);

        loginReponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.code() == 201) {

                    ServiceGenerator.jwtToken = response.body().getToken();

                    Toast.makeText(RegisterActivity.this, "REGISTRO CORRECTO", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this, DashboardActivity.class));
                    finish();

                } else {
                    // error
                    Toast.makeText(RegisterActivity.this, "Error en el registro. Revise los datos introducidos", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(RegisterActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();

            }
        });

    }

}
