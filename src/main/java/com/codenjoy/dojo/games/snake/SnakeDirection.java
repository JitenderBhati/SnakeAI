package com.codenjoy.dojo.games.snake;

import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;

import java.util.List;

public class SnakeDirection {
    public Direction getSnakeNextDirection(final Board board) {
        //TODO: Temporary basis

        List<Point> snakeBarriers = board.getBarriers();
        snakeBarriers.removeAll(board.getSnake());
        Direction safeDirection = isWall(board, board.getSnake(), board.getSnakeDirection(), snakeBarriers);
        Point snakeHead = board.getHead();
        if (safeDirection == Direction.UP) {
            snakeHead.setY(snakeHead.getY()+1);
        }
        if (safeDirection == Direction.DOWN) {
            snakeHead.setY(snakeHead.getY()-1);
        }if (safeDirection == Direction.LEFT) {
            snakeHead.setX(snakeHead.getX()-1);
        }
        if (safeDirection == Direction.RIGHT) {
            snakeHead.setX(snakeHead.getX()+1);
        }

        List<Point> applesPoints = board.getApples();
        for(Point apple : applesPoints) {
            if(apple.getX() == snakeHead.getX() && apple.getY()<snakeHead.getY()) {
                return Direction.DOWN;
            }
            if(apple.getX() == snakeHead.getX() && apple.getY()>snakeHead.getY()) {
                return Direction.UP;
            }
            if(apple.getX() > snakeHead.getX() && apple.getY()==snakeHead.getY()) {
                return Direction.RIGHT;
            }
            if(apple.getX() < snakeHead.getX() && apple.getY()==snakeHead.getY()) {
                return Direction.LEFT;
            }
        }
        return  safeDirection;
    }

    private Direction isWall(final Board board, List<Point> snakePoint, Direction snakeCurrentDirection,List<Point> snakeBarriers) {
        List<Point> barriers = snakeBarriers;
        Point snakeHead = snakePoint.get(0);
        switch (snakeCurrentDirection) {
            case UP:
                for (Point point : barriers) {
                    if (point.getY() < snakeHead.getY())
                        continue;
                    //Only for walls
                    if (point.getY() - snakeHead.getY() < 1) {
                        snakePoint = board.getSnake();
                        snakeHead = snakePoint.get(0);
                        snakeHead.setX(snakeHead.getX() - 1);
                        return isSafeToMove(board, snakePoint, Direction.LEFT, snakeBarriers);
                    }
                }
                return snakeCurrentDirection;
            case DOWN:
                for (Point point : barriers) {
                    if (point.getY() > snakeHead.getY())
                        continue;
                    if (Math.abs(point.getY() - snakeHead.getY()) < 1) {
                        snakePoint = board.getSnake();
                        snakeHead = snakePoint.get(0);
                        snakeHead.setX(snakeHead.getX() - 1);
                        return isSafeToMove(board, snakePoint, Direction.LEFT, snakeBarriers);
                    }
                }
                return snakeCurrentDirection;
            case LEFT:
                for (Point point : barriers) {
                    if (point.getX() > snakeHead.getX())
                        continue;
                    if (point.getX() - snakeHead.getX() < 1) {
                        snakePoint = board.getSnake();
                        snakeHead = snakePoint.get(0);
                        snakeHead.setY(snakeHead.getY() + 1);
                        return isSafeToMove(board, snakePoint, Direction.UP, snakeBarriers);
                    }
                }
                return snakeCurrentDirection;
            case RIGHT:
                for (Point point : barriers) {
                    if (point.getX() < snakeHead.getX())
                        continue;
                    if (Math.abs(point.getX() - snakeHead.getX()) < 1 ) {
                        snakePoint = board.getSnake();
                        snakeHead = snakePoint.get(0);
                        snakeHead.setY(snakeHead.getY() - 1);
                        return isSafeToMove(board, snakePoint, Direction.DOWN, snakeBarriers);
                    }
                }
                return snakeCurrentDirection;
            default:
                return Direction.UP;
        }
    }

    private Direction isSafeToMove(final Board board, List<Point> snakePoint, Direction snakeCurrentDirection, List<Point> snakeBarriers) {
        List<Point> barriers = snakeBarriers;
        Point snakeHead = snakePoint.get(0);
        switch (snakeCurrentDirection) {
            case UP:
                for (Point point : barriers) {
                    if (point.getY() < snakeHead.getY())
                        continue;
                    if (point.getX() - snakeHead.getX() < 1 || point.getY() - snakeHead.getY() < 1) {
                        snakePoint = board.getSnake();
                        snakeHead = snakePoint.get(0);
                        snakeHead.setX(snakeHead.getX() - 1);
                        return isSafeToMove(board, snakePoint, Direction.LEFT,snakeBarriers);
                    }
                }
                return snakeCurrentDirection;
            case LEFT:
                for (Point point : barriers) {
                    if (point.getX() > snakeHead.getX())
                        continue;
                    if (Math.abs(point.getX() - snakeHead.getX()) < 1) {
                        snakePoint = board.getSnake();
                        snakeHead = snakePoint.get(0);
                        snakeHead.setX(snakeHead.getX() + 1);
                        return isSafeToMove(board, snakePoint, Direction.RIGHT,snakeBarriers);
                    }
                    return snakeCurrentDirection;
                }
            case RIGHT:
                for (Point point : barriers) {
                    if (point.getX() < snakeHead.getX())
                        continue;
                    if (Math.abs(point.getX() - snakeHead.getX()) < 1) {
                        snakePoint = board.getSnake();
                        snakeHead = snakePoint.get(0);
                        snakeHead.setX(snakeHead.getX() + 1);
                        return isSafeToMove(board, snakePoint, Direction.LEFT, snakeBarriers);
                    }
                    return snakeCurrentDirection;
                }
            case DOWN:
                for (Point point : barriers) {
                    if (point.getY() > snakeHead.getY())
                        continue;
                    if (Math.abs(point.getY() - snakeHead.getY()) < 1) {
                        snakePoint = board.getSnake();
                        snakeHead = snakePoint.get(0);
                        snakeHead.setX(snakeHead.getX() + 1);
                        return isSafeToMove(board, snakePoint, Direction.LEFT, snakeBarriers);
                    }
                    return snakeCurrentDirection;
                }
                return Direction.DOWN;
            default:
                return Direction.DOWN;
        }
    }
}
