package com.teslyuk.android.kommersant;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigInteger;


public class GameActivity extends Activity implements View.OnClickListener{

    private static final String TAG = GameActivity.class.getSimpleName();

    private static final int[] buyButtonIds = {R.id.buy_item1_btn,
            R.id.buy_item2_btn,
            R.id.buy_item3_btn,
            R.id.buy_item4_btn,
            R.id.buy_item5_btn,
            R.id.buy_item6_btn,
            R.id.buy_item7_btn,};

    private static final int[] itemCountIds = {R.id.item1_count_tv,
            R.id.item2_count_tv,
            R.id.item3_count_tv,
            R.id.item4_count_tv,
            R.id.item5_count_tv,
            R.id.item6_count_tv,
            R.id.item7_count_tv,};


    private static final int[] itemDMoneys = {0, 1, 2, 4, 8, 16, 320,};

    private static final int[] itemPrices = {0, 20, 80, 320, 1280, 5120, 204800};

    private static int[] itemsCount;

    private Button[] buyButtons;
    private TextView[] itemsCountText;
    private TextView moneyText;

    private MediaPlayer backgroundMedia;

    private Handler handler;

    private static long money, dMoney, totalMoney;
    private static int clickCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game);

        initView();
    }

    private void initView() {
        money = 0;
        dMoney = 0;
        totalMoney = 0;
        clickCount = 0;

        buyButtons = new Button[buyButtonIds.length];
        itemsCountText = new TextView[itemCountIds.length];
        itemsCount = new int[itemCountIds.length];

        for(int i = 0; i < buyButtonIds.length; i++){
            buyButtons[i] = (Button) findViewById(buyButtonIds[i]);
            buyButtons[i].setOnClickListener(this);
            if(i!=0) {
                String currentText = buyButtons[i].getText().toString();
                buyButtons[i].setText(currentText + "\n(" + itemPrices[i] + "$  +"+itemDMoneys[i]+"$/c)");
            }

        }
        for(int i = 0; i < itemCountIds.length; i++){
            itemsCountText[i] = (TextView) findViewById(itemCountIds[i]);
        }

        moneyText = (TextView) findViewById(R.id.main_money_tv);

        handler = new Handler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        printTotalStat();
        prepareButtons();
        startBallanceChecker();

        turnBackgroundMusic();
    }

    @Override
    protected void onPause() {
        stopBallanceChecker();
        if(backgroundMedia!=null){
            backgroundMedia.stop();
        }
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        for(int i = 0; i < buyButtonIds.length; i++)
        if(buyButtonIds[i] == view.getId()){
            clickCount++;
            if(i==0) {
                money++;
                itemsCount[i]++;
                totalMoney++;
            }
            else{
                money -= itemPrices[i];
                itemsCount[i]++;
                dMoney += itemDMoneys[i];

                if(itemsCount[i]==1){
                    turnSoundEffect();//перший раз купив
                }

                if(i==buyButtonIds.length-1){
                    showDialog();
                }

            }

            turnVibro();
        }
        printTotalStat();
        prepareButtons();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setTitle("Вітаю!")
                .setMessage("Ви розширили свою економічні імперію та виграли!\nКількість кліків: " + clickCount)
                .setIcon(R.drawable.dollar)
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void turnVibro(){
        if(AppData.isVibroOn) {
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 100 milliseconds
            v.vibrate(100);
        }
    }

    private void turnSoundEffect(){
        if(AppData.isSoundEffectOn) {
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.picked_coin);
            mp.start();
        }
    }

    private void turnBackgroundMusic(){
        if(AppData.isSoundOn) {
            backgroundMedia = MediaPlayer.create(getApplicationContext(), R.raw.splash);
            backgroundMedia.setLooping(true);
            backgroundMedia.start();
        }
    }

    private void printTotalStat() {
        moneyText.setText(money + "$  загалом:" + totalMoney+"$");
        for(int i = 0; i < itemCountIds.length; i++){
            itemsCountText[i].setText(""+itemsCount[i]);
        }
    }

    private void prepareButtons() {
        for(int i = 0; i < buyButtonIds.length; i++)
            if(money < itemPrices[i])
                buyButtons[i].setEnabled(false);
            else
                buyButtons[i].setEnabled(true);
    }

    //ballance checker
    Runnable mBallanceChecker = new Runnable() {
        @Override
        public void run() {
            updateBallance();
            //restart each updateInterval millis
            handler.postDelayed(mBallanceChecker, 1*1000);
        }
    };

    private void updateBallance() {
        money += dMoney;
        totalMoney += dMoney;
        printTotalStat();
        prepareButtons();
    }

    void startBallanceChecker() {
        mBallanceChecker.run();
    }

    void stopBallanceChecker() {
        handler.removeCallbacks(mBallanceChecker);
    }
}
