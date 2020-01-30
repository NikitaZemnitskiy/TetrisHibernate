package com.tetris.db.repositories.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tetris.db.entity.FigureType;
import com.tetris.db.entity.Game;
import com.tetris.db.repositories.Repository;
import com.tetris.db.utils.HibernateUtil;
import com.tetris.game.Figure;
import lombok.SneakyThrows;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class FigureRepository implements Repository {
    public static void main(String[] args) {
        FigureRepository figureRepository = new FigureRepository();
        System.out.println(figureRepository.getFigureById(1));

    }

    public void saveNewFigure(int gameId, int figureId) {

        List<FigureType> figures;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Game game = session.get(Game.class, gameId);
            figures = game.getFigures();
            FigureType figureType = session.get(FigureType.class, figureId);
            figures.add(figureType);
            game.setFigures(figures);
            session.save(game);
            session.getTransaction().commit();
        }
    }
@SneakyThrows
    public List<com.tetris.game.Figure> getFiguresByGameId(int gameId) {
        List<com.tetris.game.Figure> figuresList = new ArrayList<>();
        List<FigureType> figuresTypes;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Game game = session.get(Game.class, gameId);
            figuresTypes = game.getFigures();

            ObjectMapper objectMapper = new ObjectMapper();
            for (FigureType f : figuresTypes) {
                String jsonString = f.getFigureStructure();
                com.tetris.game.Figure figure = objectMapper.readValue(jsonString, Figure.class);
                figuresList.add(figure);
            }
            return figuresList;
        }
    }
    @SneakyThrows
    public Figure getFigureById(int id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = session.get(FigureType.class, id).getFigureStructure();
            com.tetris.game.Figure figure = objectMapper.readValue(jsonString, Figure.class);
            session.getTransaction().commit();
            return figure;
        }
    }

}
