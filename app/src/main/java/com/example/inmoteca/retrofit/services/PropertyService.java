package com.example.inmoteca.retrofit.services;

import com.example.inmoteca.dto.PropertyDto;
import com.example.inmoteca.model.Property;
import com.example.inmoteca.model.ResponseContainer;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PropertyService {

    @GET("properties")
    Call<ResponseContainer<Property>> listProperties();

    @GET("properties/auth")
    Call<ResponseContainer<Property>> listPropertiesUser();

    @GET("properties/fav")
    Call<ResponseContainer<Property>> listFavProperties();

    @POST("/properties")
    Call<ResponseContainer<PropertyDto>> addProperty(@Body PropertyDto property);

    @PUT("/properties/{id}")
    Call<ResponseBody> editProperty(@Path("id") String id, @Body Property dto);

    @DELETE("/properties/{id}")
    Call<ResponseBody> deleteProperty(@Path("id") String id);

    @POST("properties/fav/{id}")
    Call<ResponseContainer> favOn(@Path("id") String id);

    @DELETE("properties/fav/{id}")
    Call<ResponseContainer> favOff(@Path("id") String id);
}
