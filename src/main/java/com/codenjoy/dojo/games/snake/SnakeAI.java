package com.codenjoy.dojo.games.snake;

import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

public class SnakeAI {
    int rows;
    int cols;
    Board board;
    private final PriorityQueue<Spot> openSet;
    private final List<Spot> closedSet;
    private final Spot[][] spotGrid;

    public SnakeAI(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.openSet = new PriorityQueue<>();
        this.closedSet = new ArrayList<>();
        this.spotGrid = new Spot[this.rows][this.cols];
    }

    public Direction getDirection(final Board board) {
        this.board = board;
        this.deleteBoard();
        this.generateBoard();
        Spot start = spotGrid[this.board.getHead().getX()][this.board.getHead().getY()];
        Spot end = spotGrid[this.board.getApples().get(0).getX()][this.board.getApples().get(0).getY()];
        System.out.println("Start Point -> (" + start.i + "," + start.j + ")");
        System.out.println("End Point -> (" + end.i + "," + end.j + ")");
        openSet.add(start);
        while (!openSet.isEmpty()) {
            Spot current = openSet.poll();
            if (current.i == end.i && current.j == end.j) {
                System.out.println("Got the Path!!");
                Spot temp = current;
                while (!Objects.isNull(temp.previous)) {
                    if (temp.previous.i == start.i && temp.previous.j == start.j) {
                        Point one = new PointImpl(temp.i, temp.j);
                        Point second = this.board.getHead();
                        return second.direction(one);
                    } else
                        temp = temp.previous;
                }
                return this.board.getSnakeDirection();
            }
            closedSet.add(current);
            List<Spot> neighbours = current.neighbour;
            for (Spot neighbour : neighbours) {
                if (!closedSet.contains(neighbour) && !neighbour.isBarrier) {
                    double tempG = current.g + 1;
                    boolean newPath = false;
                    if (openSet.contains(neighbour)) {
                        if (tempG < neighbour.g) {
                            newPath = true;
                            neighbour.g = tempG;
                        }
                    } else {
                        newPath = true;
                        neighbour.g = tempG;
                        openSet.add(neighbour);
                    }
                    if (newPath) {
                        neighbour.h = heuristic(neighbour, end);
                        neighbour.f = neighbour.g + neighbour.h;
                        neighbour.previous = current;
                    }
                }
            }
        }
        System.out.println("******** No Solution Got ***********");
        return this.board.getSnakeDirection();
    }

    private double heuristic(Spot a, Spot b) {
        Point one = new PointImpl(a.i, a.j);
        Point second = new PointImpl(b.i, b.j);
        return one.distance(second);
    }

    private void generateBoard() {
        for (int i = 0; i < rows; ++i)
            for (int j = 0; j < cols; ++j)
                spotGrid[i][j] = new Spot(i, j);
        this.addNeighbours();
//        this.printGrid();
    }

    private void deleteBoard() {
        for (int i = 0; i < rows; ++i)
            for (int j = 0; j < cols; ++j)
                spotGrid[i][j] = null;

    }

    private void printGrid() {
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                System.out.print(spotGrid[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void addNeighbours() {
        for (int i = 0; i < rows; ++i)
            for (int j = 0; j < cols; ++j) {
                spotGrid[i][j].addNeighbour(spotGrid);
                Point tempPoint = new PointImpl(i, j);
                if (this.board.getBarriers().contains(tempPoint))
                    spotGrid[i][j].isBarrier = true;
            }
    }
}
