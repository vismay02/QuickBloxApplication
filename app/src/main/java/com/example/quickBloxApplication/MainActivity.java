package com.example.quickBloxApplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.quickBloxApplication.adapter.ListUserAdapter;
import com.example.quickBloxApplication.model.Users;
import com.quickblox.core.Consts;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBPagedRequestBuilder;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView usersRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usersRecyclerView = findViewById(R.id.recycler_view_users);

        getUsersFromServer();
    }

    private void recyclerViewTouchEvent(final ArrayList<QBUser> users) {
        usersRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), usersRecyclerView, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                String userEmail = users.get(position).getEmail();
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                intent.putExtra(Constants.KEY_USER_EMAIL, userEmail);
                startActivity(intent);

                Log.d("EMAIL", "onClick: " + users.get(position).getEmail());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void getUsersFromServer() {
        QBPagedRequestBuilder pagedRequestBuilder = new QBPagedRequestBuilder();
        pagedRequestBuilder.setPage(1);
        pagedRequestBuilder.setPerPage(100);

        QBUsers.getUsers(pagedRequestBuilder).performAsync(new QBEntityCallback<ArrayList<QBUser>>() {
            @Override
            public void onSuccess(ArrayList<QBUser> users, Bundle params) {
                usersRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                usersRecyclerView.setAdapter(new ListUserAdapter(MainActivity.this, users));

                recyclerViewTouchEvent(users);

                //checkUserStatus(QBUser);
                Log.i("MY_TAG", "Users: " + users.toString());
            }

            @Override
            public void onError(QBResponseException e) {
                Utils.showToast(MainActivity.this, "Unable to retrieve users");
            }
        });
    }

    private void checkUserStatus(ArrayList<QBUser> users) {

    }

    private void logoutUser() {
        QBUsers.signOut().performAsync(new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid, Bundle bundle) {
                Log.d(SignInActivity.TAG, "onSuccess: ");
                Utils.showToast(getApplicationContext(), "Logged out successfully");
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
                finish();
            }

            @Override
            public void onError(QBResponseException e) {
                Log.d(SignInActivity.TAG, "onError: " + e.getMessage());
                Utils.showToast(getApplicationContext(), "Error logging out");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_menu:
                logoutUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }
}