package com.example.scame.savealifenotifier.data.repository;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.scame.savealifenotifier.R;
import com.example.scame.savealifenotifier.SaveAlifeApp;
import com.example.scame.savealifenotifier.presentation.fragments.EndPointFragment;

public class UserDataManagerImp implements IUserDataManager {

    private SharedPreferences sharedPrefs;
    private Context context;

    public UserDataManagerImp() {
        context = SaveAlifeApp.getAppComponent().getApp();
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public void setupUserMode(int userMode) {
        sharedPrefs.edit().putInt(context.getString(R.string.current_mode), userMode).apply();
    }

    @Override
    public int getUserMode() {
        return sharedPrefs.getInt(context.getString(R.string.current_mode), EndPointFragment.DRIVER_MODE); // driver mode is default
    }
}
