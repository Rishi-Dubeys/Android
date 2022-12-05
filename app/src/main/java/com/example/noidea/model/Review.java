package com.example.noidea.model;

public class Review {


    String title;
    String review;
    String rating;
    String id;
    String date;
    String gameid;


    public String getGameid() {
        return gameid;
    }

    public void setGameid(String gameid) {
        this.gameid = gameid;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    String userid;

    public Review() {

    }

    public Review(String title , String review , String rating , String date , String id, String userid , String name, String gameid) {

        this.title = title;
        this.review = review;
        this.rating = rating;
        this.date = date;
        this.id = id;
        this.userid = userid;
        this.name = name;
        this.gameid = gameid;

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
