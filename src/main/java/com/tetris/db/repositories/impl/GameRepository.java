package com.tetris.db.repositories.impl;

import com.tetris.db.entity.Game;
import com.tetris.db.repositories.Repository;
import com.tetris.db.utils.HibernateUtil;
import com.tetris.model.GameState;
import org.hibernate.Session;
import java.util.List;
import java.util.Optional;

public class GameRepository implements Repository {
    public static void main(String[] args) {
        GameRepository g = new GameRepository();
        g.createNewGame();
    }


    public Optional<Integer> getActiveGameId() {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Game> games;
            // make more cute realisation!!!

            games = session.createCriteria(Game.class).list();
            for(Game g: games){
                if(g.getState().equals(GameState.ACTIVE)){
                    return Optional.of(g.getGameId());
                }
            }
            return Optional.empty();
        }
    }

    public int createNewGame() {
        Game game = new Game();
        game.setState(GameState.ACTIVE);
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Game> games;
            session.beginTransaction();
            session.save(game);
            session.getTransaction().commit();
            // make more cute realisation!!!

            games = session.createCriteria(Game.class).list();
            return games.get(games.size()-1).getGameId();
        }
    }

    public void finishGame(int gameId) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Game game = session.load(Game.class, gameId);
            game.setState(GameState.FINISH);
            session.update(game);
            session.getTransaction().commit();
        }
    }

}
