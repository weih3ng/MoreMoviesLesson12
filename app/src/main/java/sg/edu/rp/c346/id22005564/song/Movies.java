package sg.edu.rp.c346.id22005564.song;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Movies implements Serializable {
    private int id;
    private String title;
    private String genre;
    private int year;
    private int rating;

    public Movies(int id, String title, String genre, int year, int rating) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.rating = rating;
    }

    @NonNull
    @Override
    public String toString() {
        return id + ". \n" + "Movie Title: " + title + "\n" + "Genre: " + genre + "\n" + "Year of Release: " + year + "\n" + "Rating: " + rating + " Star/s";
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    public int getRating() {
        return rating;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
