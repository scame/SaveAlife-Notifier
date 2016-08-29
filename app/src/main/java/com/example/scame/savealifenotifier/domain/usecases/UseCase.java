package com.example.scame.savealifenotifier.domain.usecases;

import com.example.scame.savealifenotifier.domain.schedulers.ObserveOn;
import com.example.scame.savealifenotifier.domain.schedulers.SubscribeOn;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public abstract class UseCase<T> {

    private SubscribeOn subscribeOn;
    private ObserveOn observeOn;
    private Subscription subscription = Subscriptions.empty();
    private Observable<T> observable;

    public UseCase(SubscribeOn subscribeOn, ObserveOn observeOn) {
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    public void execute(Subscriber<T> subscriber) {

        if (observable == null) {
            observable = getUseCaseObservable()
                    .subscribeOn(subscribeOn.getScheduler())
                    .observeOn(observeOn.getScheduler())
                    .cache()
                    .doOnError(throwable -> observable = null)
                    .doOnCompleted(() -> observable = null);

            subscription = observable.subscribe(subscriber);
        } else {
            subscription = observable.subscribe(subscriber);
        }

    }

    protected abstract Observable<T> getUseCaseObservable();

    public void unsubscribe() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
