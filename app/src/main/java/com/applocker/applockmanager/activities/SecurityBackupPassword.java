package com.applocker.applockmanager.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;


import com.applocker.applockmanager.R;
import com.applocker.applockmanager.utils.Constant;
import com.applocker.applockmanager.utils.SharedPreferenceUtils;

public class SecurityBackupPassword extends CreatePinActivity {
    private String passwordRequest;
    private String passConfirm;
    private String passBackup;
    private Vibrator v;
    private int num;
    private int number_entered;
    private ProgressDialog progressDialog;
    private MediaPlayer mediaPlayer;
    private boolean sound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txtCreateYourPassword.setText(getString(R.string.protect_your_privacy_and_secrets));
        utils = new SharedPreferenceUtils(this);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        passConfirm = utils.getStringValue(Constant.PASSWORD_CONFIRM,"123123123");
        passBackup = utils.getStringValue(Constant.CONFIRM_BACKUP_PASSWORD,"123123123123123");
        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPassword();
            }
        });
        num = utils.getIntValue(Constant.NUMBER_ENTERED, 3);
        mediaPlayer = new MediaPlayer();
        sound = utils.getBoolanValue(Constant.SWITCH_SOUND,true);
    }

    private void errorNumber() {
        int error_number = utils.getIntValue(Constant.SAVE_ERROR_NUMBER,0);
        if (error_number==num){
            if (sound == true){
                mediaPlayer = MediaPlayer.create(this,R.raw.nhungbanchanlangle);
                mediaPlayer.start();
            }
            else {
                mediaPlayer = MediaPlayer.create(this,R.raw.nhungbanchanlangle);
                mediaPlayer.stop();
            }
        }else if (error_number>num){
            System.exit(0);
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
                    startActivity(new Intent(SecurityBackupPassword.this,SettingActivity.class));
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
                startActivity(new Intent(this, CreateBackupPassword.class));
                finish();
                utils.setValue(Constant.SAVE_ERROR_NUMBER,0);

            } else {
                number_entered = utils.getIntValue(Constant.SAVE_ERROR_NUMBER,0);
                number_entered = number_entered +1;
                utils.setValue(Constant.SAVE_ERROR_NUMBER,number_entered);
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
                errorNumber();
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
            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK){
            startActivity(new Intent(this,SettingActivity.class));
            finish();
        }
        return false;
    }
}
