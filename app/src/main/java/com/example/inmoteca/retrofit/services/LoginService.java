package com.example.inmoteca.retrofit.services;

import com.example.inmoteca.model.LoginResponse;
import com.example.inmoteca.model.Registro;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginService {

    @POST("/auth")
    Call<LoginResponse> doLogin(@Header("Authorization")String authorization);


    @POST("/users")
    Call<LoginResponse> doRegister(@Body Registro registro);
}
