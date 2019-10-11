package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUp extends AppCompatActivity implements TextWatcher, View.OnClickListener {

    private TextInputEditText mSignInEmailTxt, mSignInUserNameTxt, mSignInPassWordTxt;
    private Button mSignInBtn, mLogInBtn;
    private ProgressBar mSignInProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_login);

        mSignInEmailTxt = findViewById(R.id.signInEmailMateriaEditTxt);
        mSignInUserNameTxt = findViewById(R.id.signInUserNameMateriaEditTxt);
        mSignInPassWordTxt = findViewById(R.id.signInPassWordMateriaEditTxt);
        mSignInProgressBar = findViewById(R.id.signInProgressBar);

        mSignInUserNameTxt.addTextChangedListener(this);
        mSignInPassWordTxt.addTextChangedListener(this);
        mSignInEmailTxt.addTextChangedListener(this);

        mLogInBtn = findViewById(R.id.logInButtonActivity);
        mLogInBtn.setOnClickListener(this);
        mSignInBtn = findViewById(R.id.signInButton);
        mSignInBtn.setOnClickListener(this);

        mSignInPassWordTxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if(keyCode == KeyEvent.KEYCODE_ENTER
                        && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(mSignInBtn);
                }
                return false;
            }
        });

        if(ParseUser.getCurrentUser() != null) {
//            ParseUser.getCurrentUser().logOut();
            transactionToSocialMediaActivity();
        }

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(!(mSignInUserNameTxt.getText().toString().trim().isEmpty()
                || mSignInEmailTxt.getText().toString().trim().isEmpty()
                || mSignInPassWordTxt.getText().toString().trim().isEmpty())) {
            mSignInBtn.setEnabled(true);
            mSignInBtn.animate().alpha(1);
        } else if(mSignInBtn.isEnabled()){
            mSignInBtn.setEnabled(false);
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.signInButton:
                final ParseUser signIn = new ParseUser();
                signIn.setEmail(mSignInEmailTxt.getText().toString());
                signIn.setUsername(mSignInUserNameTxt.getText().toString());
                signIn.setPassword(mSignInPassWordTxt.getText().toString());

                mSignInProgressBar.setVisibility(View.VISIBLE);

                signIn.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(SignUp.this, "User created with success!", Toast.LENGTH_SHORT).show();
                            transactionToSocialMediaActivity();
                        } else {
                            Toast.makeText(SignUp.this, e.getMessage() + "", Toast.LENGTH_SHORT).show();
                        }
                        mSignInProgressBar.setVisibility(View.GONE);
                    }
                });

                break;
            case R.id.logInButtonActivity:
                Intent logInActivity = new Intent(SignUp.this, LogIn.class);
                startActivity(logInActivity);

                break;
        }
    }

    public void rootLayoutTapped(View rootView) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void transactionToSocialMediaActivity() {
        Intent socialMediaActivity = new Intent(SignUp.this, SocialMediaActivity.class);
        startActivity(socialMediaActivity);
        finish();
    }
}
