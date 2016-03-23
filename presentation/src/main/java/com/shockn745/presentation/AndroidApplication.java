package com.shockn745.presentation;

import android.app.Activity;
import android.app.Application;

import com.shockn745.presentation.internal.di.components.ApplicationComponent;
import com.shockn745.presentation.internal.di.components.DaggerApplicationComponent;
import com.shockn745.presentation.internal.di.modules.ApplicationModule;

/**
 * @author Kempenich Florian
 */
public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initComponent();
    }

    private void initComponent() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static ApplicationComponent getApplicationComponent(Activity activity) {
        return ((AndroidApplication) activity.getApplication()).applicationComponent;
    }
}
