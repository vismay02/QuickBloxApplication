package com.example.quickBloxApplication.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quickBloxApplication.R;
import com.example.quickBloxApplication.model.Users;
import com.quickblox.core.Consts;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBPagedRequestBuilder;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;
import java.util.List;

public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<QBUser> usersList;
    private QBUser user;

    public ListUserAdapter(Context context, ArrayList<QBUser> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_users, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {

        myViewHolder.userEmailTextView.setText(usersList.get(position).getEmail());

        checkUserStatus(myViewHolder, position);
    }

    private void checkUserStatus(MyViewHolder myViewHolder, int position) {
        user = usersList.get(position);
        long currentTime = System.currentTimeMillis();
        long userLastRequestAtTime = 0;
        if (user.getLastRequestAt() != null) {
            userLastRequestAtTime = user.getLastRequestAt().getTime();
        }

// if user didn't do anything last 5 minutes (5*60*1000 milliseconds)
        if ((currentTime - userLastRequestAtTime) > 5 * 60 * 1000) {
            // user is offline now
            myViewHolder.userOfflineStatusImageView.setVisibility(View.VISIBLE);
            myViewHolder.userOnlineStatusImageView.setVisibility(View.GONE);
        } else {
            myViewHolder.userOfflineStatusImageView.setVisibility(View.GONE);
            myViewHolder.userOnlineStatusImageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView userEmailTextView;
        ImageView userOfflineStatusImageView, userOnlineStatusImageView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            userEmailTextView = itemView.findViewById(R.id.user_email_textView);
            userOfflineStatusImageView = itemView.findViewById(R.id.user_offline_status_imageView);
            userOnlineStatusImageView = itemView.findViewById(R.id.user_online_status_imageView);
        }
    }
}