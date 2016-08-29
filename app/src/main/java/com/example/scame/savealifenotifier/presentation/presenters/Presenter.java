package com.example.scame.savealifenotifier.presentation.presenters;


public interface Presenter<T> {

    void setView(T view);

    void resume();

    void pause();

    void destroy();
}
