package com.example.quickBloxApplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText usernameEditText, passwordEditText, confirmPasswordEditText;
    ProgressBar signUpProgressBar;
    TextView signInTextView;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signUpProgressBar = findViewById(R.id.sign_up_progress);
        usernameEditText = findViewById(R.id.sign_up_username_edittext);
        passwordEditText = findViewById(R.id.sign_up_password_edittext);
        confirmPasswordEditText = findViewById(R.id.sign_up_confirm_password_edittext);
        signInTextView = findViewById(R.id.sign_in_textView);
        signUpButton = findViewById(R.id.sign_up_button);

        signInTextView.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_textView:
                sendUserToSignInActivity();
                break;

            case R.id.sign_up_button:
                String email = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                if (!isValidData(email, password, confirmPassword))
                    return;
                signUpProgressBar.setVisibility(View.VISIBLE);
                registerUser(email, password);
                break;
        }
    }

    private void sendUserToSignInActivity() {
        startActivity(new Intent(RegisterActivity.this, SignInActivity.class));
        finish();
    }

    private void registerUser(String email, String password) {
        final QBUser user = new QBUser();
        user.setEmail(email);
        user.setPassword(password);

        QBUsers.signUp(user).performAsync(new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser qbUser, Bundle bundle) {
                signUpProgressBar.setVisibility(View.GONE);
                Utils.showToast(getApplicationContext(), String.valueOf(R.string.user_successfully_sign_in));
                sendUserToSignInActivity();
            }

            @Override
            public void onError(QBResponseException e) {
                signUpProgressBar.setVisibility(View.GONE);
                Utils.showToast(getApplicationContext(), String.valueOf(R.string.error));
            }
        });
    }

    private boolean isValidData(String login, String password, String confirm) {

        if (TextUtils.isEmpty(login) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirm)) {
            if (TextUtils.isEmpty(login)) {
                usernameEditText.setError(getResources().getString(R.string.error_field_is_empty));
            }
            if (TextUtils.isEmpty(password)) {
                passwordEditText.setError(getResources().getString(R.string.error_field_is_empty));
            }
            if (TextUtils.isEmpty(confirm)) {
                confirmPasswordEditText.setError(getResources().getString(R.string.error_field_is_empty));
            }
            return false;
        }

        if (!TextUtils.equals(password, confirm)) {
            confirmPasswordEditText.setError(getResources().getString(R.string.confirm_error));
            return false;
        }
        return true;
    }
}