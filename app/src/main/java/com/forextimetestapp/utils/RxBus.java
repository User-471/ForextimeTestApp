package com.forextimetestapp.utils;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RxBus {

    private static RxBus INSTANCE;

    public static RxBus getInstance() {
        if(INSTANCE==null) {
            INSTANCE = new RxBus();
        }
        return INSTANCE;
    }

    private PublishSubject<Object> bus = PublishSubject.create();

    public void send(Object o) {
        bus.onNext(o);
    }

    public Observable<Object> toObservable() {
        return bus;
    }

}