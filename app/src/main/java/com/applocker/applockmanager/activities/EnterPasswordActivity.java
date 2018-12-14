package com.applocker.applockmanager.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.applocker.R;
import com.applocker.applockmanager.service.AppLockService;
import com.applocker.applockmanager.utils.Constant;
import com.applocker.applockmanager.utils.SharedPreferenceUtils;

public class EnterPasswordActivity extends CreatePinActivity {
    private String passwordRequest;
    private String passConfirm;
    private String passBackup;
    private Vibrator v;
    private boolean check_on_off;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txtCreateYourPassword.setText(getString(R.string.protect_your_privacy_and_secrets));
        utils = new SharedPreferenceUtils(this);
        passConfirm = utils.getStringValue(Constant.PASSWORD_CONFIRM,"");
        passBackup = utils.getStringValue(Constant.CONFIRM_BACKUP_PASSWORD,"");
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPassword();
            }
        });
        check_on_off = utils.getBoolanValue(Constant.SWITCH_ON_OFF,true);
        if (check_on_off == true){
            Intent intent = new Intent(this, AppLockService.class);
            startService(intent);
        }
        else {
            stopService(new Intent(this,AppLockService.class));
        }

    }

    private void requestPassword(){
        passwordRequest = edt1.getText().toString() + edt2.getText().toString() + edt3.getText().toString() + edt4.getText().toString() + edt5.getText().toString();
        if (passwordRequest.isEmpty()){
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
            if (passwordRequest.equals(passConfirm) || passwordRequest.equals(passBackup)) {
                progressBar.setVisibility(View.VISIBLE);
                startActivity(new Intent(this, AppList.class));
                finish();

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.app_name));
                builder.setMessage(R.string.wrong_password);
                builder.setCancelable(false);
                builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                edt1.setText(null);
                edt2.setText(null);
                edt3.setText(null);
                edt4.setText(null);
                edt5.setText(null);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(2000, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    v.vibrate(2000);
                }
            }
        }
    }
}
