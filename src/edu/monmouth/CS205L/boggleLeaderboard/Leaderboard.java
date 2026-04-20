package edu.monmouth.CS205L.boggleLeaderboard;
import java.util.List;

/**
 * Project 5
 *
 * A leaderboard backed by any SortedMap. Scores are keys; names are values.
 * YOU DON'T NEED TO MODIFY ANYTHING IN THIS FILE!!!
 */
public class Leaderboard {

    private final SortedMap<Integer, String> map;
    private final String name;

    public Leaderboard(String name, SortedMap<Integer, String> map) {
        this.name = name;
        this.map  = map;
    }

    /** Exposes the backing map (for benchmark access). */
    public SortedMap<Integer, String> map() { return map; }

    public void addScore(int score, String player) { map.put(score, player); }
    public String getPlayer(int score)             { return map.get(score);  }
    public void removeScore(int score)             { map.remove(score);      }
    public int size()                              { return map.size();      }

    public String topPlayer() {
        Integer top = map.maxKey();
        return top == null ? "(empty)" : map.get(top) + " [" + top + "]";
    }

    public String bottomPlayer() {
        Integer bot = map.minKey();
        return bot == null ? "(empty)" : map.get(bot) + " [" + bot + "]";
    }

    /** Prints all players with scores in [lo, hi], ascending. */
    public void printScoreRange(int lo, int hi) {
        List<Integer> scores = map.rangeSearch(lo, hi);
        System.out.printf("  Players with scores in [%d, %d]:%n", lo, hi);
        if (scores.isEmpty()) {
            System.out.println("    (none)");
        } else {
            for (int s : scores) {
                System.out.printf("    %-20s %d%n", map.get(s), s);
            }
        }
    }

    /** Prints comparison count since last reset, then resets. */
    public void printAndResetStats(String label) {
        System.out.printf("  [%s] comparisons for %-30s %d%n",
                name, label + ":", map.getComparisonCount());
        map.resetStats();
    }
}
