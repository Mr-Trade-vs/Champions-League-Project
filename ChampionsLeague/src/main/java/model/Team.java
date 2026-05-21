package model;

public class Team {
    private String name;
    private String country;
    private int numberTitles;
    private double ratingUEFA;
    private int points;
    private int score;

    public Team(String name, String country, int numberTitles) {
        this.name = name;
        this.country = country;
        this.numberTitles = numberTitles;
        this.ratingUEFA = 0;
        this.points = 0;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getNumberTitles() {
        return numberTitles;
    }

    public void setNumberTitles(int numberTitles) {
        this.numberTitles = numberTitles;
    }

    public double getRatingUEFA() {
        return ratingUEFA;
    }

    public void setRatingUEFA(double ratingUEFA) {
        this.ratingUEFA = ratingUEFA;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return (" "+name+" ");
    }
}
