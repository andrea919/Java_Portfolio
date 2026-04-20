package edu.monmouth.CS205L.boggleLeaderboard;

import java.util.*;

/**
 * Project 5
 * YOU DON'T NEED TO MODIFY THIS FILE!!!
 *
 * Runs four benchmarks against all three tree implementations and prints
 * a comparison table. Study the output to answer the analysis questions.
 */
public class LeaderboardDriver {

    // Player names
    private static final String[] PLAYERS = {
        "Andrea", "Chloe", "Sami", "Sasha", "Warda", "Dr. Callahan"
    };

    public static void main(String[] args) {

        System.out.println("=================================================");
        System.out.println("  RankDB Leaderboard Benchmark");
        System.out.println("=================================================");

        // ── Benchmark 1: sequential insertion (adversarial for BST) ─────────
        System.out.println("\nBENCHMARK 1: Sequential insertion (scores 1..500)");
        System.out.println("  This is the worst case for an unbalanced BST.");
        runBenchmark1();

        // ── Benchmark 2: random insertion ────────────────────────────────────
        System.out.println("\nBENCHMARK 2: Random insertion (500 random scores)");
        runBenchmark2();

        // ── Benchmark 3: hot player (temporal locality) ──────────────────────
        System.out.println("\nBENCHMARK 3: Hot player lookups (same 5 players, 1000 gets)");
        System.out.println("  Splay trees excel here -- watch the comparison counts.");
        runBenchmark3();

        // ── Benchmark 4: range query ─────────────────────────────────────────
        System.out.println("\nBENCHMARK 4: Range query (find all players scoring 200-400)");
        runBenchmark4();

        // ── Live demo ────────────────────────────────────────────────────────
        System.out.println("\n=================================================");
        System.out.println("  LIVE DEMO with Red-Black Tree");
        System.out.println("=================================================");
        liveDemo();
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private static Leaderboard[] makeLeaderboards() {
        return new Leaderboard[] {
            new Leaderboard("BST",   new BSTMap<>()),
            new Leaderboard("Splay", new SplayMap<>()),
            new Leaderboard("RBT",   new RBTMap<>())
        };
    }

    private static void runBenchmark1() {
        Leaderboard[] boards = makeLeaderboards();
        for (Leaderboard lb : boards) lb.map().resetStats();   // warm up

        for (Leaderboard lb : boards) {
            lb.map().resetStats();
            for (int i = 1; i <= 500; i++) {
                lb.addScore(i, PLAYERS[i % PLAYERS.length]);
            }
            lb.printAndResetStats("insert 500 sequential");
        }
    }

    private static void runBenchmark2() {
        Leaderboard[] boards = makeLeaderboards();
        Random rng = new Random(42);
        int[] scores = new int[500];
        for (int i = 0; i < 500; i++) scores[i] = rng.nextInt(100_000);

        for (Leaderboard lb : boards) {
            lb.map().resetStats();
            for (int i = 0; i < 500; i++) {
                lb.addScore(scores[i], PLAYERS[i % PLAYERS.length]);
            }
            lb.printAndResetStats("insert 500 random");
        }
    }

    private static void runBenchmark3() {
        Leaderboard[] boards = makeLeaderboards();
        Random rng = new Random(42);

        // Populate with 500 random scores
        for (Leaderboard lb : boards) {
            for (int i = 0; i < 500; i++) {
                lb.addScore(rng.nextInt(100_000), PLAYERS[i % PLAYERS.length]);
            }
        }

        // Pick 5 "hot" scores that exist in all trees
        int[] hotScores = new int[5];
        List<Integer> keys = boards[0].map().rangeSearch(0, Integer.MAX_VALUE);
        for (int i = 0; i < 5; i++) hotScores[i] = keys.get(i * 20);

        // 1000 lookups heavily biased toward those 5 scores
        for (Leaderboard lb : boards) {
            lb.map().resetStats();
            for (int i = 0; i < 1000; i++) {
                int score = (i % 10 < 8)
                        ? hotScores[rng.nextInt(5)]   // 80% hot
                        : rng.nextInt(100_000);        // 20% cold
                lb.getPlayer(score);
            }
            lb.printAndResetStats("1000 get (80% hot)");
        }
    }

    private static void runBenchmark4() {
        Leaderboard[] boards = makeLeaderboards();
        Random rng = new Random(42);
        for (Leaderboard lb : boards) {
            for (int i = 0; i < 500; i++) {
                lb.addScore(rng.nextInt(600), PLAYERS[i % PLAYERS.length]);
            }
        }
        for (Leaderboard lb : boards) {
            lb.map().resetStats();
            lb.map().rangeSearch(200, 400);
            lb.printAndResetStats("rangeSearch(200,400)");
        }
    }

    private static void liveDemo() {
        Leaderboard lb = new Leaderboard("RBT", new RBTMap<>());

        System.out.println("\nAdding players...");
        String[][] entries = {
            {"8450", "AceHunter"}, {"9210", "BlitzKing"}, {"7330", "CyberWolf"},
            {"9800", "DawnRaider"}, {"6550", "EchoStrike"}, {"8900", "FrostByte"},
            {"5200", "GhostRun"},  {"9100", "HyperNova"},  {"7750", "IronClad"},
            {"8300", "JetStream"}
        };
        for (String[] e : entries) lb.addScore(Integer.parseInt(e[0]), e[1]);

        System.out.println("  Top player    : " + lb.topPlayer());
        System.out.println("  Bottom player : " + lb.bottomPlayer());
        System.out.println("  Total players : " + lb.size());

        System.out.println("\nRange query -- scores 8000 to 9500:");
        lb.printScoreRange(8000, 9500);

        System.out.println("\nDawnRaider retires (score 9800 removed)...");
        lb.removeScore(9800);
        System.out.println("  New top player: " + lb.topPlayer());
    }
}
