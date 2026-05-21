package model;

import java.time.LocalDateTime;

public class Match {
    private Team localTeam;
    private Team visitingTeam;
    private int localScore;
    private int visitingScore;
    private LocalDateTime date;

    public Match(Team localTeam, Team visitingTeam) {
        this.localTeam = localTeam;
        this.visitingTeam = visitingTeam;
        this.localScore = localScore;
        this.visitingScore = visitingScore;
        this.date = date;
    }

    public Team getLocalTeam() {
        return localTeam;
    }

    public void setLocalTeam(Team localTeam) {
        this.localTeam = localTeam;
    }

    public Team getVisitingTeam() {
        return visitingTeam;
    }

    public void setVisitingTeam(Team visitingTeam) {
        this.visitingTeam = visitingTeam;
    }

    public int getLocalScore() {
        return localScore;
    }

    public void setLocalScore(int localScore) {
        this.localScore = localScore;
    }

    public int getVisitingScore() {
        return visitingScore;
    }

    public void setVisitingScore(int visitingScore) {
        this.visitingScore = visitingScore;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "\nMatch: " + "\n" +
                "----------------------------------------\n" +
                "Date: " + date.toString() + "\n" +
                "Local Team: " + localTeam + "\n" +
                "Visitor Team: " + visitingTeam + "\n" +
                "Score: " + localScore + " - " + visitingScore + "\n" +
                "----------------------------------------\n";
    }
}
