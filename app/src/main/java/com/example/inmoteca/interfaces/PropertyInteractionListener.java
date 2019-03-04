package com.example.inmoteca.interfaces;

import android.widget.ImageView;

import com.example.inmoteca.ui.DashboardActivity;

public interface PropertyInteractionListener {

    public void verDetalles(String idProperty, String propertyTitle, String propertyDescription, int propertyRooms, int propertySize, String propertyCity);

    public void favOn(String id, final ImageView imagen);

    public void favOff(String id, final ImageView imagen);
}
