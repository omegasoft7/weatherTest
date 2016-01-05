package com.example.farhad.weather;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.farhad.weather.adapter.CityAdapter;
import com.example.farhad.weather.model.City;
import com.example.farhad.weather.server.Observables;
import com.example.farhad.weather.view.CityRow;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import fslogger.lizsoft.lv.fslogger.FSLogger;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.progressbar)
    ProgressBar progressbar;

    @Bind(R.id.cityrow)
    CityRow cityrow;

    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;

    @Inject
    Observables observables;

    private CityAdapter cityAdapter;

    private ArrayList<City> cities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MyAPP) getApplication()).getServiceComponent().inject(this);

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        cityAdapter = new CityAdapter(this, cities);

        observables.DownloadData(new Observables.ServerAction<City>() {
            @Override
            public void onCompleted(ArrayList<City> objects) {
                FSLogger.w(1, "onCompleted");

                observables.GetWeathers(cities, new Observables.ServerAction<City>() {
                    @Override
                    public void onCompleted(ArrayList<City> objects) {
                        FSLogger.w(1, "ffffff onCompleted");
                        cityrow.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        progressbar.setVisibility(View.GONE);

                        cityAdapter.notifyDataSetChanged();

                        callLocationService();
                    }

                    @Override
                    public void onError(Throwable e) {
                        FSLogger.w(1, "ffffff onError");
                    }

                    @Override
                    public void onNext(City city) {
                        FSLogger.w(1, "ffffff onNext weather:" + city.getChannel().getItem().getForecast().get(0).getLow());
                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                FSLogger.w(1, "onError");
            }

            @Override
            public void onNext(City city) {
                FSLogger.w(1, "onNext city:" + city.toString());
                cities.add(city);
            }
        });


        recyclerView.setAdapter(cityAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onPause() {
        super.onPause();
        observables.unsubscribe();
    }

    protected synchronized void callLocationService() {
        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3,
                3, mLocationListener);
    }

    private final LocationListener mLocationListener = new android.location.LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            for (City city: cities) {
                Location _loc = new Location("");
                _loc.setLatitude(Double.parseDouble(city.getLatitude()));
                _loc.setLongitude(Double.parseDouble(city.getLongitude()));
                city.setDistance(location.distanceTo(_loc));
            }

            Collections.sort(cities);
            cityAdapter.notifyDataSetChanged();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
        }
    };
}
