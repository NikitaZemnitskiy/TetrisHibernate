package com.tetris.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tetris.game.handler.MoveEventType;
import com.tetris.model.Point;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.tetris.game.handler.MoveEventType.LEFT_ROTATE;
import static com.tetris.game.handler.MoveEventType.RIGHT_ROTATE;


@Data
@Builder
@Slf4j
public class Figure {

    private final List<Point> points;
    private final Point pivot;
    private final Point currentCoordinateOnBoard;

    @JsonCreator
    public Figure(@JsonProperty("points")  List<Point> points, @JsonProperty("pivot") Point pivot, @JsonProperty("currentCoordinateOnBoard") Point currentCoordinateOnBoard) {
        this.points = points;
        this.pivot = pivot;
        this.currentCoordinateOnBoard = currentCoordinateOnBoard;
    }

    public Figure getNewFigureByMoveEventType(MoveEventType eventType) {
        return eventType == LEFT_ROTATE || eventType == RIGHT_ROTATE ? rotate(eventType) : move(eventType);
    }

    private Figure move(MoveEventType eventType) {
        log.debug("Figure move event {}",eventType);
        Point newCoordinateOnBoard;
        switch (eventType) {
            case MOVE_RIGHT: {
                newCoordinateOnBoard = new Point(currentCoordinateOnBoard.getX() + 1,
                        currentCoordinateOnBoard.getY());
                break;
            }
            case MOVE_LEFT: {
                newCoordinateOnBoard = new Point(currentCoordinateOnBoard.getX() - 1,
                        currentCoordinateOnBoard.getY());
                break;
            }
            case MOVE_DOWN: {
                newCoordinateOnBoard = new Point(currentCoordinateOnBoard.getX(),
                        currentCoordinateOnBoard.getY() + 1);
                break;
            }
            default:
                throw new IllegalArgumentException("Invalid rotate state");
        }
        return new Figure(points, pivot, newCoordinateOnBoard);
    }

    private Figure rotate(MoveEventType eventType) {
        log.debug("Figure rotate event {}", eventType);
        if (eventType == LEFT_ROTATE) {
            return new Figure(points.stream()
                    .map(point -> new Point(getPointByPivot(point).getY() * -1, getPointByPivot(point).getX()))
                    .collect(Collectors.toList()), pivot, currentCoordinateOnBoard);
        }
        if (eventType == RIGHT_ROTATE) {
            return new Figure(points.stream()
                    .map(point -> new Point(getPointByPivot(point).getY(), getPointByPivot(point).getX() * -1))
                    .collect(Collectors.toList()), pivot, currentCoordinateOnBoard);
        }

        throw new IllegalArgumentException("Invalid rotate state");
    }

    public List<Point> getPointsByBoardCoordinates() {
        return points.stream().map(point -> new Point(point.getX() + currentCoordinateOnBoard.getX(),
                point.getY() + currentCoordinateOnBoard.getY())).collect(Collectors.toList());
    }


    private Point getPointByPivot(Point point) {
        return new Point(point.getX() - pivot.getX(), point.getY() - pivot.getY());
    }
    /*public List<Figure> getClassicFigures(){
        Figure figure1 = createFigure1();
        Figure figure2 = createFigure2();
        Figure figure3 = createFigure3();
        Figure figure4 = createFigure4();
        List<Figure> figures = new ArrayList<>();
        figures.add(figure1);
        figures.add(figure2);
        figures.add(figure3);
        figures.add(figure4);
        return figures;
    }
    public Figure createFigure1(){
        List<Point> points = new ArrayList<>();
        points.add(new Point(0,0));
        points.add(new Point(1,0));
        points.add(new Point(1,1));
        points.add(new Point(2,1));
        Point pivot = new Point(1,1);
        Point startPoint = new Point(5,10);
        return new Figure(points, pivot, startPoint);
    }
    public Figure createFigure2(){
        List<Point> points = new ArrayList<>();
        points.add(new Point(2,2));
        points.add(new Point(1,1));
        points.add(new Point(0,2));
        points.add(new Point(2,0));
        Point pivot = new Point(1,1);
        Point startPoint = new Point(5,10);
        return new Figure(points, pivot, startPoint);
    }
    public Figure createFigure3(){
        List<Point> points = new ArrayList<>();
        points.add(new Point(1,1));
        points.add(new Point(2,1));
        points.add(new Point(3,1));
        points.add(new Point(4,1));
        Point pivot = new Point(2,1);
        Point startPoint = new Point(5,10);
        return new Figure(points, pivot, startPoint);
    }
    public Figure createFigure4(){
        List<Point> points = new ArrayList<>();
        points.add(new Point(0,0));
        points.add(new Point(0,1));
        points.add(new Point(2,1));
        points.add(new Point(3,1));
        Point pivot = new Point(2,1);
        Point startPoint = new Point(5,10);
        return new Figure(points, pivot, startPoint);
    }*/

}
