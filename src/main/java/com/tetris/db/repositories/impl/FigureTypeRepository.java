package com.tetris.db.repositories.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tetris.db.entity.FigureType;
import com.tetris.db.utils.HibernateUtil;
import com.tetris.game.Figure;
import lombok.SneakyThrows;
import org.hibernate.Session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FigureTypeRepository {

@SneakyThrows
    public Set<Figure> getFigures() {
        Set<Figure> figures = new HashSet<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
         List<FigureType> list = session.createCriteria(FigureType.class).list();
         for(FigureType f:list){
             Figure figure = objectMapper.readValue(f.getFigureStructure(), Figure.class);
             figures.add(figure);
         }
        }
        return figures;
    }
}
