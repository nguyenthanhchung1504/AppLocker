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

import com.applocker.applockmanager.R;
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
    @BindView(R.id.progressBar)
    protected ProgressBar progressBar;
    @BindView(R.id.txt_two)
    TextView txtTwo;
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
        txtCreateYourPassword.setVisibility(View.INVISIBLE);
        txtTwo.setVisibility(View.VISIBLE);
        txtTwo.setText(getString(R.string.create_your_password));


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
            edt1.setBackgroundResource(R.drawable.effect_password);
            edt2.requestFocus();
        } else if (TextUtils.isEmpty(edt2.getText().toString())) {
            edt2.setText(number + "");
            edt2.setBackgroundResource(R.drawable.effect_password);
            edt3.requestFocus();
        } else if (TextUtils.isEmpty(edt3.getText().toString())) {
            edt3.setText(number + "");
            edt3.setBackgroundResource(R.drawable.effect_password);
            edt4.requestFocus();
        } else if (TextUtils.isEmpty(edt4.getText().toString())) {
            edt4.setText(number + "");
            edt4.setBackgroundResource(R.drawable.effect_password);
            edt5.requestFocus();
        } else if (TextUtils.isEmpty(edt5.getText().toString())) {
            edt5.setText(number + "");
            edt5.setBackgroundResource(R.drawable.effect_password);
        }
    }

    private void deletePassword() {
        if (!TextUtils.isEmpty(edt5.getText().toString())) {
            edt5.setText("");
            edt5.setBackgroundResource(R.drawable.circle_textview);
            edt4.requestFocus();
        } else if (!TextUtils.isEmpty(edt4.getText().toString())) {
            edt4.setText("");
            edt4.setBackgroundResource(R.drawable.circle_textview);
            edt3.requestFocus();
        } else if (!TextUtils.isEmpty(edt3.getText().toString())) {
            edt3.setText("");
            edt3.setBackgroundResource(R.drawable.circle_textview);
            edt2.requestFocus();
        } else if (!TextUtils.isEmpty(edt2.getText().toString())) {
            edt2.setText("");
            edt2.setBackgroundResource(R.drawable.circle_textview);
            edt1.requestFocus();
        } else if (!TextUtils.isEmpty(edt1.getText().toString())) {
            edt1.requestFocus();
            edt1.setBackgroundResource(R.drawable.circle_textview);
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
            builder.setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
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
        imgDelete.setBackgroundResource(R.drawable.normal_del);
        imgZero.setBackgroundResource(R.drawable.normal_zero);
        imgOne.setBackgroundResource(R.drawable.normal_one);
        imgTwo.setBackgroundResource(R.drawable.normal_two);
        imgThree.setBackgroundResource(R.drawable.normal_three);
        imgFour.setBackgroundResource(R.drawable.normal_four);
        imgFive.setBackgroundResource(R.drawable.normal_five);
        imgSix.setBackgroundResource(R.drawable.normal_six);
        imgSeven.setBackgroundResource(R.drawable.normal_seven);
        imgEight.setBackgroundResource(R.drawable.normal_eight);
        imgNine.setBackgroundResource(R.drawable.normal_nine);
    }

    public void theme2() {
        backgroundLock.setBackgroundResource(R.drawable.bg_2);
        imgDelete.setBackgroundResource(R.drawable.ring_delete);
        imgZero.setBackgroundResource(R.drawable.ring_zero);
        imgOne.setBackgroundResource(R.drawable.ring_one);
        imgTwo.setBackgroundResource(R.drawable.ring_two);
        imgThree.setBackgroundResource(R.drawable.ring_three);
        imgFour.setBackgroundResource(R.drawable.ring_four);
        imgFive.setBackgroundResource(R.drawable.ring_five);
        imgSix.setBackgroundResource(R.drawable.ring_six);
        imgSeven.setBackgroundResource(R.drawable.ring_seven);
        imgEight.setBackgroundResource(R.drawable.ring_eight);
        imgNine.setBackgroundResource(R.drawable.ring_nine);

    }

    public void theme3() {
        backgroundLock.setBackgroundResource(R.drawable.bg_3);
        imgDelete.setBackgroundResource(R.drawable.star_delete);
        imgZero.setBackgroundResource(R.drawable.star_zero);
        imgOne.setBackgroundResource(R.drawable.star_one);
        imgTwo.setBackgroundResource(R.drawable.star_two);
        imgThree.setBackgroundResource(R.drawable.star_three);
        imgFour.setBackgroundResource(R.drawable.star_four);
        imgFive.setBackgroundResource(R.drawable.star_five);
        imgSix.setBackgroundResource(R.drawable.star_six);
        imgSeven.setBackgroundResource(R.drawable.star_seven);
        imgEight.setBackgroundResource(R.drawable.star_eight);
        imgNine.setBackgroundResource(R.drawable.star_nine);
    }

    public void theme4() {
        backgroundLock.setBackgroundResource(R.drawable.bg_4);
        imgDelete.setBackgroundResource(R.drawable.pentagon_delete);
        imgZero.setBackgroundResource(R.drawable.pentagon_zero);
        imgOne.setBackgroundResource(R.drawable.pentagon_one);
        imgTwo.setBackgroundResource(R.drawable.pentagon_two);
        imgThree.setBackgroundResource(R.drawable.pentagon_three);
        imgFour.setBackgroundResource(R.drawable.pentagon_four);
        imgFive.setBackgroundResource(R.drawable.pentagon_five);
        imgSix.setBackgroundResource(R.drawable.pentagon_six);
        imgSeven.setBackgroundResource(R.drawable.pentagon_seven);
        imgEight.setBackgroundResource(R.drawable.pentagon_eight);
        imgNine.setBackgroundResource(R.drawable.pentagon_nine);

    }

    public void theme5() {
        backgroundLock.setBackgroundResource(R.drawable.bg_5);
        imgDelete.setBackgroundResource(R.drawable.love_delete);
        imgZero.setBackgroundResource(R.drawable.love_zero);
        imgOne.setBackgroundResource(R.drawable.love_one);
        imgTwo.setBackgroundResource(R.drawable.love_two);
        imgThree.setBackgroundResource(R.drawable.love_three);
        imgFour.setBackgroundResource(R.drawable.love_four);
        imgFive.setBackgroundResource(R.drawable.love_five);
        imgSix.setBackgroundResource(R.drawable.love_six);
        imgSeven.setBackgroundResource(R.drawable.love_seven);
        imgEight.setBackgroundResource(R.drawable.love_eight);
        imgNine.setBackgroundResource(R.drawable.love_nine);

    }

    public void theme6() {
        backgroundLock.setBackgroundResource(R.drawable.bg_6);
        imgDelete.setBackgroundResource(R.drawable.shield_delete);
        imgZero.setBackgroundResource(R.drawable.shield_zero);
        imgOne.setBackgroundResource(R.drawable.shield_one);
        imgTwo.setBackgroundResource(R.drawable.shield_two);
        imgThree.setBackgroundResource(R.drawable.shield_three);
        imgFour.setBackgroundResource(R.drawable.shield_four);
        imgFive.setBackgroundResource(R.drawable.shield_five);
        imgSix.setBackgroundResource(R.drawable.shield_six);
        imgSeven.setBackgroundResource(R.drawable.shield_seven);
        imgEight.setBackgroundResource(R.drawable.shield_eight);
        imgNine.setBackgroundResource(R.drawable.shield_nine);

    }


}
