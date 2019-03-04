package com.example.inmoteca.util;

import android.content.Context;
import android.content.SharedPreferences;

public class UtilToken {
    public static void setToken(Context mContext, String token){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                "login",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("JWT_KEY", token);
        editor.commit();
    }

    public static String getToken(Context mContext){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                "login",
                Context.MODE_PRIVATE
        );

        String jwt = sharedPreferences
                .getString("JWT_KEY", null);

        return jwt;
    }


    public static void setIdProperty(Context mContext, String values){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                "login",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id_propiedad", values);
        editor.commit();
    }

    public static String getIdProperty(Context mContext){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                "login",
                Context.MODE_PRIVATE);
        return sharedPreferences.getString("id_propiedad", null);
    }
}
