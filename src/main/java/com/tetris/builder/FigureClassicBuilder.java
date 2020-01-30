package com.tetris.builder;

import com.tetris.db.repositories.impl.FigureRepository;
import com.tetris.game.Figure;
import com.tetris.model.Point;


public class FigureClassicBuilder extends AbstractFigureBuilder {

    public FigureClassicBuilder(int gameId) {
        super(gameId);
    }

    @Override
    public Figure next(Point boardStartPoint) {
        int randomFigureId = (int) (Math.random() * 4) + 1;
        FigureRepository figureRepository = new FigureRepository();
        repository.saveNewFigure(getGameId(), randomFigureId);
        return figureRepository.getFigureById(randomFigureId);
    }
}
