package com.example.farhad.weather.view;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.farhad.weather.R;
import com.example.farhad.weather.model.City;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by farhad on 11/7/15.
 */
public class CityRow extends RelativeLayout {

    @Bind(R.id.cityview_cityname)
    TextView cityName;

    @Bind(R.id.cityview_minTemp)
    TextView minTemp;

    @Bind(R.id.cityview_maxTemp)
    TextView maxTemp;

    @Bind(R.id.cityview_distance)
    TextView distance;


    private City currCity;

    public CityRow(Context context) {
        super(context);
        init();
    }

    public CityRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CityRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cityrow_view, this);

        if (isInEditMode()) return;

        ButterKnife.bind(this, view);

        setOnClickListener(onClickListener);
    }

    public void setCurrCity(City city) {
        this.currCity = city;

        if (city.getChannel() != null) {
            minTemp.setText(city.getChannel().getItem().getForecast().get(0).getLow());
            maxTemp.setText(city.getChannel().getItem().getForecast().get(0).getHigh());
            distance.setText(String.valueOf(city.getDistance()));
        }

        cityName.setText(currCity.getCity());
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

            // Setting Dialog Title
            alertDialog.setTitle(currCity.getChannel().getDescription());

            // Setting Dialog Message
            alertDialog.setMessage(Html.fromHtml(currCity.getChannel().getItem().getDescription()));

            // Showing Alert Message
            alertDialog.show();
        }
    };
}
