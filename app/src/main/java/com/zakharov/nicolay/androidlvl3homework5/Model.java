package com.zakharov.nicolay.androidlvl3homework5;


import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;


public class Model {

    private String login;

    private String id;

    @SerializedName("avatar_url")
    private String avatar;

    @Nullable
    public String getAvatar() {
        return avatar;
    }

    @Nullable
    public String getLogin() {
        return login;
    }

    public String getUserId() {
        return id;
    }

    public void setLogin(String login){
        this.login = login;
    }
    public void setUserID(String userId){this.id = userId;}
    public void setAvatarUrl(String avatarUrl){
        this.avatar = avatarUrl;
    }
}
