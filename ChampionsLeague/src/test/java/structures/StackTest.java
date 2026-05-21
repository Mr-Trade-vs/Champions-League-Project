package structures;

import model.Match;
import model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StackTest {

    @Nested
    class EmptyStack {
        private Stack<Team> stackTeam;
        private Stack<Match> stackMatch;

        @BeforeEach
        void setUpEmptyStack() {
            stackTeam = new Stack<>();
            stackMatch = new Stack<>();
        }

        @Test
        void testEmptyStackProperties() {
            assertTrue(stackTeam.isEmpty());
            assertTrue(stackMatch.isEmpty());
            assertEquals(0, stackTeam.size());
            assertEquals(0, stackMatch.size());
            assertNull(stackTeam.peek());
            assertNull(stackMatch.peek());
        }

        @Test
        void testPush() {
            Team team = new Team("Real Madrid", "España", 14);
            Match match = new Match(team, new Team("Barcelona", "España", 5));

            stackTeam.push(team);
            stackMatch.push(match);

            assertFalse(stackTeam.isEmpty());
            assertFalse(stackMatch.isEmpty());
            assertEquals(1, stackTeam.size());
            assertEquals(1, stackMatch.size());
            assertEquals(team, stackTeam.peek());
            assertEquals(match, stackMatch.peek());
        }

        @Test
        void testPopEmpty() {
            assertNull(stackTeam.pop());
            assertNull(stackMatch.pop());
        }
    }

    @Nested
    class StackWithElements {
        private Stack<Team> stackTeam;
        private Stack<Match> stackMatch;
        private Team firstTeam;
        private Team secondTeam;
        private Match firstMatch;
        private LocalDateTime matchDate;

        @BeforeEach
        void setUpStackWithElements() {
            stackTeam = new Stack<>();
            stackMatch = new Stack<>();
            matchDate = LocalDateTime.now();

            firstTeam = new Team("Bayern Munich", "Alemania", 6);
            secondTeam = new Team("Inter", "Italia", 3);
            firstMatch = new Match(firstTeam, secondTeam);
            firstMatch.setDate(matchDate);

            stackTeam.push(firstTeam);
            stackTeam.push(secondTeam);
            stackMatch.push(firstMatch);
        }

        @Test
        void testPushWithElements() {
            Team newTeam = new Team("Liverpool", "Inglaterra", 6);
            Match newMatch = new Match(newTeam, secondTeam);

            stackTeam.push(newTeam);
            stackMatch.push(newMatch);

            assertEquals(3, stackTeam.size());
            assertEquals(2, stackMatch.size());
            assertEquals(newTeam, stackTeam.peek());
            assertEquals(newMatch, stackMatch.peek());
        }

        @Test
        void testPop() {
            Team poppedTeam = stackTeam.pop();
            Match poppedMatch = stackMatch.pop();

            assertEquals(secondTeam, poppedTeam);
            assertEquals(firstMatch, poppedMatch);
            assertEquals(1, stackTeam.size());
            assertEquals(0, stackMatch.size());
            assertEquals(firstTeam, stackTeam.peek());
            assertNull(stackMatch.peek());
        }

        @Test
        void testPeek() {
            assertEquals(secondTeam, stackTeam.peek());
            assertEquals(firstMatch, stackMatch.peek());
            assertEquals(2, stackTeam.size());
            assertEquals(1, stackMatch.size());
        }

        @Test
        void testMultipleOperations() {
            Team thirdTeam = new Team("PSG", "Francia", 0);
            Match secondMatch = new Match(thirdTeam, firstTeam);

            stackTeam.push(thirdTeam);
            assertEquals(3, stackTeam.size());
            assertEquals(thirdTeam, stackTeam.peek());

            Team poppedTeam = stackTeam.pop();
            assertEquals(thirdTeam, poppedTeam);
            assertEquals(secondTeam, stackTeam.peek());

            stackMatch.push(secondMatch);
            assertEquals(secondMatch, stackMatch.peek());

            Match poppedMatch = stackMatch.pop();
            assertEquals(secondMatch, poppedMatch);
            assertEquals(firstMatch, stackMatch.peek());
        }
    }

    @Nested
    class StackOrderVerification {
        private Stack<Team> stack;

        @BeforeEach
        void setUp() {
            stack = new Stack<>();
        }

        @Test
        void testLIFOOrder() {
            Team[] teams = {
                    new Team("Manchester City", "Inglaterra", 1),
                    new Team("Napoli", "Italia", 0),
                    new Team("Porto", "Portugal", 2),
                    new Team("Ajax", "Holanda", 4)
            };

            for (Team team : teams) {
                stack.push(team);
            }

            for (int i = teams.length - 1; i >= 0; i--) {
                assertEquals(teams[i], stack.pop());
            }
        }

        @Test
        void testPushPopAlternation() {
            Team team1 = new Team("Manchester City", "Inglaterra", 1);
            Team team2 = new Team("Napoli", "Italia", 0);

            stack.push(team1);
            assertEquals(team1, stack.pop());
            stack.push(team2);
            assertEquals(team2, stack.pop());
            assertTrue(stack.isEmpty());
        }
    }
}