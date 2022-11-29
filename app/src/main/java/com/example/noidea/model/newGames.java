package com.example.noidea.model;
public class newGames {

    String name;
    String platform;
    String releaseDate;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNewsID() {
        return newsID;
    }

    String url;
    String newsID ;

    public void setNewsID(String newsID) {
        this.newsID = newsID;
    }

    public newGames(String name, String platform, String releaseDate, String newsID , String url) {
        this.name = name;
        this.platform = platform;
        this.releaseDate = releaseDate;
        this.newsID = newsID;
        this.url = url;
    }




    public newGames() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }



}
