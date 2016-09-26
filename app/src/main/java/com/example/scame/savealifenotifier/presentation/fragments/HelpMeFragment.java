package com.example.scame.savealifenotifier.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
    private static final String HELP_ME_VISIBILITY = "Help me visibility";

    @Inject
    IHelpMePresenter<IHelpMePresenter.HelpMeView> presenter;

    @BindView(R.id.help_me_end_btn) PulsatorLayout helpMeEnd;
    @BindView(R.id.help_me_start_btn) PulsatorLayout helpMeStart;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.help_me_fragment, container, false);

        ((PageActivity) getActivity()).getHelpMeComponent().inject(this);

        ButterKnife.bind(this, fragmentView);
        presenter.setView(this);

        configurePulsator();
        restoreHelpButton(savedInstanceState);
        return fragmentView;
    }

    private void configurePulsator() {
        helpMeEnd.setCount(PULSE_COUNT);
        helpMeEnd.setDuration(PULSE_DURATION);
        helpMeEnd.start();

        helpMeStart.setCount(PULSE_COUNT);
        helpMeStart.setDuration(PULSE_DURATION);
        helpMeStart.start();
    }

    private void restoreHelpButton(Bundle savedInstanceState) {
        if (savedInstanceState != null){
            //Log.i("helpmebtn", String.valueOf(savedInstanceState.getInt(HELP_ME_VISIBILITY)));
            switch(savedInstanceState.getInt(HELP_ME_VISIBILITY)){
                case View.VISIBLE:
                    helpMeStart.setVisibility(View.VISIBLE);
                    helpMeEnd.setVisibility(View.GONE);
                    break;
                case View.INVISIBLE:
                    helpMeStart.setVisibility(View.GONE);
                    helpMeEnd.setVisibility(View.VISIBLE);
                    break;
                case View.GONE:
                    helpMeStart.setVisibility(View.GONE);
                    helpMeEnd.setVisibility(View.VISIBLE);
                    break;
                default:
                    helpMeStart.setVisibility(View.VISIBLE);
                    helpMeEnd.setVisibility(View.GONE);
            }
        }
    }

    @OnClick(R.id.help_me_start_btn)
    public void onHelpMeClickStart() {
        showMaterialDialog();
    }

    @OnClick(R.id.help_me_end_btn)
    public void onHelpMeClickEnd() {
        showMaterialDialog();
    }

    @Override
    public void showConfirmation(String confirmation) {
        Toast.makeText(getContext(), confirmation, Toast.LENGTH_SHORT).show();
    }

    private void showMaterialDialog() {
        MaterialStyledDialog materialDialog;

        if (helpMeStart.getVisibility() == View.VISIBLE) {
            materialDialog = new MaterialStyledDialog(getContext())
                    .withDialogAnimation(true)
                    .setHeaderColor(R.color.colorPrimary)
                    .setDescription("Do you really need help?")
                    .setPositive(getString(R.string.dialog_positive), (dialog, which) -> {
                        presenter.sendHelpMessage("help me test");
                        helpMeEnd.setVisibility(View.VISIBLE);
                        helpMeStart.setVisibility(View.INVISIBLE);
                    });

        } else  {
            materialDialog = new MaterialStyledDialog(getContext())
                    .setHeaderColor(R.color.colorPulsatorGreen)
                    .setDescription("You don't need help anymore?")
                    .setPositive(getString(R.string.dialog_positive), (dialog, which) -> {
                        // TODO: send an end message
                        helpMeStart.setVisibility(View.VISIBLE);
                        helpMeEnd.setVisibility(View.INVISIBLE);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //Log.i("helpmebtn", String.valueOf(helpMeStart.getVisibility()));
        outState.putInt(HELP_ME_VISIBILITY, helpMeStart.getVisibility());
        super.onSaveInstanceState(outState);
    }
}
