package ui;

import model.ControllerChampions;
import model.Match;
import model.Team;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ControllerChampions controller = new ControllerChampions();
        Scanner sc = new Scanner(System.in);
        int choice;

        System.out.println("Welcome to the CHAMPIONS LEAGUE\n" + "Marlon/Daniela Castaño\n" +
                "What do you want to do today?");

        do {
            System.out.println("[1] Register Team\n" +
                    "[2] Register Match\n" +
                    "[3] Cancel Last Action\n" +
                    "[4] Preload data for the tournament (Only if all the data structures are empty)\n" +
                    "[5] Add Score To Match\n" +
                    "[6] Show Ranking (Only when the UEFA Champions League finishes)" +
                    "[7] Exit");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:

                    System.out.println("What's the name of the team?" +
                            "You can see all participating teams here:" +
                            "https://www.transfermarkt.co/uefa-champions-league/teilnehmer/pokalwettbewerb/CL");
                    String teamName = sc.nextLine();

                    System.out.println("Where is the team from?");
                    String country = sc.nextLine();

                    //In this case, we only consider the number of tittles in UEFA Champions League
                    System.out.println("How many tittles have this team?\n" +
                            "If you don't know its number of tittles, check this link:\n" +
                            "https://es.uefa.com/uefachampionsleague/news/0275-15416e03729a-924781310e2d-1000--palmares-historico-de-la-copa-de-europa-los-equipos-con-m/");
                    int numberTittles = sc.nextInt();
                    sc.nextLine();

                    registerTeam(teamName, country, numberTittles, controller);

                    break;

                case 2:
                    System.out.println("What's the name of local team?");
                    String  localTeamName = sc.nextLine();

                    System.out.println("What's the name of the visiting team?");
                    String  visitingTeamName = sc.nextLine();

                    registerMatch(localTeamName, visitingTeamName, controller);

                    break;

                case 3:
                    cancelAction(controller);
                    break;

                case 4:
                    preloadTournament(controller);
                    break;

                case 5:
                    System.out.println("Actual Match:" +
                            controller.actualMatch());

                    System.out.println("Enter the local team's score: ");
                    int localScore = sc.nextInt();

                    System.out.println("Enter the visiting team's score: ");
                    int visitingScore = sc.nextInt();
                    sc.nextLine();

                    score(localScore, visitingScore, controller);
                    break;

                case 6:
                    ranking(controller);
                    break;
            }
        } while (choice != 7);
        sc.close();
    }

    public static void registerTeam(String teamName, String country, int tittles, ControllerChampions controller) {
        Team team = new Team(teamName, country, tittles);
        controller.addTeam(team);
    }

    public static void registerMatch(String localTeamName, String visitingTeamName, ControllerChampions controller) {
        Match match = controller.addMatch(localTeamName, visitingTeamName);
        System.out.println("Match programming for the Champions League: \n" + match);
    }

    public static void cancelAction(ControllerChampions controller) {
        String message = controller.cancelAction();
        System.out.println("System Cancel: \n" + message);
    }

    public static void preloadTournament(ControllerChampions controller) {
        String message = controller.preloadChampions();
        System.out.println("System Pre-loaded: \n" + message);
    }

    public static void score(int localScore, int visitingScore, ControllerChampions controller) {
        String message = controller.setMatchScore(localScore, visitingScore);
        System.out.println(message);
    }

    public static void ranking(ControllerChampions controller) {
        String message = controller.printRanking();
        System.out.println(message);
    }
}
