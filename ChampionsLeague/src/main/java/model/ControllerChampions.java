package model;

import structures.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class ControllerChampions {
    private HashTable<Team>  hashTableTeams = new HashTable<>();
    private HashTable<Match> hashTableMatches = new HashTable<>();
    private LinkedList<Team> keysTeam = new LinkedList();
    private boolean teamAction = false;
    private boolean matchAction = false;
    private Stack<Match> cancelMatch = new Stack<>();
    private Stack<Team> cancelTeam = new Stack<>();
    private Queue<Match> matchQueue = new Queue<>();
    private final ComparatorCoefficientAndPoints comparator = new ComparatorCoefficientAndPoints();
    private PriorityQueue<Team> rankingTeams = new PriorityQueue<>(comparator);
    private boolean scoreAction = false;
    private LinkedList<Match> organizeQueue = new LinkedList<>();
    private LinkedList<Team> nextMatch = new LinkedList<>();

    public void addTeam(Team team) {
        teamAction = true;
        matchAction = false;
        scoreAction = false;
        cancelTeam.push(team);
        hashTableTeams.put(team, team);
        keysTeam.add(team);
    }

    public Match addMatch(String localTeamName, String visitingTeamName) {
        teamAction = false;
        matchAction = true;
        scoreAction = false;
        Team localTeam = selectTeam(localTeamName);
        Team visitingTeam = selectTeam(visitingTeamName);
        Match match = new Match(localTeam, visitingTeam);
        match.setDate(LocalDateTime.now().plusWeeks(2).truncatedTo(ChronoUnit.MINUTES));
        cancelMatch.push(match);
        hashTableMatches.put(match, match);
        matchQueue.enqueue(match);
        return match;
    }

    public String cancelAction() {
        String message = "";
        if (matchAction) {
            Match deleteMatch = cancelMatch.pop();
            hashTableMatches.remove(deleteMatch);
            message += deleteMatch;

            while (!matchQueue.isEmpty() && !deleteMatch.equals(matchQueue.peek())) {
                    organizeQueue.add(matchQueue.dequeue());
            }

            if (!matchQueue.isEmpty()) {
                matchQueue.dequeue();
            }

            Node<Match> currrent = organizeQueue.getFirst();
            while (currrent != null) {
                matchQueue.enqueue(currrent.getData());
                currrent = currrent.getNext();
            }
        } else if (teamAction) {
            Team deleteTeam = cancelTeam.pop();
            hashTableTeams.remove(deleteTeam);
            message += deleteTeam;
        } else if (scoreAction) {
            Match restoreMatch = cancelMatch.pop();
            message += restoreMatch;

            hashTableMatches.remove(restoreMatch);
            restoreMatch.setLocalScore(0);
            restoreMatch.setVisitingScore(0);
            hashTableMatches.put(restoreMatch, restoreMatch);

            while (!matchQueue.isEmpty()) {
                organizeQueue.add(matchQueue.dequeue());
            }

            matchQueue.enqueue(restoreMatch);

            Node<Match> currrent = organizeQueue.getFirst();
            while (currrent != null) {
                matchQueue.enqueue(currrent.getData());
                currrent = currrent.getNext();
            }
        }
        return message;
    }

    private Team selectTeam(String selectTeam) {
        if (keysTeam.isEmpty()) {
            return null;
        } else {
            Node<Team> current = keysTeam.getFirst();
            while (keysTeam != null && !current.getData().getName().equals(selectTeam)) {
                current = current.getNext();
            }
            return current.getData();
        }
    }

    public String actualMatch() {
        if (matchQueue.isEmpty()) {
            return "Queue is empty";
        } else  {
            Match match = matchQueue.peek();
            return match.toString();
        }
    }

    public String setMatchScore(int localScore, int visitingScore) {
        teamAction = false;
        matchAction = false;
        scoreAction = true;
        Team winnerTeam = null;
        Team lostTeam = null;

        if (!matchQueue.isEmpty()) {
            Match match = matchQueue.dequeue();
            hashTableMatches.remove(match);
            match.setLocalScore(localScore);
            match.setVisitingScore(visitingScore);
            hashTableMatches.put(match, match);
            cancelMatch.push(match);

            if (localScore > visitingScore) {
                winnerTeam = hashTableTeams.remove(match.getLocalTeam());
                winnerTeam.setRatingUEFA(+1);
                winnerTeam.setPoints(+3);
                winnerTeam.setScore(localScore);
                hashTableTeams.put(winnerTeam, winnerTeam);
                lostTeam = hashTableTeams.remove(match.getVisitingTeam());
                lostTeam.setScore(visitingScore);
                hashTableTeams.put(lostTeam, lostTeam);
                rankingTeams.add(lostTeam);



                return ("Match score update: " + match +
                        "\n" + advanceNextStage(winnerTeam));

            } else if (visitingScore > localScore) {
                winnerTeam = hashTableTeams.remove(match.getVisitingTeam());
                winnerTeam.setRatingUEFA(+1);
                winnerTeam.setPoints(+3);
                hashTableTeams.put(winnerTeam, winnerTeam);
                lostTeam = hashTableTeams.remove(match.getLocalTeam());
                rankingTeams.add(lostTeam);

                return ("Match score update: " + match +
                        "\n" + advanceNextStage(winnerTeam));
            } else {
                Random randomGoals = new Random();
                int localPenalty = randomGoals.nextInt(6);
                int visitingPenalty = randomGoals.nextInt(6);

                while(localPenalty == visitingPenalty) {
                    visitingPenalty = randomGoals.nextInt(6);
                }

                if (localPenalty > visitingPenalty) {
                    winnerTeam = hashTableTeams.remove(match.getLocalTeam());
                    winnerTeam.setRatingUEFA(+1);
                    winnerTeam.setPoints(+3);
                    hashTableTeams.put(winnerTeam, winnerTeam);
                    lostTeam = hashTableTeams.remove(match.getVisitingTeam());
                    rankingTeams.add(lostTeam);

                    return ("The match has gone to penalties, with the score being: " + match +
                            "\n" + advanceNextStage(winnerTeam));
                } else {
                    winnerTeam = hashTableTeams.remove(match.getVisitingTeam());
                    winnerTeam.setRatingUEFA(+1);
                    winnerTeam.setPoints(+3);
                    hashTableTeams.put(winnerTeam, winnerTeam);
                    lostTeam = hashTableTeams.remove(match.getLocalTeam());
                    rankingTeams.add(lostTeam);

                    return ("The match has gone to penalties, with the score being: " + match +
                            "\n" + advanceNextStage(winnerTeam));
                }
            }
        } else {
            return "Queue is empty";
        }
    }

    public String advanceNextStage(Team winnerTeam) {
        String message = "";
        nextMatch.add(winnerTeam);
        if (nextMatch.size() == 2) {
            String localTeamName = nextMatch.getFirst().getData().getName();
            String visitingTeamName = nextMatch.getFirst().getNext().getData().getName();

            nextMatch.clear();
            Match nextStage = addMatch(localTeamName, visitingTeamName);

            message += "New Match created: " + nextStage;
        } else {
            message += "The winner is: " + nextMatch.getFirst().getData();
        }
        return message;
    }


    public String preloadChampions() {
        String tournament = "";
        Team team1 = new Team("PSV", "Netherlands", 1);
        team1.setRatingUEFA(69.250);
        addTeam(team1);
        Team team2 = new Team("Arsenal", "England", 0);
        team2.setRatingUEFA(98);
        addTeam(team2);
        addMatch(team1.getName(), team2.getName());

        Team team3 = new Team("Real Madrid", "Spain", 15);
        team3.setRatingUEFA(143.500);
        addTeam(team3);
        Team team4 = new Team("Atletico de Madrid", "Spain", 0);
        team4.setRatingUEFA(93.500);
        addTeam(team4);
        addMatch(team3.getName(), team4.getName());

        Team team5 = new Team("PSG", "France", 0);
        team5.setRatingUEFA(111);
        addTeam(team5);
        Team team6 = new Team("Liverpool", "England", 6);
        team6.setRatingUEFA(125.500);
        addTeam(team6);
        addMatch(team5.getName(), team6.getName());

        Team team7 = new Team("Club Brujas", "Belgium", 0);
        team7.setRatingUEFA(71.750);
        addTeam(team7);
        Team team8 = new Team("Aston Villa", "England", 1);
        team8.setRatingUEFA(47.250);
        addTeam(team8);
        addMatch(team7.getName(), team8.getName());

        Team team9 = new Team("Benfica", "Portugal", 2);
        team9.setRatingUEFA(87.750);
        addTeam(team9);
        Team team10 = new Team("Barcelona", "Spain", 5);
        team10.setRatingUEFA(102.250);
        addTeam(team10);
        addMatch(team9.getName(), team10.getName());

        Team team11 = new Team("Borussia Dortmund", "Germany", 2);
        team11.setRatingUEFA(106.750);
        addTeam(team11);
        Team team12 = new Team("Lille", "France", 0);
        team12.setRatingUEFA(66);
        addTeam(team12);
        addMatch(team11.getName(), team12.getName());

        Team team13 = new Team("Bayern de Múnich", "Germany", 6);
        team13.setRatingUEFA(135.250);
        addTeam(team13);
        Team team14 = new Team("Leverkusen", "Germany", 0);
        team14.setRatingUEFA(95.250);
        addTeam(team14);
        addMatch(team13.getName(), team14.getName());

        Team team15 = new Team("Feyenoord", "Netherlands", 1);
        team15.setRatingUEFA(71);
        addTeam(team15);
        Team team16 = new Team("Inter", "Italy", 0);
        team16.setRatingUEFA(111.750);
        addTeam(team16);
        addMatch(team15.getName(), team16.getName());

        return tournament += "Designed Matches:\n" +
                        team1 + "v.s" + team2 + "\n" +
                        team3 + "v.s" + team4 + "\n" +
                        team5 + "v.s" + team6 + "\n" +
                        team7 + "v.s" + team8 + "\n" +
                        team9 + "v.s" + team10 + "\n" +
                        team11 + "v.s" + team12 + "\n" +
                        team13 + "v.s" + team14 + "\n" +
                        team15 + "v.s" + team16 + "\n";
    }

    public String printRanking() {
        String message = "Ranking:\n";

        while (!rankingTeams.isEmpty()) {
            Team teamPrint = rankingTeams.remove();
            message += teamPrint + "" + teamPrint.getRatingUEFA() + "\n";
        }

        return message;
    }
}
