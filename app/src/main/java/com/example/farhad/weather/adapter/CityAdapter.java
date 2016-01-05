package com.example.farhad.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.farhad.weather.R;
import com.example.farhad.weather.model.City;
import com.example.farhad.weather.view.CityRow;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    Context context;
    private ArrayList<City> cityList = new ArrayList<>();

    public CityAdapter(Context context, ArrayList<City> cities) {
        this.context = context;
        this.cityList = cities;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.citylist_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cityrow.setCurrCity(cityList.get(position));
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.cityview)
        CityRow cityrow;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}