package com.example.quickBloxApplication.helper;

import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;
import java.util.List;

public class DataHolder {
    private static DataHolder instance;
    private List<QBUser> qbUsers;
    private QBUser signInQbUser;

    public DataHolder() {
        qbUsers = new ArrayList<>();
    }

    public static synchronized DataHolder getInstance() {
        if (instance == null) {
            instance = new DataHolder();
        }
        return instance;
    }

    private void addQbUsers(List<QBUser> qbUsers) {
        for (QBUser qbUser : qbUsers) {
            addQbUser(qbUser);
        }
    }

    private void addQbUser(QBUser qbUser) {
        if (!qbUsers.contains(qbUser)) {
            qbUsers.add(qbUser);
        }
    }

    public void updateQbUserList(int location, QBUser qbUser) {
        if (location != -1) {
            qbUsers.set(location, qbUser);
        }
    }

    public List<QBUser> getQbUsers() {
        return qbUsers;
    }

    public void clear() {
        qbUsers.clear();
    }

    public QBUser getSignInQbUser() {
        return signInQbUser;
    }

    public void setSignInQbUser(QBUser signInQbUser) {
        this.signInQbUser = signInQbUser;
    }

    public boolean isSignedIn() {
        return signInQbUser != null;
    }

}