package com.example.inmoteca.ui;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.inmoteca.R;
import com.example.inmoteca.interfaces.PropertyInteractionListener;
import com.example.inmoteca.model.Property;
import com.example.inmoteca.model.ResponseContainer;
import com.example.inmoteca.retrofit.generator.ServiceGenerator;
import com.example.inmoteca.retrofit.generator.TipoAutenticacion;
import com.example.inmoteca.retrofit.services.PropertyService;
import com.example.inmoteca.util.UtilToken;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MypropertyRecyclerViewAdapter extends RecyclerView.Adapter<MypropertyRecyclerViewAdapter.ViewHolder> {

    private final List<Property> mValues;
    private final PropertyInteractionListener mListener;
    private Context ctx;

    public MypropertyRecyclerViewAdapter(Context ctx, int layout, List<Property> items, PropertyInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.ctx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_property, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
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

        if (UtilToken.getToken(ctx) != null) {
            if (mValues.get(position).isFav())
                holder.ivEmptyFav.setImageResource(R.drawable.ic_filledfav);
            else
                holder.ivEmptyFav.setImageResource(R.drawable.ic_favorite_border_white_24dp);


            holder.ivEmptyFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.ivEmptyFav.getDrawable().getConstantState().equals(holder.ivEmptyFav.getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp).getConstantState()))
                        mListener.favOn(mValues.get(position).getId(), holder.ivEmptyFav);
                    else
                        mListener.favOff(mValues.get(position).getId(), holder.ivEmptyFav);
                }
            });


        }
        //Ocultar icono fav mientras no estés logueado
        if(UtilToken.getToken(ctx)==null){
            holder.ivEmptyFav.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public Property mItem;
        public ImageView ivProperty;
        public ImageView ivEmptyFav;
        public TextView tvPrice;
        public TextView tvRooms;
        public TextView tvCity;
        public ImageView ivDelete;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ivProperty = view.findViewById(R.id.imageViewPropertyPhoto);
            ivEmptyFav = view.findViewById(R.id.imageViewEmptyFav);
            tvPrice = view.findViewById(R.id.textViewPrice);
            tvRooms = view.findViewById(R.id.textViewRooms);
            tvCity = view.findViewById(R.id.textViewCity);
            ivDelete = view.findViewById(R.id.imageViewDelete);
        }

        @Override
        public String toString() {
            return super.toString() + " '";
        }
    }
}
