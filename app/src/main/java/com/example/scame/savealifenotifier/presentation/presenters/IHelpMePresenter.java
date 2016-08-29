package com.example.scame.savealifenotifier.presentation.presenters;


public interface IHelpMePresenter<T> extends Presenter<T> {

    interface HelpMeView {

        void showConfirmation(String confirmation);
    }

    void sendHelpMessage(String message);
}
