package com.applocker.applockmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.applocker.R;
import com.applocker.applockmanager.utils.Constant;
import com.applocker.applockmanager.utils.SharedPreferenceUtils;

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
    Switch swOnOff;
    private SharedPreferenceUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

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
        swOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

    }

    @OnClick({R.id.layout_change_password, R.id.layout_change_theme, R.id.layout_backup_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_change_password:
                startActivity(new Intent(this, ChangePasswordActivity.class));
                break;
            case R.id.layout_change_theme:
                startActivity(new Intent(this, ChangeThemeActivity.class));
                break;
            case R.id.layout_backup_password:
                startActivity(new Intent(this, SecurityBackupPassword.class));
                break;
        }
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        startActivity(new Intent(this, AppList.class));
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            startActivity(new Intent(this, AppList.class));
            finish();
        }
        return false;
    }
}
