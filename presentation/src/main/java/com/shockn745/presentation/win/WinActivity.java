package com.shockn745.presentation.win;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;

import com.shockn745.domain.R;
import com.shockn745.presentation.AndroidApplication;
import com.shockn745.presentation.internal.di.components.ApplicationComponent;
import com.shockn745.presentation.internal.di.components.DaggerWinActivityComponent;
import com.shockn745.presentation.internal.di.components.WinActivityComponent;
import com.shockn745.presentation.internal.di.modules.WinActivityModule;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WinActivity extends AppCompatActivity {

    public static final String WINNER_KEY = "winner";
    @Bind(R.id.win_square)
    AppCompatButton winSquare;

    @Inject
    WinAnimations winAnimations;

    public static Intent makeStartingIntent(Context context, int winner) {
        Intent intent = new Intent(context, WinActivity.class);
        Bundle args = new Bundle();
        args.putInt(WINNER_KEY, winner);
        intent.putExtras(args);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getActivityComponent().inject(this);
        winAnimations.initTransitions();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.win_toolbar);
        setSupportActionBar(toolbar);

        int winner = getIntent().getExtras().getInt(WINNER_KEY);


    }

    private WinActivityComponent getActivityComponent() {
        ApplicationComponent appComponent = AndroidApplication.getApplicationComponent(this);
        return DaggerWinActivityComponent.builder()
                .applicationComponent(appComponent)
                .winActivityModule(new WinActivityModule(this))
                .build();
    }
}
