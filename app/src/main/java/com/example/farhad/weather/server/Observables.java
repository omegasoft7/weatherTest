package com.example.farhad.weather.server;

import com.example.farhad.weather.model.City;
import com.example.farhad.weather.server.api.ServerApi;
import com.example.farhad.weather.server.api.YahooServerApi;
import com.fewlaps.quitnowcache.QNCache;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit.client.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by farhad on 11/7/15.
 */
public class Observables {

    private ServerApi serverApi;
    private YahooServerApi yahooServerApi;
    private QNCache mCache;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();


    public Observables(ServerApi api, YahooServerApi yahooApi, QNCache cache) {
        this.serverApi = api;
        this.yahooServerApi = yahooApi;
        this.mCache = cache;
    }

    public void GetWeathers(List<City> cities, ServerAction<City> subject) {

        internalServerAction<City> internalServerAction = new internalServerAction<>("GetWeathers", mCache, subject);

        if (!internalServerAction.getFromCache()) {
            compositeSubscription.add(Observable.from(cities)
                    .subscribeOn(Schedulers.io())
                    .flatMap(city -> {
                        String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\")", city.getCity() + ", " + city.getCountry());

                        return yahooServerApi.getWeather(YQL, "json").map(weather -> {
                            city.setChannel(weather.getChannel());
                            return city;
                        });
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<City>() {
                        @Override
                        public void onCompleted() {
                            internalServerAction.onCompleted();
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            internalServerAction.onError(e);
                        }

                        @Override
                        public void onNext(City city) {
                            internalServerAction.onNext(city);
                        }
                    }));
        }
    }

    public void DownloadData(ServerAction<City> subject) {

        internalServerAction<City> internalServerAction = new internalServerAction<>("DownloadData", mCache, subject);

        if (!internalServerAction.getFromCache()) {
            compositeSubscription.add(serverApi
                    .getDataFile()
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<Response>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            internalServerAction.onError(e);
                        }

                        @Override
                        public void onNext(Response response) {

                            BufferedReader reader = null;
                            try {

                                reader = new BufferedReader(new InputStreamReader(response.getBody().in()));

                                String line;
                                try {
                                    while ((line = reader.readLine()) != null) {
                                        if (line.contains("country")) continue;

                                        City city = new City();
                                        String[] info = line.trim().split(" ");
                                        city.setCountry(info[0].trim());
                                        city.setZip(info[1].trim());
                                        int citynumber = 2;
                                        StringBuilder cityName = new StringBuilder("");
                                        cityName.append(info[2]).append(" ");
                                        while (info[2].contains("\"")) {
                                            citynumber++;
                                            cityName.append(info[citynumber]).append(" ");
                                            if (info[citynumber].contains("\"")) {
                                                break;
                                            }
                                        }
                                        city.setCity(cityName.toString().trim().replace("\"", ""));
                                        city.setLongitude(info[citynumber + 1].trim());
                                        city.setLatitude(info[citynumber + 2].trim());
                                        internalServerAction.onNext(city);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            internalServerAction.onCompleted();
                        }
                    }));
        }
    }

    public void unsubscribe() {
        compositeSubscription.unsubscribe();
    }


    public interface ServerAction<T> {
        void onCompleted(ArrayList<T> objects);

        void onError(Throwable e);

        void onNext(T parseobject);
    }
}
