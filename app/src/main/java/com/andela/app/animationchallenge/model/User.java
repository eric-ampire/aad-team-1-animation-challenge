package com.andela.app.animationchallenge.model;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String displayName;
    private String userName;
    private String profileUrl;

    public User() {
    }

    public User(String id, String displayName, String userName, String profileUrl) {
        this.id = id;
        this.displayName = displayName;
        this.userName = userName;
        this.profileUrl = profileUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
