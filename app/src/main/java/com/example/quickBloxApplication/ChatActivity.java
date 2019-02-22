package com.example.quickBloxApplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();

        String userEmail = intent.getStringExtra(Constants.KEY_USER_EMAIL);

        Log.d("ChatActivity", "onCreate: " + userEmail);
    }
}
