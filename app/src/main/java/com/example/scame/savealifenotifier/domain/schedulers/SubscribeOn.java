package com.example.scame.savealifenotifier.domain.schedulers;


import rx.Scheduler;

public interface SubscribeOn {

    Scheduler getScheduler();
}
