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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LogIn extends AppCompatActivity implements TextWatcher, View.OnClickListener {

    private TextInputEditText mLogInEmailNameTxt, mLogInPassWordTxt;
    private Button mLogInBtn, mSignInBtnActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mLogInEmailNameTxt = findViewById(R.id.logInEmailMateriaEditTxt);
        mLogInPassWordTxt = findViewById(R.id.logInPassWordMateriaEditTxt);
        mLogInBtn = findViewById(R.id.logInButton);
        mLogInBtn.setOnClickListener(this);
        mSignInBtnActivity = findViewById(R.id.signInButtonActivity);
        mSignInBtnActivity.setOnClickListener(this);

        mLogInEmailNameTxt.addTextChangedListener(this);
        mLogInPassWordTxt.addTextChangedListener(this);

        mLogInPassWordTxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if(keyCode == KeyEvent.KEYCODE_ENTER
                        && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(mLogInBtn);
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
        if(!(mLogInEmailNameTxt.getText().toString().trim().isEmpty()
                || mLogInPassWordTxt.getText().toString().trim().isEmpty())) {
            mLogInBtn.setEnabled(true);
            mLogInBtn.animate().alpha(1);
        } else if(mLogInBtn.isEnabled()){
            mLogInBtn.setEnabled(false);
            mLogInBtn.animate().alpha(0.5f);
        }
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.logInButton:
                final ParseUser user = new ParseUser();

                user.logInInBackground(mLogInEmailNameTxt.getText().toString(),
                        mLogInPassWordTxt.getText().toString(),
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if(user != null && e == null) {
                                    Toast.makeText(LogIn.this, "usuario logado", Toast.LENGTH_SHORT).show();
                                    transactionToSocialMediaActivity();
                                } else {
                                    Toast.makeText(LogIn.this, e.getMessage() + "", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                break;
            case R.id.signInButtonActivity:
                Intent signInActivity = new Intent(LogIn.this, SignUp.class);
                startActivity(signInActivity);

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
        Intent socialMediaActivity = new Intent(LogIn.this, SocialMediaActivity.class);
        startActivity(socialMediaActivity);

        finish();
    }
}
