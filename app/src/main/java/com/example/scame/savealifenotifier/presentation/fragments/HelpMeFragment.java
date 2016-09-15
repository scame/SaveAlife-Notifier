package com.example.scame.savealifenotifier.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.scame.savealifenotifier.R;
import com.example.scame.savealifenotifier.presentation.activities.PageActivity;
import com.example.scame.savealifenotifier.presentation.presenters.IHelpMePresenter;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class HelpMeFragment extends BaseFragment implements IHelpMePresenter.HelpMeView {

    private static final int PULSE_COUNT = 4;
    private static final int PULSE_DURATION = 7000;

    @Inject
    IHelpMePresenter<IHelpMePresenter.HelpMeView> presenter;

    @BindView(R.id.help_me_end_btn) PulsatorLayout pulsatorGreen;
    @BindView(R.id.help_me_start_btn) PulsatorLayout pulsatorRed;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.help_me_fragment, container, false);

        ((PageActivity) getActivity()).getHelpMeComponent().inject(this);

        ButterKnife.bind(this, fragmentView);
        presenter.setView(this);

        configurePulsator();

        return fragmentView;
    }

    private void configurePulsator() {
        pulsatorGreen.setCount(PULSE_COUNT);
        pulsatorGreen.setDuration(PULSE_DURATION);
        pulsatorGreen.start();

        pulsatorRed.setCount(PULSE_COUNT);
        pulsatorRed.setDuration(PULSE_DURATION);
        pulsatorRed.start();
    }


    @OnClick(R.id.help_me_start_btn)
    public void onHelpMeClickStart() {
        Log.i("onxHelpMeStart", "true");
        showMaterialDialog();
    }

    @OnClick(R.id.help_me_end_btn)
    public void onHelpMeClickEnd() {
        Log.i("onxHelpMeEnd", "true");
        showMaterialDialog();
    }

    @Override
    public void showConfirmation(String confirmation) {
        Toast.makeText(getContext(), confirmation, Toast.LENGTH_SHORT).show();
    }

    private void showMaterialDialog() {
        MaterialStyledDialog materialDialog;

        if (pulsatorRed.getVisibility() == View.VISIBLE) {
            materialDialog = new MaterialStyledDialog(getContext())
                    .withDialogAnimation(true)
                    .setHeaderColor(R.color.colorPrimary)
                    .setDescription("Do you really need help?")
                    .setPositive(getString(R.string.dialog_positive), (dialog, which) -> {
                        presenter.sendHelpMessage("help me test");
                        pulsatorGreen.setVisibility(View.VISIBLE);
                        pulsatorRed.setVisibility(View.INVISIBLE);
                    });

        } else  {
            materialDialog = new MaterialStyledDialog(getContext())
                    .setHeaderColor(R.color.colorPulsatorGreen)
                    .setDescription("You don't need help anymore?")
                    .setPositive(getString(R.string.dialog_positive), (dialog, which) -> {
                        // TODO: send an end message
                        pulsatorRed.setVisibility(View.VISIBLE);
                        pulsatorGreen.setVisibility(View.INVISIBLE);
                    });
        }

        materialDialog
                .withDialogAnimation(true)
                .setTitle("Confirmation")
                .setStyle(Style.HEADER_WITH_TITLE)
                .setNegative(getString(R.string.dialog_negative), (dialog, which) -> {
                    dialog.dismiss();
                })
                .setCancelable(false)
                .build();

        materialDialog.show();
    }
}
