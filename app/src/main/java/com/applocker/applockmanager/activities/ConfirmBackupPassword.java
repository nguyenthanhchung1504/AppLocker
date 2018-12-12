package com.applocker.applockmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.applocker.R;
import com.applocker.applockmanager.utils.Constant;
import com.applocker.applockmanager.utils.SharedPreferenceUtils;

public class ConfirmBackupPassword extends CreatePinActivity {
    private String confirmBackupPassword;
    private String backupPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txtCreateYourPassword.setText(getString(R.string.confirm_change_password));
        utils = new SharedPreferenceUtils(this);
        backupPassword = utils.getStringValue(Constant.BACKUP_PASSWORD,"");
        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPassword();
            }
        });


    }

    private void requestPassword(){
        confirmBackupPassword = edt1.getText().toString() + edt2.getText().toString() + edt3.getText().toString() + edt4.getText().toString() + edt5.getText().toString();
        if (confirmBackupPassword.equals(backupPassword)){
            utils.setValue(Constant.CONFIRM_BACKUP_PASSWORD, confirmBackupPassword);
            startActivity(new Intent(this,SettingActivity.class));
            finish();
        }
        else {
            Toast.makeText(this, getString(R.string.not_correct_password_create), Toast.LENGTH_LONG).show();
        }
    }
}
