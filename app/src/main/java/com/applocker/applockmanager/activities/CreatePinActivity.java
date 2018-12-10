package com.applocker.applockmanager.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.applocker.R;
import com.applocker.applockmanager.utils.Constant;
import com.applocker.applockmanager.utils.SharedPreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreatePinActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    @BindView(R.id.edt1)
    protected EditText edt1;
    @BindView(R.id.edt2)
    protected EditText edt2;
    @BindView(R.id.edt3)
    protected EditText edt3;
    @BindView(R.id.edt4)
    protected EditText edt4;
    @BindView(R.id.edt5)
    protected EditText edt5;
    @BindView(R.id.txt_create_your_password)
    protected TextView txtCreateYourPassword;
    @BindView(R.id.txt_ok)
    protected TextView txtOk;
    private String password;

    private SharedPreferenceUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pin);
        ButterKnife.bind(this);
        edt1.setOnTouchListener(this);
        edt2.setOnTouchListener(this);
        edt3.setOnTouchListener(this);
        edt4.setOnTouchListener(this);
        edt5.setOnTouchListener(this);
        edt1.setEnabled(false);
        edt2.setEnabled(false);
        edt3.setEnabled(false);
        edt4.setEnabled(false);
        edt5.setEnabled(false);

        utils = new SharedPreferenceUtils(this);
        txtCreateYourPassword.setText(getString(R.string.create_your_password));


    }

    @OnClick({R.id.txt_ok, R.id.img_zero, R.id.img_delete, R.id.img_four, R.id.img_five, R.id.img_six, R.id.img_seven, R.id.img_eight, R.id.img_nine, R.id.img_one, R.id.img_two, R.id.img_three})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_ok:
                getPassword();
                break;
            case R.id.img_zero:
                setPassword(0);
                break;
            case R.id.img_delete:
                deletePassword();
                break;
            case R.id.img_four:
                setPassword(4);
                break;
            case R.id.img_five:
                setPassword(5);
                break;
            case R.id.img_six:
                setPassword(6);
                break;
            case R.id.img_seven:
                setPassword(7);
                break;
            case R.id.img_eight:
                setPassword(8);
                break;
            case R.id.img_nine:
                setPassword(9);
                break;
            case R.id.img_one:
                setPassword(1);
                break;
            case R.id.img_two:
                setPassword(2);
                break;
            case R.id.img_three:
                setPassword(3);
                break;
        }
    }

    private void setPassword(int number) {
        if (TextUtils.isEmpty(edt1.getText().toString())) {
            edt1.setText(number + "");
            edt2.requestFocus();
        } else if (TextUtils.isEmpty(edt2.getText().toString())) {
            edt2.setText(number + "");
            edt3.requestFocus();
        } else if (TextUtils.isEmpty(edt3.getText().toString())) {
            edt3.setText(number + "");
            edt4.requestFocus();
        } else if (TextUtils.isEmpty(edt4.getText().toString())) {
            edt4.setText(number + "");
            edt5.requestFocus();
        } else if (TextUtils.isEmpty(edt5.getText().toString())) {
            edt5.setText(number + "");
        }
    }

    private void deletePassword() {
        if (!TextUtils.isEmpty(edt5.getText().toString())) {
            edt5.setText("");
            edt4.requestFocus();
        } else if (!TextUtils.isEmpty(edt4.getText().toString())) {
            edt4.setText("");
            edt3.requestFocus();
        } else if (!TextUtils.isEmpty(edt3.getText().toString())) {
            edt3.setText("");
            edt2.requestFocus();
        } else if (!TextUtils.isEmpty(edt2.getText().toString())) {
            edt2.setText("");
            edt1.requestFocus();
        } else if (!TextUtils.isEmpty(edt1.getText().toString())) {
            edt1.requestFocus();
            edt1.setText("");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    private void disableKeyBoard(EditText edt, MotionEvent event) {
        int inType4 = edt.getInputType(); // backup the input type
        edt.setInputType(InputType.TYPE_NULL); // disable soft input
        edt.onTouchEvent(event); // call native handler
        edt.setInputType(inType4); // restore input type
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.edt1:
                disableKeyBoard(edt1, event);
                return true; // consume touch even
            case R.id.edt2:
                disableKeyBoard(edt2, event);
                return true; // consume touch even
            case R.id.edt3:
                disableKeyBoard(edt3, event);
                return true; // consume touch even
            case R.id.edt4:
                disableKeyBoard(edt4, event);
                return true; // consume touch even
            case R.id.edt5:
                disableKeyBoard(edt5, event);
                return true; // consume touch even
        }
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    private void getPassword() {
        password = edt1.getText().toString() + edt2.getText().toString() + edt3.getText().toString() + edt4.getText().toString() + edt5.getText().toString();
        if (password.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.app_name));
            builder.setMessage(R.string.please_enter_your_password);
            builder.setCancelable(false);
            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else {
            utils.setValue(Constant.PASSWORD_CREATE, password);
            startActivity(new Intent(this, ConfirmPasswordActivity.class));
        }

    }
}
