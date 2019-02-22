package com.example.quickBloxApplication;

import android.content.Context;
import android.widget.Toast;

class Utils {

    static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}