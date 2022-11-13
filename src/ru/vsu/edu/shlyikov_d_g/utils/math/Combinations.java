package ru.vsu.edu.shlyikov_d_g.utils.math;

public record Combinations(long n, long m) {

    public long solve() {
        if (n == m || m <= 0 || n <= 0) {
            return 1;
        }
        if (n - m == 1 || m == 1) {
            return n;
        }
        if (n - m > m) {
            return factorial(n - m + 1, n) / factorial(m);
        } else {
            return factorial(m + 1, n) / factorial(n - m);
        }
    }

    private long factorial(long m) {
        if (m > 1) {
            return m * factorial(m - 1);
        } else {
            return 1;
        }
    }

    private long factorial(long start, long end) {
        if (start >= end) {
            return end;
        }
        return factorial(start + 1, end) * start;
    }
}
