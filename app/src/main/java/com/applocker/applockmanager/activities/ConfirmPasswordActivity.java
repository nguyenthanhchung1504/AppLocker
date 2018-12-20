package com.applocker.applockmanager.activities;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;


import com.applocker.applockmanager.R;
import com.applocker.applockmanager.utils.Constant;
import com.applocker.applockmanager.utils.SharedPreferenceUtils;

public class ConfirmPasswordActivity extends CreatePinActivity {
    private SharedPreferenceUtils utils;
    private String passwordConfirm;
    private String passwordCreate;
    private Vibrator v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txtCreateYourPassword.setText(getString(R.string.confirm_your_password));
        txtCreateYourPassword.setVisibility(View.INVISIBLE);
        txtTwo.setVisibility(View.VISIBLE);
        txtTwo.setText(getString(R.string.confirm_change_password));
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        utils = new SharedPreferenceUtils(this);
        passwordCreate = utils.getStringValue(Constant.PASSWORD_CREATE,"");
        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOk();
            }
        });

    }

    private void onClickOk(){
        passwordConfirm = edt1.getText().toString() + edt2.getText().toString() + edt3.getText().toString() + edt4.getText().toString() + edt5.getText().toString();
        if (passwordConfirm.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.app_name));
            builder.setMessage(R.string.blank_password);
            builder.setCancelable(false);
            builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(2000, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(2000);
            }
        }
        else {
            if (passwordConfirm.equals(passwordCreate)) {
                utils.setValue(Constant.PASSWORD_CONFIRM, passwordConfirm);
                utils.setValue(Constant.PREFERENCES_SECOND, true);
                startActivity(new Intent(this, AppList.class));
                finish();
            } else {
                edt1.setText(null);
                edt2.setText(null);
                edt3.setText(null);
                edt4.setText(null);
                edt5.setText(null);
                edt1.setBackgroundResource(R.drawable.circle_textview);
                edt2.setBackgroundResource(R.drawable.circle_textview);
                edt3.setBackgroundResource(R.drawable.circle_textview);
                edt4.setBackgroundResource(R.drawable.circle_textview);
                edt5.setBackgroundResource(R.drawable.circle_textview);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(2000, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    v.vibrate(2000);
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.app_name));
                builder.setMessage(R.string.not_correct_password_create);
                builder.setCancelable(false);
                builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }
    }


}
