package com.applocker.applockmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.applocker.R;
import com.applocker.applockmanager.utils.Constant;
import com.applocker.applockmanager.utils.SharedPreferenceUtils;

public class ChangePasswordActivity extends CreatePinActivity {
    private String changePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txtCreateYourPassword.setText(getString(R.string.change_password));
        utils = new SharedPreferenceUtils(this);
        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPassword();
            }
        });


    }

    private void requestPassword(){
        changePassword = edt1.getText().toString() + edt2.getText().toString() + edt3.getText().toString() + edt4.getText().toString() + edt5.getText().toString();
        utils.setValue(Constant.CHANGE_PASSWORD,changePassword);
        startActivity(new Intent(this,ConfirmChangePassword.class));
        finish();
    }
}
