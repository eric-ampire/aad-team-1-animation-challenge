package com.andela.app.animationchallenge.model;

import java.io.Serializable;
import java.util.Date;

public class Photo implements Serializable {
    private String id;
    private String url;
    private String title;
    private String description;
    private String idOwner;
    private Date date;
    private boolean isDownloadable;

    public Photo(String id, String url, String title, String description, String idOwner, Date date, boolean isDownloadable) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.description = description;
        this.idOwner = idOwner;
        this.date = date;
        this.isDownloadable = isDownloadable;
    }

    public Photo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(String idOwner) {
        this.idOwner = idOwner;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isDownloadable() {
        return isDownloadable;
    }

    public void setDownloadable(boolean downloadable) {
        isDownloadable = downloadable;
    }
}
