package com.tetris.builder;

import com.tetris.db.repositories.impl.FigureRepository;
import com.tetris.game.Figure;
import com.tetris.model.Point;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class FigureRestoreBuilder extends AbstractFigureBuilder {
    private final FigureBuilder builder;

    public FigureRestoreBuilder(int gameId, FigureBuilder builder) {
        super(gameId);
        this.builder = builder;
    }


    @Override
    public Figure next(Point boardStartPoint) {
        FigureRepository figureRepository = new FigureRepository();
        // rewrite
        return figureRepository.getFiguresByGameId(getGameId()).get(0);
    }
}
