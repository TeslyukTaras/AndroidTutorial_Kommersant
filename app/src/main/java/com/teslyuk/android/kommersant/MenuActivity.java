package com.teslyuk.android.kommersant;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by taras.teslyuk on 10/27/15.
 */
public class MenuActivity extends Activity implements View.OnClickListener {

    private Button newGame, settings, about;
    private ImageView rateApp, shareApp;

    String packageName = "com.teslyuk.bacterialwar.android";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_menu);

        initView();
    }

    private void initView() {
        newGame = (Button) findViewById(R.id.menu_new_game_btn);
        settings = (Button) findViewById(R.id.menu_setting_btn);
        about = (Button) findViewById(R.id.menu_about_btn);

        rateApp = (ImageView) findViewById(R.id.rate_iv);
        shareApp = (ImageView) findViewById(R.id.share_iv);

        newGame.setOnClickListener(this);
        settings.setOnClickListener(this);
        about.setOnClickListener(this);

        rateApp.setOnClickListener(this);
        shareApp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
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

            case R.id.rate_iv:
                makeRateCall();
                break;

            case R.id.share_iv:
                makeShareCall();
                break;

        }
    }

    private void makeRateCall() {
        Uri uri = Uri.parse("market://details?id=" + packageName/*context.getPackageName()*/);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + packageName /*context.getPackageName()*/)));
        }
    }

    private void makeShareCall() {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String sAux = "\nLet me recommend you this application\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=" + packageName + " \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }
}