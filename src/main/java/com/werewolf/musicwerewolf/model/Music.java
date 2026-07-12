package com.werewolf.musicwerewolf.model;

public class Music {
    private String id;
    private String title;
    private String artist;
    private String category;
    private String bilibiliUrl;  // 新增B站链接

    public Music(String id, String title, String artist, String category, String bilibiliUrl) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.category = category;
        this.bilibiliUrl = bilibiliUrl;
    }

    // 所有getter和setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getBilibiliUrl() { return bilibiliUrl; }
    public void setBilibiliUrl(String bilibiliUrl) { this.bilibiliUrl = bilibiliUrl; }
}