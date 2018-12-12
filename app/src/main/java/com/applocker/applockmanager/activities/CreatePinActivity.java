package com.applocker.applockmanager.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.applocker.R;
import com.applocker.applockmanager.utils.Constant;
import com.applocker.applockmanager.utils.SharedPreferenceUtils;
import com.bumptech.glide.Glide;

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
    protected Button txtOk;
    @BindView(R.id.img_zero)
    ImageView imgZero;
    @BindView(R.id.img_delete)
    ImageView imgDelete;
    @BindView(R.id.img_four)
    ImageView imgFour;
    @BindView(R.id.img_five)
    ImageView imgFive;
    @BindView(R.id.img_six)
    ImageView imgSix;
    @BindView(R.id.img_seven)
    ImageView imgSeven;
    @BindView(R.id.img_eight)
    ImageView imgEight;
    @BindView(R.id.img_nine)
    ImageView imgNine;
    @BindView(R.id.img_one)
    ImageView imgOne;
    @BindView(R.id.img_two)
    ImageView imgTwo;
    @BindView(R.id.img_three)
    ImageView imgThree;
    @BindView(R.id.background_lock)
    ConstraintLayout backgroundLock;
    private String password;

    protected SharedPreferenceUtils utils;

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
        int pos = utils.getIntValue(Constant.CHANGE_THEME, 0);
        if (pos == 0) {
            themeDefault();
        }
        if (pos == 1) {
            theme2();
        }
        if (pos == 2) {
            theme3();
        }
        if (pos == 3) {
            theme4();
        }
        if (pos == 4) {
            theme5();
        }
        if (pos == 5) {
            theme6();
        }

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
        if (password.isEmpty()) {
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
        } else {
            utils.setValue(Constant.PASSWORD_CREATE, password);
            startActivity(new Intent(this, ConfirmPasswordActivity.class));
        }

    }

    public void themeDefault() {
        backgroundLock.setBackgroundResource(R.drawable.bg_1);
        Glide.with(this).load(R.drawable.ic_n_delete).into(imgDelete);
        Glide.with(this).load(R.drawable.ic_n_0).into(imgZero);
        Glide.with(this).load(R.drawable.ic_n1).into(imgOne);
        Glide.with(this).load(R.drawable.ic_n2).into(imgTwo);
        Glide.with(this).load(R.drawable.ic_n3).into(imgThree);
        Glide.with(this).load(R.drawable.ic_n4).into(imgFour);
        Glide.with(this).load(R.drawable.ic_n5).into(imgFive);
        Glide.with(this).load(R.drawable.ic_n6).into(imgSix);
        Glide.with(this).load(R.drawable.ic_n7).into(imgSeven);
        Glide.with(this).load(R.drawable.ic_n8).into(imgEight);
        Glide.with(this).load(R.drawable.ic_n9).into(imgNine);
    }

    public void theme2() {
        backgroundLock.setBackgroundResource(R.drawable.bg_2);
        Glide.with(this).load(R.drawable.ic_2delete).into(imgDelete);
        Glide.with(this).load(R.drawable.ic_20).into(imgZero);
        Glide.with(this).load(R.drawable.ic_21).into(imgOne);
        Glide.with(this).load(R.drawable.ic_22).into(imgTwo);
        Glide.with(this).load(R.drawable.ic_23).into(imgThree);
        Glide.with(this).load(R.drawable.ic_24).into(imgFour);
        Glide.with(this).load(R.drawable.ic_25).into(imgFive);
        Glide.with(this).load(R.drawable.ic_26).into(imgSix);
        Glide.with(this).load(R.drawable.ic_27).into(imgSeven);
        Glide.with(this).load(R.drawable.ic_28).into(imgEight);
        Glide.with(this).load(R.drawable.ic_29).into(imgNine);

    }

    public void theme3() {
        backgroundLock.setBackgroundResource(R.drawable.bg_3);
        Glide.with(this).load(R.drawable.ic_3delete).into(imgDelete);
        Glide.with(this).load(R.drawable.ic_30).into(imgZero);
        Glide.with(this).load(R.drawable.ic_31).into(imgOne);
        Glide.with(this).load(R.drawable.ic_32).into(imgTwo);
        Glide.with(this).load(R.drawable.ic_33).into(imgThree);
        Glide.with(this).load(R.drawable.ic_34).into(imgFour);
        Glide.with(this).load(R.drawable.ic_35).into(imgFive);
        Glide.with(this).load(R.drawable.ic_36).into(imgSix);
        Glide.with(this).load(R.drawable.ic_37).into(imgSeven);
        Glide.with(this).load(R.drawable.ic_38).into(imgEight);
        Glide.with(this).load(R.drawable.ic_39).into(imgNine);
    }

    public void theme4() {
        backgroundLock.setBackgroundResource(R.drawable.bg_4);
        Glide.with(this).load(R.drawable.ic_4delete).into(imgDelete);
        Glide.with(this).load(R.drawable.ic_40).into(imgZero);
        Glide.with(this).load(R.drawable.ic_41).into(imgOne);
        Glide.with(this).load(R.drawable.ic_42).into(imgTwo);
        Glide.with(this).load(R.drawable.ic_43).into(imgThree);
        Glide.with(this).load(R.drawable.ic_44).into(imgFour);
        Glide.with(this).load(R.drawable.ic_45).into(imgFive);
        Glide.with(this).load(R.drawable.ic_46).into(imgSix);
        Glide.with(this).load(R.drawable.ic_47).into(imgSeven);
        Glide.with(this).load(R.drawable.ic_48).into(imgEight);
        Glide.with(this).load(R.drawable.ic_49).into(imgNine);
    }

    public void theme5() {
        backgroundLock.setBackgroundResource(R.drawable.bg_5);
        Glide.with(this).load(R.drawable.ic_5delete).into(imgDelete);
        Glide.with(this).load(R.drawable.ic_50).into(imgZero);
        Glide.with(this).load(R.drawable.ic_51).into(imgOne);
        Glide.with(this).load(R.drawable.ic_52).into(imgTwo);
        Glide.with(this).load(R.drawable.ic_53).into(imgThree);
        Glide.with(this).load(R.drawable.ic_54).into(imgFour);
        Glide.with(this).load(R.drawable.ic_55).into(imgFive);
        Glide.with(this).load(R.drawable.ic_56).into(imgSix);
        Glide.with(this).load(R.drawable.ic_57).into(imgSeven);
        Glide.with(this).load(R.drawable.ic_58).into(imgEight);
        Glide.with(this).load(R.drawable.ic_59).into(imgNine);
    }

    public void theme6() {
        backgroundLock.setBackgroundResource(R.drawable.bg_6);
        Glide.with(this).load(R.drawable.ic_6delete).into(imgDelete);
        Glide.with(this).load(R.drawable.ic_60).into(imgZero);
        Glide.with(this).load(R.drawable.ic_61).into(imgOne);
        Glide.with(this).load(R.drawable.ic_62).into(imgTwo);
        Glide.with(this).load(R.drawable.ic_63).into(imgThree);
        Glide.with(this).load(R.drawable.ic_64).into(imgFour);
        Glide.with(this).load(R.drawable.ic_65).into(imgFive);
        Glide.with(this).load(R.drawable.ic_66).into(imgSix);
        Glide.with(this).load(R.drawable.ic_67).into(imgSeven);
        Glide.with(this).load(R.drawable.ic_68).into(imgEight);
        Glide.with(this).load(R.drawable.ic_69).into(imgNine);
    }


}
