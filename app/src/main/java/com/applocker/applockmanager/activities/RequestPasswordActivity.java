package com.applocker.applockmanager.activities;

import android.os.Bundle;

import com.applocker.R;

public class RequestPasswordActivity extends CreatePinActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txtCreateYourPassword.setText(getString(R.string.protect_your_privacy_and_secrets));
    }
}
