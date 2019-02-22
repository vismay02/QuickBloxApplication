package com.example.quickBloxApplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.quickblox.auth.session.QBSettings;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.StoringMechanism;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    EditText loginEditText, passwordEditText;
    Button signInButton;
    TextView registerUser;
    ProgressBar progressBar;
    public static final String TAG = "MY_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initViews();
    }

    private void initViews() {
        loginEditText = findViewById(R.id.username_edittext);
        passwordEditText = findViewById(R.id.password_in_edittext);
        signInButton = findViewById(R.id.sign_in_button);
        registerUser = findViewById(R.id.register_user_textView);
        progressBar = findViewById(R.id.progress_bar);

        signInButton.setOnClickListener(this);
        registerUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                progressBar.setVisibility(View.VISIBLE);
                loginUser();
                break;

            case R.id.register_user_textView:
                takeUserToRegisterActivity();
                break;
        }
    }

    private void takeUserToRegisterActivity() {
        startActivity(new Intent(SignInActivity.this, RegisterActivity.class));
    }

    private void loginUser() {
        String email = loginEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();

        QBUser qbUser = new QBUser();
        qbUser.setEmail(email);
        qbUser.setPassword(password);

        QBUsers.signIn(qbUser).performAsync(new QBEntityCallback<QBUser>() {

            @Override
            public void onSuccess(QBUser qbUser, Bundle bundle) {
                progressBar.setVisibility(View.GONE);
                Utils.showToast(getApplicationContext(), "Logging in...");
                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onError(QBResponseException e) {
                Log.d(TAG, "onError: " + e.getMessage());
                progressBar.setVisibility(View.GONE);
                Utils.showToast(getApplicationContext(), "User not registered");
            }
        });
    }
}