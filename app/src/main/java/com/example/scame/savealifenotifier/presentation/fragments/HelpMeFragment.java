package com.example.scame.savealifenotifier.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.scame.savealifenotifier.R;
import com.example.scame.savealifenotifier.presentation.activities.PageActivity;
import com.example.scame.savealifenotifier.presentation.presenters.IHelpMePresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelpMeFragment extends BaseFragment implements IHelpMePresenter.HelpMeView {

    @Inject
    IHelpMePresenter<IHelpMePresenter.HelpMeView> presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.help_me_fragment, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("SaveAlife HelpMe");
        ((PageActivity) getActivity()).getHelpMeComponent().inject(this);

        ButterKnife.bind(this, fragmentView);
        presenter.setView(this);

        return fragmentView;
    }

    @OnClick(R.id.help_me_btn)
    public void onHelpMeClick() {
        presenter.sendHelpMessage("help me");
    }

    @Override
    public void showConfirmation(String confirmation) {
        Toast.makeText(getContext(), confirmation, Toast.LENGTH_SHORT).show();
    }
}
