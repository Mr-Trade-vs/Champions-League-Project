package structures;

import model.Match;
import model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HashTableTest {

    @Nested
    class EmptyHashTable {

        private HashTable<Team> hashTableTeam;
        private HashTable<Match> hashTableMatch;

        @BeforeEach
        void setUpEmptyHashTable() {
            hashTableTeam = new HashTable<>();
            hashTableMatch = new HashTable<>();
        }

        @Test
        void testPut() throws NullPointerException {
            Team team = new Team("Borussia Dortmund", "Alemania", 1);
            Team teamSecond = new Team("Liverpool", "Inglaterra", 6);
            Match match = new Match(team, teamSecond);
            hashTableTeam.put(team, team);
            hashTableTeam.put(teamSecond, teamSecond);
            hashTableMatch.put(match, match);

            assertEquals(team, hashTableTeam.search(team));
            assertEquals(teamSecond, hashTableTeam.search(teamSecond));
            assertEquals(match, hashTableMatch.search(match));
        }

        @Test
        void testSearch() throws NullPointerException {
            Team teamSearchOne = new Team("Benfica", "Portugal", 2);
            Team teamSearchSecond = new Team("AC Milan", "Italia", 7);
            Match matchSearch = new Match(teamSearchOne, teamSearchSecond);

            NullPointerException exception = assertThrows(NullPointerException.class, () -> {
                hashTableTeam.search(teamSearchOne);
                hashTableTeam.search(teamSearchSecond);
                hashTableMatch.search(matchSearch);
            });
        }

        @Test
        void testRemove() throws NullPointerException {
            Team teamDeleteOne = new Team("Benfica", "Portugal", 2);
            Team teamDeleteSecond = new Team("AC Milan", "Italia", 7);
            Match matchDelete = new Match(teamDeleteOne, teamDeleteSecond);

            NullPointerException exception = assertThrows(NullPointerException.class, () -> {
                hashTableTeam.remove(teamDeleteOne);
                hashTableTeam.remove(teamDeleteSecond);
                hashTableMatch.remove(matchDelete);
            });
        }
    }

    @Nested
    class HashTableWithElements {
        private HashTable<Team> hashTableTeam;
        private HashTable<Match> hashTableMatch;
        private LocalDateTime matchDate;

        @BeforeEach
        void setUpElementsTable() {
            hashTableTeam = new HashTable<>();
            hashTableMatch = new HashTable<>();

            Team team = new Team("Liverpool", "Inglaterra", 6);
            Team teamSecond = new Team("Benfica", "Portugal", 2);
            matchDate = LocalDateTime.now();
            Match match = new Match(team, teamSecond);
            match.setDate(matchDate);

            hashTableTeam.put(team, team);
            hashTableTeam.put(teamSecond, teamSecond);
            hashTableMatch.put(match, match);
        }

        @Test
        void testPut() throws NullPointerException {
            Team teamAddOne = new Team("Bayern Munich", "Alemania", 6);
            Team teamAddSecond = new Team("AC Milan", "Italia", 7);
            Match matchAdd = new Match(teamAddOne, teamAddSecond);

            hashTableTeam.put(teamAddOne, teamAddOne);
            hashTableTeam.put(teamAddSecond, teamAddSecond);
            hashTableMatch.put(matchAdd, matchAdd);

            assertEquals(teamAddOne, hashTableTeam.search(teamAddOne));
            assertEquals(teamAddSecond, hashTableTeam.search(teamAddSecond));
            assertEquals(matchAdd, hashTableMatch.search(matchAdd));
        }

        @Test
        void testSearch() throws NullPointerException {
            Team teamSearchOne = new Team("Liverpool", "Inglaterra", 6);
            Team teamSearchSecond = new Team("Benfica", "Portugal", 2);
            Match matchSearch = new Match(teamSearchOne, teamSearchSecond);
            matchSearch.setDate(matchDate);

            Team foundOne = hashTableTeam.search(teamSearchOne);
            Team foundSecond = hashTableTeam.search(teamSearchSecond);
            Match foundMatch = hashTableMatch.search(matchSearch);

            assertEquals(teamSearchOne, foundOne);
            assertEquals(teamSearchSecond, foundSecond);
            assertEquals(matchSearch, foundMatch);

        }

        @Test
        void testRemove() throws NullPointerException {
            Team teamRemove = new Team("Liverpool", "Inglaterra", 6);
            Team teamRemoveTwo = new Team("Benfica", "Portugal", 2);
            Match matchRemove = new Match(teamRemove, teamRemoveTwo);
            matchRemove.setDate(matchDate);

            Team teamDelete = hashTableTeam.remove(teamRemove);
            Match matchDelete = hashTableMatch.remove(matchRemove);

            assertEquals(teamRemove, teamDelete);
            assertEquals(matchRemove, matchDelete);
        }
    }

    @Nested
    class HashTableCollisionHandling {
        private HashTable<Team> hashTable;

        @BeforeEach
        void setUp() {
            hashTable = new HashTable<>();
        }

        @Test
        void testMultipleTeamsWithSameHash() {
            Team team1 = new Team("Manchester City", "Inglaterra", 1);
            Team team2 = new Team("Napoli", "Italia", 0);
            Team team3 = new Team("Porto", "Portugal", 2);

            hashTable.put(team1, team1);
            hashTable.put(team2, team2);
            hashTable.put(team3, team3);

            assertEquals(team1, hashTable.search(team1));
            assertEquals(team2, hashTable.search(team2));
            assertEquals(team3, hashTable.search(team3));
        }

        @Test
        void testRemoveWithCollisions() {
            Team team1 = new Team("Ajax", "Holanda", 4);
            Team team2 = new Team("Celtic", "Escocia", 1);

            hashTable.put(team1, team1);
            hashTable.put(team2, team2);

            Team removed = hashTable.remove(team1);
            assertEquals(team1, removed);
            assertEquals(team2, hashTable.search(team2));
        }
    }
}
