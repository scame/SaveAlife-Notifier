package com.example.scame.savealifenotifier.presentation.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.scame.savealifenotifier.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirebaseTestActivity extends AppCompatActivity {

    private static final String TAG = "logTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firebase_test_layout);

        //startActivity(new Intent(this, PageActivity.class));

        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);
                Log.i(TAG, "Key: " + key + " Value: " + value);
            }
        }
    }

    @OnClick(R.id.subscribe_btn)
    public void onSubscribeClick() {
        FirebaseMessaging.getInstance().subscribeToTopic("news");

        Toast.makeText(this, "Subscribed to news", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.log_token_btn)
    public void onLogTokenClick() {
        String token = FirebaseInstanceId.getInstance().getToken();


        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
        Log.i(TAG, token);
    }
}
