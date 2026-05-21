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

public class QueueTest {

    @Nested
    class EmptyQueue {
        private Queue<Team> queueTeam;
        private Queue<Match> queueMatch;

        @BeforeEach
        void setUpEmptyQueue() {
            queueTeam = new Queue<>();
            queueMatch = new Queue<>();
        }

        @Test
        void testEmptyQueueProperties() {
            assertTrue(queueTeam.isEmpty());
            assertTrue(queueMatch.isEmpty());
            assertEquals(0, queueTeam.size());
            assertEquals(0, queueMatch.size());
            assertNull(queueTeam.peek());
            assertNull(queueMatch.peek());
        }

        @Test
        void testEnqueue() {
            Team team = new Team("Real Madrid", "España", 14);
            Match match = new Match(team, new Team("Barcelona", "España", 5));

            queueTeam.enqueue(team);
            queueMatch.enqueue(match);

            assertFalse(queueTeam.isEmpty());
            assertFalse(queueMatch.isEmpty());
            assertEquals(1, queueTeam.size());
            assertEquals(1, queueMatch.size());
            assertEquals(team, queueTeam.peek());
            assertEquals(match, queueMatch.peek());
        }

        @Test
        void testDequeueEmpty() {
            assertNull(queueTeam.dequeue());
            assertNull(queueMatch.dequeue());
        }
    }

    @Nested
    class QueueWithElements {
        private Queue<Team> queueTeam;
        private Queue<Match> queueMatch;
        private Team firstTeam;
        private Team secondTeam;
        private Match firstMatch;
        private LocalDateTime matchDate;

        @BeforeEach
        void setUpQueueWithElements() {
            queueTeam = new Queue<>();
            queueMatch = new Queue<>();
            matchDate = LocalDateTime.now();

            firstTeam = new Team("Bayern Munich", "Alemania", 6);
            secondTeam = new Team("Inter", "Italia", 3);
            firstMatch = new Match(firstTeam, secondTeam);
            firstMatch.setDate(matchDate);

            queueTeam.enqueue(firstTeam);
            queueTeam.enqueue(secondTeam);
            queueMatch.enqueue(firstMatch);
        }

        @Test
        void testEnqueueWithElements() {
            Team newTeam = new Team("Liverpool", "Inglaterra", 6);
            Match newMatch = new Match(newTeam, secondTeam);

            queueTeam.enqueue(newTeam);
            queueMatch.enqueue(newMatch);

            assertEquals(3, queueTeam.size());
            assertEquals(2, queueMatch.size());
            assertEquals(firstTeam, queueTeam.peek());
            assertEquals(firstMatch, queueMatch.peek());
        }

        @Test
        void testDequeue() {
            Team dequeuedTeam = queueTeam.dequeue();
            Match dequeuedMatch = queueMatch.dequeue();

            assertEquals(firstTeam, dequeuedTeam);
            assertEquals(firstMatch, dequeuedMatch);
            assertEquals(1, queueTeam.size());
            assertEquals(0, queueMatch.size());
            assertEquals(secondTeam, queueTeam.peek());
            assertNull(queueMatch.peek());
        }

        @Test
        void testPeek() {
            assertEquals(firstTeam, queueTeam.peek());
            assertEquals(firstMatch, queueMatch.peek());
            assertEquals(2, queueTeam.size());
            assertEquals(1, queueMatch.size());
        }

        @Test
        void testMultipleOperations() {
            Team thirdTeam = new Team("PSG", "Francia", 0);
            Match secondMatch = new Match(thirdTeam, firstTeam);

            queueTeam.enqueue(thirdTeam);
            assertEquals(3, queueTeam.size());

            Team dequeuedTeam = queueTeam.dequeue();
            assertEquals(firstTeam, dequeuedTeam);
            assertEquals(secondTeam, queueTeam.peek());

            queueMatch.enqueue(secondMatch);
            Match dequeuedMatch = queueMatch.dequeue();
            assertEquals(firstMatch, dequeuedMatch);
            assertEquals(secondMatch, queueMatch.peek());
        }
    }

    @Nested
    class QueueRemovalOperations {
        private Queue<Team> queue;

        @BeforeEach
        void setUp() {
            queue = new Queue<>();
            queue.enqueue(new Team("Manchester City", "Inglaterra", 1));
            queue.enqueue(new Team("Napoli", "Italia", 0));
            queue.enqueue(new Team("Porto", "Portugal", 2));
        }

        @Test
        void testCompleteRemoval() {
            assertEquals(3, queue.size());

            queue.dequeue();
            queue.dequeue();
            queue.dequeue();

            assertTrue(queue.isEmpty());
            assertNull(queue.peek());

            queue.enqueue(new Team("Ajax", "Holanda", 4));
            assertEquals(1, queue.size());
            assertEquals("Ajax", queue.peek().getName());
        }

        @Test
        void testInterleavedOperations() {
            queue.dequeue();
            queue.enqueue(new Team("Ajax", "Holanda", 4));
            queue.dequeue();

            assertEquals(2, queue.size());
            assertEquals("Porto", queue.peek().getName());
        }
    }
}