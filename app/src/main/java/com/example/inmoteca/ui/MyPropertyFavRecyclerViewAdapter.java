package com.example.inmoteca.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.inmoteca.R;
import com.example.inmoteca.fragments.PropertyFavFragment.OnListFragmentInteractionListener;
import com.example.inmoteca.interfaces.PropertyInteractionListener;
import com.example.inmoteca.model.Property;

import java.util.List;


public class MyPropertyFavRecyclerViewAdapter extends RecyclerView.Adapter<MyPropertyFavRecyclerViewAdapter.ViewHolder> {

    private final List<Property> mValues;
    private final PropertyInteractionListener mListener;
    private Context ctx;


    public MyPropertyFavRecyclerViewAdapter(Context ctx, int layout, List<Property> items, PropertyInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.ctx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_propertyfav, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvPrice.setText(String.valueOf(holder.mItem.getPrice()+" €/mes."));
        holder.tvRooms.setText(String.valueOf(holder.mItem.getRooms())+" hab.");
        holder.tvCity.setText(holder.mItem.getCity()+", "+(holder.mItem.getProvince()));

        if(holder.mItem.getListPhotos() == null) {
            Glide.with(ctx).load("https://www.abc.es/Media/201304/22/vallecas-solvia--644x362.JPG").into(holder.ivProperty);
        }else {
            Glide.with(ctx).load(holder.mItem.getListPhotos().get(0)).into(holder.ivProperty);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.verDetalles(holder.mItem.getId(), holder.mItem.getTitle(), holder.mItem.getDescription(), holder.mItem.getRooms(), holder.mItem.getSize(), holder.mItem.getCity());

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public Property mItem;
        public ImageView ivProperty;
        public TextView tvPrice;
        public TextView tvRooms;
        public TextView tvCity;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ivProperty = view.findViewById(R.id.imageViewPropertyPhoto);
            tvPrice = view.findViewById(R.id.textViewPrice);
            tvRooms = view.findViewById(R.id.textViewRooms);
            tvCity = view.findViewById(R.id.textViewCity);
        }

        @Override
        public String toString() {
            return super.toString() + " '";
        }
    }
}
