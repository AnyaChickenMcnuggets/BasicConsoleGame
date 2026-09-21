package net.codestudent.main;

import java.util.Random;

/** Test double: returns queued zero-based results for nextInt(bound), ignoring bound. */
class QueuedRandom extends Random {

    private final int[] results;
    private int index = 0;

    QueuedRandom(int... zeroBasedResults) {
        this.results = zeroBasedResults;
    }

    @Override
    public int nextInt(int bound) {
        return results[index++];
    }
}
