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
import android.widget.Toast;

import com.applocker.applockmanager.R;
import com.applocker.applockmanager.utils.Constant;
import com.applocker.applockmanager.utils.SharedPreferenceUtils;

public class CreateBackupPassword extends CreatePinActivity {
    private String backupPassword;
    private Vibrator v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txtCreateYourPassword.setText(getString(R.string.backup_password));
        txtCreateYourPassword.setVisibility(View.INVISIBLE);
        txtTwo.setVisibility(View.VISIBLE);
        txtTwo.setText(getString(R.string.backup_password));
        utils = new SharedPreferenceUtils(this);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPassword();
            }
        });


    }

    private void requestPassword(){
        backupPassword = edt1.getText().toString() + edt2.getText().toString() + edt3.getText().toString() + edt4.getText().toString() + edt5.getText().toString();
        if (backupPassword.isEmpty()){
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
            utils.setValue(Constant.BACKUP_PASSWORD, backupPassword);
            startActivity(new Intent(this, ConfirmBackupPassword.class));
            finish();
        }
    }
}
