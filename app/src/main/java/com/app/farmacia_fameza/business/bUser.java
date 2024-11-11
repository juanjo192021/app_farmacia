package com.app.farmacia_fameza.business;
import android.content.Context;

import com.app.farmacia_fameza.controller.cUser;
import com.app.farmacia_fameza.models.User;


public class bUser {
    private cUser CUser;

    public bUser(Context context) {
        CUser=new cUser(context);
    }

    public boolean login(User user) {
        return CUser.login(user);
    }

    public boolean register(User user) {
        return CUser.registerUser(user);
    }
}
