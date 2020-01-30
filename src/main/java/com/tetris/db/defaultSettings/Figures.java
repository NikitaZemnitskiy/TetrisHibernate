package com.tetris.db.defaultSettings;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tetris.db.entity.FigureType;
import com.tetris.db.entity.Game;
import com.tetris.db.utils.HibernateUtil;
import com.tetris.game.Figure;
import com.tetris.model.Point;
import lombok.SneakyThrows;
import org.hibernate.Session;

import java.util.ArrayList;

import java.util.List;

public class Figures {
    public static void main(String[] args) {
        Figures figures = new Figures();
        figures.setClassicFigures();
    }
    @SneakyThrows
    public void setClassicFigures() {
        Figure figure1 = createFigure1();
        Figure figure2 = createFigure2();
        Figure figure3 = createFigure3();
        Figure figure4 = createFigure4();
        List<Figure> figures = new ArrayList<>();
        figures.add(figure1);
        figures.add(figure2);
        figures.add(figure3);
        figures.add(figure4);
        for(Figure f:figures) {
            FigureType figureType = new FigureType();
            ObjectMapper objectMapper = new ObjectMapper();
            String jsString = objectMapper.writeValueAsString(f);
            figureType.setFigureStructure(jsString);
            System.out.println(jsString);
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                session.beginTransaction();
                session.save(figureType);
                session.getTransaction().commit();
            }
        }
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
    }
}
