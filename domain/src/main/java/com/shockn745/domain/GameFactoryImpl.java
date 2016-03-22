package com.shockn745.domain;

import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.application.driving.dto.Player;
import com.shockn745.utils.NullObjects;

/**
 * @author Kempenich Florian
 */
public class GameFactoryImpl implements GameFactory {

    @Override
    public Game makeNewGame() {
        Player[][] emptyBoard = NullObjects.makeEmptyBoard();

        GameStatus newGameStatus =
                new GameStatus(GameStatus.NO_ID, emptyBoard, Player.noPlayer(), Player.noPlayer());

        Board board = new BoardImpl(emptyBoard);
        return new GameImpl(board, newGameStatus);
    }

    @Override
    public Game makeGame(GameStatus status) {
        Board board = new BoardImpl(status.board);
        return new GameImpl(board, status);
    }
}
