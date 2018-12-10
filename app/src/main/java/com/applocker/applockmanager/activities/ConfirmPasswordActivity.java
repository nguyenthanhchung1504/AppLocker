package com.applocker.applockmanager.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.applocker.R;
import com.applocker.applockmanager.utils.Constant;
import com.applocker.applockmanager.utils.SharedPreferenceUtils;

import butterknife.OnClick;
public class ConfirmPasswordActivity extends CreatePinActivity {
    private SharedPreferenceUtils utils;
    private String passwordConfirm;
    private String passwordCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txtCreateYourPassword.setText(getString(R.string.confirm_your_password));

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
        if (passwordConfirm.equals(passwordCreate)){
            utils.setValue(Constant.PASSWORD_CONFIRM,passwordConfirm);
            utils.setValue(Constant.PREFERENCES_SECOND,true);
            startActivity(new Intent(this,ListApplicationActivity.class));
            finish();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.app_name));
            builder.setMessage(R.string.not_correct_password_create);
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
