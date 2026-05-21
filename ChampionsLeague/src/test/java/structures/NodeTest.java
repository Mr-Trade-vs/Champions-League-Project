package structures;

import model.Match;
import model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NodeTest {

    @Nested
    class NodeNull {
        private Node<Team> node;
        private Node<Match> match;

        @BeforeEach
        void setUpNull() {
            node = new Node<>(null);
            match = new Node<>(null);
        }

        @Test
        void testGetters() {
            assertEquals(null, node.getData());
            assertEquals(null, node.getNext());
            assertEquals(null, match.getData());
            assertEquals(null, match.getNext());
        }

        @Test
        void testSetters() {
            Team team = new Team("Real Madrid", "España", 15);
            Team teamTwo = new Team("Barcelona", "España", 5);
            Match matchTeams = new Match(team, teamTwo);
            node.setData(team);
            node.setNext(new Node<>(teamTwo));
            match.setData(matchTeams);

            assertEquals(team, node.getData());
            assertEquals(teamTwo, node.getNext().getData());
            assertEquals(matchTeams, match.getData());
        }
    }

    @Nested
    class NodeElement {

        private Node<Team> node;
        private Node<Match> match;

        @BeforeEach
        void setUpElement() {
            Team team = new Team("Real Madrid", "España", 15);
            Team secondTeam = new Team("Barcelona", "España", 5);
            Match matchTeams = new Match(team, secondTeam);
            node = new Node<>(team);
            node.setNext(new Node<>(secondTeam));
            match = new Node<>(matchTeams);
        }

        @Test
        void testGetters() {
            assertEquals("Real Madrid", node.getData().getName());
            assertEquals("España", node.getData().getCountry());
            assertEquals(15, node.getData().getNumberTitles());
            assertEquals("Barcelona", node.getNext().getData().getName());
            assertEquals("España", node.getNext().getData().getCountry());
            assertEquals(5, node.getNext().getData().getNumberTitles());
            assertEquals("Real Madrid", match.getData().getLocalTeam().getName());
            assertEquals("España", match.getData().getLocalTeam().getCountry());
            assertEquals(15, match.getData().getLocalTeam().getNumberTitles());
            assertEquals("Barcelona", match.getData().getVisitingTeam().getName());
            assertEquals("España", match.getData().getVisitingTeam().getCountry());
            assertEquals(5, match.getData().getVisitingTeam().getNumberTitles());
        }

        @Test
        void testSetters() {
            Team team = new Team("PSG", "Francia", 0);
            Team secondTeam = new Team("Arsenal", "Inglaterra", 0);
            Match matchOne = new Match(team, secondTeam);
            node.setData(team);
            node.setNext(new Node<>(secondTeam));
            match.setData(matchOne);

            assertEquals(team, node.getData());
            assertEquals(secondTeam, node.getNext().getData());
            assertEquals(matchOne, match.getData());
        }
    }

    @Nested
    class NodeChain {
        private Node<Team> firstNode;
        private Node<Team> secondNode;
        private Node<Team> thirdNode;

        @BeforeEach
        void setUpChain() {
            Team team1 = new Team("Manchester City", "Inglaterra", 1);
            Team team2 = new Team("Napoli", "Italia", 0);
            Team team3 = new Team("Porto", "Portugal", 2);

            firstNode = new Node<>(team1);
            secondNode = new Node<>(team2);
            thirdNode = new Node<>(team3);

            firstNode.setNext(secondNode);
            secondNode.setNext(thirdNode);
        }

        @Test
        void testChainLinks() {
            assertEquals("Manchester City", firstNode.getData().getName());
            assertEquals("Napoli", firstNode.getNext().getData().getName());
            assertEquals("Porto", firstNode.getNext().getNext().getData().getName());
        }

        @Test
        void testModifyChain() {
            Team newTeam = new Team("Ajax", "Holanda", 4);
            secondNode.setData(newTeam);

            assertEquals("Ajax", firstNode.getNext().getData().getName());
            assertEquals("Porto", secondNode.getNext().getData().getName());
        }
    }
}
