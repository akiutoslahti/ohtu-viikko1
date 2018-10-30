package ohtuesimerkki;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class StatisticsTest {

    Reader readerStub = new Reader() {

        public List<Player> getPlayers() {
            ArrayList<Player> players = new ArrayList<Player>();

            players.add(new Player("Semenko", "EDM", 4, 12));
            players.add(new Player("Lemieux", "PIT", 45, 54)); //99
            players.add(new Player("Kurri", "EDM", 37, 53));
            players.add(new Player("Yzerman", "DET", 42, 56)); //98
            players.add(new Player("Gretzky", "EDM", 35, 89)); //124

            return players;
        }
    };

    Statistics stats;

    @Before
    public void setUp() {
        // luodaan Statistics-olio joka käyttää "stubia"
        stats = new Statistics(readerStub);
    }

    @Test
    public void searchNonExistingPlayer() {
        assertNull(stats.search("Matti Meikaelaeinen"));
    }

    @Test
    public void searchExistingPlayer() {
        Player semenko = stats.search("Semenko");
        assertNotNull(semenko);
        assertEquals("Semenko", semenko.getName());
        assertEquals("EDM", semenko.getTeam());
        assertEquals(4, semenko.getGoals());
        assertEquals(12, semenko.getAssists());
        assertEquals(16, semenko.getPoints());
    }

    @Test
    public void teamNonExisting() {
        List<Player> team = stats.team("Hyrylaen vauhtiveikot");
        assertEquals(0, team.size());
    }

    @Test
    public void teamExisting() {
        String[] expectedMembers = {"Semenko", "Kurri", "Gretzky"};
        List<Player> team = stats.team("EDM");
        assertEquals(3, team.size());
        for (Player p : team) {
            assertTrue(Arrays.asList(expectedMembers).contains(p.getName()));
        }
    }

    @Test
    public void topScorersZero() {
        assertNull(stats.topScorers(0));
    }

    @Test
    public void topScorersThree() {
        List<Player> topScorers = stats.topScorers(3);
        String[] expectedScorers = {"Lemieux", "Yzerman", "Gretzky"};
        assertEquals(3, topScorers.size());
        for (Player p : topScorers) {
            assertTrue(Arrays.asList(expectedScorers).contains(p.getName()));
        }
    }

    @Test
    public void topScorersSix() {
        List<Player> topScorers = stats.topScorers(6);
        String[] expectedScorers = {"Semenko", "Lemieux", "Kurri", "Yzerman", "Gretzky"};
        assertEquals(5, topScorers.size());
        for (Player p : topScorers) {
            assertTrue(Arrays.asList(expectedScorers).contains(p.getName()));
        }
    }

}
