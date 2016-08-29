package com.example.scame.savealifenotifier.presentation.presenters;


import android.util.Log;

import com.example.scame.savealifenotifier.domain.usecases.DefaultSubscriber;
import com.example.scame.savealifenotifier.domain.usecases.HelpMessageUseCase;

import java.io.IOException;

import okhttp3.ResponseBody;

public class HelpMePresenterImp<T extends IHelpMePresenter.HelpMeView> implements IHelpMePresenter<T> {

    private T view;

    private HelpMessageUseCase helpMessageUseCase;

    public HelpMePresenterImp(HelpMessageUseCase helpMessageUseCase) {
        this.helpMessageUseCase = helpMessageUseCase;
    }

    @Override
    public void sendHelpMessage(String message) {
        helpMessageUseCase.setMessage(message);
        helpMessageUseCase.execute(new HelpMessageSubscriber());
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
}
