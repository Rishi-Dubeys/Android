package com.example.noidea.model;

public class Games {


    String name;
    String platform;
    String releaseDate;
    String publisher;
    String Description;
    String url;



    String id;

    public Games() {

    };

    public Games(String str_name, String str_platform, String str_date, String key, String url, String str_publisher, String str_description) {
        this.name = str_name;
        this.platform = str_platform;
        this.releaseDate = str_date;
        this.id = key;
        this.url = url;
        this.publisher = str_publisher;
        this.Description = str_description;
    }


    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
