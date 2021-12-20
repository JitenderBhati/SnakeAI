package com.codenjoy.dojo.games.snake;

import java.util.ArrayList;
import java.util.List;

public class Spot implements Comparable<Spot> {
    final int i;
    final int j;
    double f;
    double g;
    double h;
    Spot previous;
    boolean isBarrier;
    List<Spot> neighbour;

    public Spot(int i, int j) {
        this.i = i;
        this.j = j;
        this.f = 0;
        this.g = 0;
        this.h = 0;
        this.isBarrier = false;
        this.previous = null;
        this.neighbour = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public void addNeighbour(final Spot[][] spotGrid) {
        int x = this.i;
        int y = this.j;
        if (x < spotGrid.length - 1)
            this.neighbour.add(spotGrid[+1][y]);
        if (y < spotGrid[0].length - 1)
            this.neighbour.add(spotGrid[x][y + 1]);
        if (x > 0)
            this.neighbour.add(spotGrid[x - 1][y]);
        if (y > 0)
            this.neighbour.add(spotGrid[x][y - 1]);
//        if (i > 0 && j > 0)
//            this.neighbour.add(spotGrid[i - 1][j - 1]);
//        if (i < spotGrid.length - 1 - 1 && j > 0)
//            this.neighbour.add(spotGrid[i + 1][j - 1]);
//        if (j > 0 && i < spotGrid.length - 1 - 1)
//            this.neighbour.add(spotGrid[i + 1][j - 1]);
//        if (i < spotGrid.length - 1 - 1 && j < spotGrid[0].length - 1 - 1)
//            this.neighbour.add(spotGrid[i + 1][j + 1]);
    }

    @Override
    public int compareTo(Spot o) {
        return (int) (this.f - o.f);
    }

    @Override
    public String toString() {
        return "Spot{" +
                "i=" + i +
                ", j=" + j +
                ", isBarrier=" + isBarrier +
                ", neighbour=" + neighbour.size() +
                '}';
    }
}
