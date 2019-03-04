package com.example.inmoteca.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.inmoteca.R;
import com.example.inmoteca.interfaces.PropertyInteractionListener;
import com.example.inmoteca.model.Property;
import com.example.inmoteca.model.ResponseContainer;
import com.example.inmoteca.retrofit.generator.ServiceGenerator;
import com.example.inmoteca.retrofit.generator.TipoAutenticacion;
import com.example.inmoteca.retrofit.services.PropertyService;
import com.example.inmoteca.ui.MypropertyRecyclerViewAdapter;
import com.example.inmoteca.util.UtilToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropertyListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private PropertyInteractionListener mListener;
    MypropertyRecyclerViewAdapter adapter;
    Context ctx;
    List<Property> properties;

    public PropertyListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PropertyListFragment newInstance(int columnCount) {
        PropertyListFragment fragment = new PropertyListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_property_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            properties = new ArrayList<>();

            // properties.add(new Property("Piso en Triana", "...", 1000,3 , 20, "Calle Pureza", "41010", "Sevilla"));
            if (UtilToken.getToken(ctx) == null) {
                PropertyService service2 = ServiceGenerator.createService(PropertyService.class);
                Call<ResponseContainer<Property>> call = service2.listProperties();
                call.enqueue(new Callback<ResponseContainer<Property>>() {
                    @Override
                    public void onResponse(Call<ResponseContainer<Property>> call, Response<ResponseContainer<Property>> response) {
                        try {

                            if (response.isSuccessful()) {
                                ResponseContainer<Property> data = response.body();

                                properties = data.getRows();

                                adapter = new MypropertyRecyclerViewAdapter(
                                        ctx,
                                        R.layout.fragment_property,
                                        properties,
                                        mListener
                                );

                                recyclerView.setAdapter(adapter);

                            } else {
                                Toast.makeText(getActivity(), "Error al obtener lista inmuebles", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Log.d("onResponse", "There is an error");
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(Call<ResponseContainer<Property>> call, Throwable t) {
                        Log.d("onResponse", "There is another error");
                    }

                });

            } else {
                PropertyService service2 = ServiceGenerator.createService(PropertyService.class, UtilToken.getToken((ctx)), TipoAutenticacion.JWT);
                Call<ResponseContainer<Property>> call = service2.listPropertiesUser();
                call.enqueue(new Callback<ResponseContainer<Property>>() {
                    @Override
                    public void onResponse(Call<ResponseContainer<Property>> call, Response<ResponseContainer<Property>> response) {
                        try {

                            if (response.isSuccessful()) {
                                ResponseContainer<Property> data = response.body();

                                properties = data.getRows();

                                adapter = new MypropertyRecyclerViewAdapter(
                                        ctx,
                                        R.layout.fragment_property,
                                        properties,
                                        mListener
                                );

                                recyclerView.setAdapter(adapter);

                            } else {
                                Toast.makeText(getActivity(), "Error al obtener lista inmuebles", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Log.d("onResponse", "There is an error");
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(Call<ResponseContainer<Property>> call, Throwable t) {
                        Log.d("onResponse", "There is another error");
                    }

                });
            }

        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        ctx = context;
        super.onAttach(context);
        if (context instanceof PropertyInteractionListener) {
            mListener = (PropertyInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PropertyInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
