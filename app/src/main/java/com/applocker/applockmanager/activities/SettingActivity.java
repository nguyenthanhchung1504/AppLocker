package com.applocker.applockmanager.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.applocker.applockmanager.R;
import com.applocker.applockmanager.service.AppLockService;
import com.applocker.applockmanager.utils.Constant;
import com.applocker.applockmanager.utils.SharedPreferenceUtils;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.txt_change_password)
    TextView txtChangePassword;
    @BindView(R.id.txt_change_theme)
    TextView txtChangeTheme;
    @BindView(R.id.layout_setting)
    ConstraintLayout layoutSetting;
    @BindView(R.id.sw_on_off)
    ToggleButton swOnOff;
    @BindView(R.id.sw_sound)
    ToggleButton swSound;
    @BindView(R.id.btn_number)
    ElegantNumberButton btnNumber;
    private SharedPreferenceUtils utils;
    private boolean checkOnOf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        swOnOff.setText(null);
        swOnOff.setTextOn(null);
        swOnOff.setTextOff(null);
        swSound.setText(null);
        swSound.setTextOn(null);
        swSound.setTextOff(null);
        utils = new SharedPreferenceUtils(this);
        int pos = utils.getIntValue(Constant.CHANGE_THEME, 0);
        if (pos == 0) {
            layoutSetting.setBackgroundResource(R.drawable.bg_1);
        }
        if (pos == 1) {
            layoutSetting.setBackgroundResource(R.drawable.bg_2);
        }
        if (pos == 2) {
            layoutSetting.setBackgroundResource(R.drawable.bg_3);
        }
        if (pos == 3) {
            layoutSetting.setBackgroundResource(R.drawable.bg_4);
        }
        if (pos == 4) {
            layoutSetting.setBackgroundResource(R.drawable.bg_5);
        }
        if (pos == 5) {
            layoutSetting.setBackgroundResource(R.drawable.bg_6);
        }
        checkOnOf = utils.getBoolanValue(Constant.SWITCH_ON_OFF, true);
        swOnOff.setChecked(checkOnOf);

        int save_num = utils.getIntValue(Constant.NUMBER_ENTERED,3);
        btnNumber.setNumber(String.valueOf(save_num));

        if (checkOnOf == true) {
            swOnOff.setBackgroundResource(R.drawable.ic_on);
            Intent intent = new Intent(this, AppLockService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent);
            } else {
                startService(intent);
            }
        } else if (checkOnOf == false){
           swOnOff.setBackgroundResource(R.drawable.ic_off);
            Intent intent = new Intent(this, AppLockService.class);
            stopService(intent);
        }



        boolean checkSound;
        checkSound = utils.getBoolanValue(Constant.SWITCH_SOUND,true);
        if (checkSound == true){
            swSound.setBackgroundResource(R.drawable.ic_on);

        }
        else {
            swSound.setBackgroundResource(R.drawable.ic_off);

        }


        swOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    swOnOff.setChecked(true);
                    swOnOff.setBackgroundResource(R.drawable.ic_on);
                    utils.setValue(Constant.SWITCH_ON_OFF, true);
                    startActivity(new Intent(SettingActivity.this, SecurityOnLockApp.class));
                    finish();
                } else {
                    swOnOff.setChecked(false);
                    swOnOff.setBackgroundResource(R.drawable.ic_off);
                    startActivity(new Intent(SettingActivity.this, SecurityAppActivity.class));
                    finish();
                }
            }
        });

        swSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    swSound.setBackgroundResource(R.drawable.ic_on);
                    swSound.setChecked(true);
                    utils.setValue(Constant.SWITCH_SOUND,true);
                } else {
                    swSound.setBackgroundResource(R.drawable.ic_off);
                    swSound.setChecked(false);
                    utils.setValue(Constant.SWITCH_SOUND,false);
                }
            }
        });

        btnNumber.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.parseInt(btnNumber.getNumber());
                utils.setValue(Constant.NUMBER_ENTERED,num);

            }
        });
    }

    @OnClick({R.id.layout_change_password, R.id.layout_change_theme, R.id.layout_backup_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_change_password:
                startActivity(new Intent(this, SecurityChangePassword.class));
                finish();
                break;
            case R.id.layout_change_theme:
                startActivity(new Intent(this, ChangeThemeActivity.class));
                finish();
                break;
            case R.id.layout_backup_password:
                startActivity(new Intent(this, SecurityBackupPassword.class));
                finish();
                break;
        }
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage(getString(R.string.loading_data));
        progress.show();

        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                startActivity(new Intent(SettingActivity.this, AppList.class));
                finish();
                progress.cancel();
            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 3000);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            final ProgressDialog progress = new ProgressDialog(this);
            progress.setMessage(getString(R.string.loading_data));
            progress.show();

            Runnable progressRunnable = new Runnable() {

                @Override
                public void run() {
                    startActivity(new Intent(SettingActivity.this, AppList.class));
                    finish();
                    progress.cancel();
                }
            };

            Handler pdCanceller = new Handler();
            pdCanceller.postDelayed(progressRunnable, 3000);
        }
        if (keyCode == event.KEYCODE_HOME) {
            stopService(new Intent(this, AppLockService.class));
        }
        return false;
    }


    @Override
    protected void onPause() {
        super.onPause();
        finishAffinity();

    }

    @Override
    protected void onStop() {
        super.onStop();
        finishAffinity();
    }
}
