package com.tetris.game.handler.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tetris.db.entity.FigureType;
import com.tetris.db.entity.Game;
import com.tetris.db.utils.HibernateUtil;
import com.tetris.game.Figure;
import com.tetris.game.handler.MoveEventType;
import com.tetris.game.handler.MoveHandler;
import com.tetris.game.handler.user.UserMoveHandler;
import org.hibernate.Session;

public class DbMoveHandler implements MoveHandler {

    private final UserMoveHandler userMoveHandler;
    private final int restoreGameId;

    public DbMoveHandler(int restoreGameId) {
        this.restoreGameId = restoreGameId;
        userMoveHandler = new UserMoveHandler(restoreGameId);
    }

    @Override
    public MoveEventType getNewMoveEvent() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Game game = session.get(Game.class, restoreGameId);
            MoveEventType met = game.getMoves().get(0).getMoveEventType();
            game.getMoves().remove(0);
            return met;
        }
    }
}
