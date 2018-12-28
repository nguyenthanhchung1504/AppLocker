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
import android.view.View;
import android.widget.Toast;

import com.applocker.applockmanager.R;
import com.applocker.applockmanager.utils.Constant;
import com.applocker.applockmanager.utils.SharedPreferenceUtils;

public class ConfirmBackupPassword extends CreatePinActivity {
    private String confirmBackupPassword;
    private String backupPassword;
    private Vibrator v;
    private int num;
    private int number_entered;
    private ProgressDialog progressDialog;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txtCreateYourPassword.setText(getString(R.string.confirm_change_password));
        txtCreateYourPassword.setVisibility(View.INVISIBLE);
        txtTwo.setVisibility(View.VISIBLE);
        txtTwo.setText(getString(R.string.confirm_change_password));
        utils = new SharedPreferenceUtils(this);
        backupPassword = utils.getStringValue(Constant.BACKUP_PASSWORD,"");
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPassword();
            }
        });
        num = utils.getIntValue(Constant.NUMBER_ENTERED, 3);
        mediaPlayer = new MediaPlayer();

    }
//    private void errorNumber() {
//        int error_number = utils.getIntValue(Constant.SAVE_ERROR_NUMBER,0);
//        if (error_number==num){
//            mediaPlayer = MediaPlayer.create(this,R.raw.nhungbanchanlangle);
//            mediaPlayer.start();
//        }else if (error_number>num){
//            Intent startMain = new Intent(Intent.ACTION_MAIN);
//            startMain.addCategory(Intent.CATEGORY_HOME);
//            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(startMain);
//        }
//    }
    private void requestPassword(){
        confirmBackupPassword = edt1.getText().toString() + edt2.getText().toString() + edt3.getText().toString() + edt4.getText().toString() + edt5.getText().toString();
        if (confirmBackupPassword.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.app_name));
            builder.setMessage(R.string.blank_password  );
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
            if (confirmBackupPassword.equals(backupPassword)) {
                utils.setValue(Constant.CONFIRM_BACKUP_PASSWORD, confirmBackupPassword);
                startActivity(new Intent(this, SettingActivity.class));
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
