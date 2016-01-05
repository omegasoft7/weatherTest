package com.example.farhad.weather.server;

import com.fewlaps.quitnowcache.QNCache;

import java.util.ArrayList;

import fslogger.lizsoft.lv.fslogger.FSLogger;

public class internalServerAction<T> {

    private Observables.ServerAction<T> serverAction;
    private ArrayList<T> objects = new ArrayList<>();
    private QNCache mCache;
    private String uniqueCode;

    public internalServerAction(String code, QNCache cache, Observables.ServerAction<T> sa) {
        uniqueCode = code;
        mCache = cache;
        serverAction = sa;
    }

    public void onCompleted() {
        onCompleted(true);
    }

    private void onCompleted(boolean caching) {
        FSLogger.w(1, "internalServerAction onCompleted");

        if (caching) {
            mCache.set(uniqueCode, objects);
        }

        serverAction.onCompleted(objects);
    }

    public void onError(Throwable e) {
        FSLogger.w(1, "internalServerAction onError");
        serverAction.onError(e);
    }

    public void onNext(T parseobject) {
        FSLogger.w(1, "internalServerAction onNext");
        objects.add(parseobject);
        serverAction.onNext(parseobject);
    }

    public boolean getFromCache () {

        if (mCache.contains(uniqueCode)) {
            try {
                ArrayList<T> list = (ArrayList<T>) mCache.get(uniqueCode);
                if (list == null) return false;

                FSLogger.w(1, "WOOOOOOOW Sending from cache!");
                for (int i = 0; i < list.size(); i++) {
                    onNext(list.get(i));
                }
                onCompleted(false);
                return true;
            } catch (ClassCastException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }
}