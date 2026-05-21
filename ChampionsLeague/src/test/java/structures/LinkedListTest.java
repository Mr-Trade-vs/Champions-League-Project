package structures;

import model.Match;
import model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedListTest {

    @Nested
    class EmptyLinkedList {

        private LinkedList<Team> teams;
        private LinkedList<Match> matches;

        @BeforeEach
        void setUpEmpty() {
            teams = new LinkedList<>();
            matches = new LinkedList<>();
        }

        @Test
        void testAdd() {
            Team team = new Team("PSG", "Francia", 0);
            Team secondTeam = new Team("Arsenal", "Inglaterra", 0);
            Match matchOne = new Match(team, secondTeam);

            teams.add(team);
            teams.add(secondTeam);
            matches.add(matchOne);

            assertEquals(team, teams.getFirst().getData());
            assertEquals(secondTeam, teams.getFirst().getNext().getData());
            assertEquals(matchOne, matches.getFirst().getData());
        }

        @Test
        void testSearch() throws NullPointerException {
            Team team = new Team("PSG", "Francia", 0);
            Team secondTeam = new Team("Arsenal", "Inglaterra", 0);
            Match matchOne = new Match(team, secondTeam);

            NullPointerException exception = assertThrows(NullPointerException.class, () -> {
                teams.search(team);
                matches.search(matchOne);
            });
        }

        @Test
        void testRemove() throws NullPointerException {
            Team team = new Team("PSG", "Francia", 0);
            Team secondTeam = new Team("Arsenal", "Inglaterra", 0);
            Match matchOne = new Match(team, secondTeam);

            NullPointerException exception = assertThrows(NullPointerException.class, () -> {
                teams.remove(team);
                matches.remove(matchOne);
            });
        }
    }

    @Nested
    class setUpFirstElement {

        private LinkedList<Team> teams;
        private LinkedList<Match> matches;
        private LocalDateTime matchDate;

        @BeforeEach
        void setUpWithElements() {
            teams = new LinkedList<>();
            matches = new LinkedList<>();

            Team team = new Team("Bayern Munich", "Alemania", 6);
            Team secondTeam = new Team("Inter", "Italia", 3);
            matchDate = LocalDateTime.now();
            Match matchOne = new Match(team, secondTeam);
            matchOne.setDate(matchDate);

            teams.add(team);
            teams.add(secondTeam);
            matches.add(matchOne);
        }

        @Test
        void testAdd() {
            Team teamNew = new Team("PSG", "Francia", 0);
            Team secondTeamNew = new Team("Arsenal", "Inglaterra", 0);
            Match matchSecond = new Match(teamNew, secondTeamNew);

            teams.add(teamNew);
            teams.add(secondTeamNew);
            matches.add(matchSecond);

            assertEquals(teamNew, teams.getFirst().getNext().getNext().getData());
            assertEquals(secondTeamNew, teams.getFirst().getNext().getNext().getNext().getData());
            assertEquals(matchSecond, matches.getFirst().getNext().getData());
        }


        @Test
        void testSearch() throws NullPointerException {
            Team teamSearchOne = new Team("Bayern Munich", "Alemania", 6);
            Team teamSearchTwo = new Team("Inter", "Italia", 3);
            Match matchSearch = new Match(teamSearchOne, teamSearchTwo);
            matchSearch.setDate(matchDate);

            Team foundTeam = teams.search(teamSearchOne);
            Team foundTeam2 = teams.search(teamSearchTwo);
            Match foundMatch = matches.search(matchSearch);

            assertEquals(teamSearchOne, foundTeam);
            assertEquals(teamSearchTwo, foundTeam2);
            assertEquals(matchSearch, foundMatch);
        }

        @Test
        void testRemove() throws NullPointerException {
            Team teamRemoveOne = new Team("Bayern Munich", "Alemania", 6);
            Team teamTwo = new Team("Inter", "Italia", 3);
            Match matchRemove = new Match(teamRemoveOne, teamTwo);
            matchRemove.setDate(matchDate);

            Team teamDelete = teams.remove(teamRemoveOne);
            Match matchDelete = matches.remove(matchRemove);

            assertEquals(teamRemoveOne, teamDelete);
            assertEquals(matchRemove, matchDelete);
        }
    }

    @Nested
    class LinkedListOperationsOrder {
        private LinkedList<Team> list;
        private LocalDateTime matchDate;

        @BeforeEach
        void setUpList() {
            list = new LinkedList<>();
            matchDate = LocalDateTime.now();
        }

        @Test
        void testAddAndRemoveSequence() {
            Team[] teams = {
                    new Team("Manchester City", "Inglaterra", 1),
                    new Team("Napoli", "Italia", 0),
                    new Team("Porto", "Portugal", 2),
                    new Team("Ajax", "Holanda", 4)
            };

            for (Team team : teams) {
                list.add(team);
            }
            assertEquals(teams[0], list.getFirst().getData());

            Team removedTeam = list.remove(teams[1]);
            assertEquals(teams[1], removedTeam);

            Team removedLast = list.remove(teams[3]);
            assertEquals(teams[3], removedLast);

            assertEquals(teams[0], list.getFirst().getData());
            assertEquals(teams[2], list.getFirst().getNext().getData());
        }

        @Test
        void testSearchAfterModifications() {
            Team team1 = new Team("Manchester City", "Inglaterra", 1);
            Team team2 = new Team("Napoli", "Italia", 0);
            Team team3 = new Team("Porto", "Portugal", 2);

            list.add(team1);
            list.add(team2);
            list.add(team3);

            list.remove(team2);

            assertEquals(team1, list.search(team1));
            assertEquals(team3, list.search(team3));

            NullPointerException exception = assertThrows(NullPointerException.class, () -> {
                list.search(team2);
            });

            Team team4 = new Team("Ajax", "Holanda", 4);
            list.add(team4);
            assertEquals(team4, list.search(team4));
        }

        @Test
        void testBoundaryOperations() {
            Team firstTeam = new Team("Manchester City", "Inglaterra", 1);
            Team lastTeam = new Team("Ajax", "Holanda", 4);

            list.add(firstTeam);
            assertEquals(firstTeam, list.getFirst().getData());
            assertEquals(firstTeam, list.getLast().getData());

            list.add(lastTeam);
            assertEquals(firstTeam, list.getFirst().getData());
            assertEquals(lastTeam, list.getLast().getData());

            Team removedFirst = list.remove(firstTeam);
            assertEquals(firstTeam, removedFirst);
            assertEquals(lastTeam, list.getFirst().getData());
            assertEquals(lastTeam, list.getLast().getData());

            Team removedLast = list.remove(lastTeam);
            assertEquals(lastTeam, removedLast);
            assertTrue(list.isEmpty());
        }
    }
}
