package com.teslyuk.android.kommersant;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

/**
 * Created by taras.teslyuk on 10/27/15.
 */
public class SettingActivity extends Activity {

    ToggleButton soundOn, soundEffectOn, vibroOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView() {
        soundOn = (ToggleButton) findViewById(R.id.setting_music_on_btn);
        soundEffectOn = (ToggleButton) findViewById(R.id.setting_sound_effects_on_btn);
        vibroOn = (ToggleButton) findViewById(R.id.setting_vibro_on_btn);

        soundOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
             AppData.isSoundOn = b;
            }
        });

        soundEffectOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                AppData.isSoundEffectOn = b;
            }
        });

        vibroOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                AppData.isVibroOn = b;
            }
        });

        soundOn.setChecked(AppData.isSoundOn);
        soundEffectOn.setChecked(AppData.isSoundEffectOn);
        vibroOn.setChecked(AppData.isVibroOn);
    }
}
