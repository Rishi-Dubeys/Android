package com.example.noidea.model;
public class newGames {

    String name;
    String platform;
    String releaseDate;
    String url;
    String newsID ;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public newGames() {
    }

    public String getNewsID() {
        return newsID;
    }

    public void setNewsID(String newsID) {
        this.newsID = newsID;
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

    public newGames(String name , String platform , String releaseDate , String url , String newsID) {
        this.name = name;
        this.platform= platform;
        this.releaseDate = releaseDate;
        this.url = url;
        this.newsID = newsID;
    }

}
