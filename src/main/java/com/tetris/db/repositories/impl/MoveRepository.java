package com.tetris.db.repositories.impl;

import com.tetris.db.entity.Game;
import com.tetris.db.entity.Move;
import com.tetris.db.repositories.Repository;
import com.tetris.db.utils.HibernateUtil;
import com.tetris.game.handler.MoveEventType;
import com.tetris.model.GameState;
import org.hibernate.Session;

public class MoveRepository implements Repository {
    public static void main(String[] args) {
        MoveRepository mr = new MoveRepository();
        mr.saveNewMoveEvent(1,MoveEventType.MOVE_DOWN);
    }

    public void saveNewMoveEvent(int gameId, MoveEventType moveType) {
        Move move = new Move();
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Game game = session.get(Game.class, gameId);
            move.setGame(game);
            move.setMoveEventType(moveType);
            session.save(move);
            session.getTransaction().commit();
        }
    }


}
