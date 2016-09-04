package com.example.scame.savealifenotifier.presentation.activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.scame.savealifenotifier.presentation.di.components.ApplicationComponent;
import com.example.scame.savealifenotifier.presentation.models.HelpMessageModel;

public class GoogleHelpMapActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        HelpMessageModel helpMessage = i.getParcelableExtra(GoogleHelpMapActivity.class.getCanonicalName());
        startGoogleMapIntent(helpMessage);
    }

    private void startGoogleMapIntent(HelpMessageModel helpMessageModel) {
        Double latitude = helpMessageModel.getLatitude();
        Double longitude = helpMessageModel.getLongitude();
        String message = helpMessageModel.getMessage();

        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + latitude + "," + longitude + "(" + message + ")");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    @Override
    protected void inject(ApplicationComponent component) {

    }
}
