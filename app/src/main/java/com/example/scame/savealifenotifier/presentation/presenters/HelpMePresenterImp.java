package com.example.scame.savealifenotifier.presentation.presenters;


import android.util.Log;

import com.example.scame.savealifenotifier.domain.usecases.DefaultSubscriber;
import com.example.scame.savealifenotifier.domain.usecases.DeviceStatusUseCase;
import com.example.scame.savealifenotifier.domain.usecases.HelpMessageUseCase;

import java.io.IOException;

import okhttp3.ResponseBody;

public class HelpMePresenterImp<T extends IHelpMePresenter.HelpMeView> implements IHelpMePresenter<T> {

    private T view;

    private HelpMessageUseCase helpMessageUseCase;

    private DeviceStatusUseCase deviceStatusUseCase;

    private String helpMessage;

    public HelpMePresenterImp(HelpMessageUseCase helpMessageUseCase,
                              DeviceStatusUseCase deviceStatusUseCase) {

        this.helpMessageUseCase = helpMessageUseCase;
        this.deviceStatusUseCase = deviceStatusUseCase;
    }

    @Override
    public void sendHelpMessage(String message) {
        helpMessage = message;

        deviceStatusUseCase.execute(new DeviceStatusSubscriber());
    }

    private final class DeviceStatusSubscriber extends DefaultSubscriber<ResponseBody> {

        @Override
        public void onCompleted() {
            super.onCompleted();

            helpMessageUseCase.setMessage(helpMessage);
            helpMessageUseCase.execute(new HelpMessageSubscriber());
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            Log.i("onxError", "deviceStatusError + " + e.getLocalizedMessage());
        }
    }

    private final class HelpMessageSubscriber extends DefaultSubscriber<ResponseBody> {

        @Override
        public void onNext(ResponseBody responseBody) {
            super.onNext(responseBody);

            try {
                Log.i("onxNext", responseBody.string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onCompleted() {
            super.onCompleted();

            Log.i("onxCompleted", "completed");
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            Log.i("onxError", e.getLocalizedMessage());
        }
    }

    @Override
    public void setView(T view) {
        this.view = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }
}
