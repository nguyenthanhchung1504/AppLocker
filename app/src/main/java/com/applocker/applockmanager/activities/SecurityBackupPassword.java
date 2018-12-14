package com.applocker.applockmanager.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.applocker.R;
import com.applocker.applockmanager.utils.Constant;
import com.applocker.applockmanager.utils.SharedPreferenceUtils;

public class SecurityBackupPassword extends CreatePinActivity {
    private String passwordRequest;
    private String passConfirm;
    private String passBackup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txtCreateYourPassword.setText(getString(R.string.protect_your_privacy_and_secrets));
        utils = new SharedPreferenceUtils(this);
        passConfirm = utils.getStringValue(Constant.PASSWORD_CONFIRM,"");
        passBackup = utils.getStringValue(Constant.CONFIRM_BACKUP_PASSWORD,"123123123123123");
        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPassword();
            }
        });

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
        }
        else {
            if (passwordRequest.equals(passConfirm) || passwordRequest.equals(passBackup)) {
                startActivity(new Intent(this, CreateBackupPassword.class));
                finish();

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.app_name));
                builder.setMessage(R.string.wrong_password);
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
            }
        }
    }
}
