package com.teslyuk.android.kommersant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by taras.teslyuk on 10/27/15.
 */
public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    Button newGame, settings, about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_menu);

        initView();
    }

    private void initView() {
        newGame = (Button) findViewById(R.id.menu_new_game_btn);
        settings = (Button) findViewById(R.id.menu_setting_btn);
        about = (Button) findViewById(R.id.menu_about_btn);

        newGame.setOnClickListener(this);
        settings.setOnClickListener(this);
        about.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.menu_new_game_btn:
                intent = new Intent(MenuActivity.this, GameActivity.class);
                startActivity(intent);
                break;

            case R.id.menu_setting_btn:
                intent = new Intent(MenuActivity.this, SettingActivity.class);
                startActivity(intent);
                break;

            case R.id.menu_about_btn:
                intent = new Intent(MenuActivity.this, AboutActivity.class);
                startActivity(intent);
                break;

        }
    }
}
