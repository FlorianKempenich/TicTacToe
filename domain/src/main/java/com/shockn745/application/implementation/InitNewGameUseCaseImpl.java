package com.shockn745.application.implementation;

import com.shockn745.GameRepository;
import com.shockn745.application.GameStatus;
import com.shockn745.application.InitNewGameUseCase;
import com.shockn745.domain.Board;
import com.shockn745.domain.BoardImpl;
import com.shockn745.domain.Game;
import com.shockn745.domain.GameImpl;

/**
 * @author Kempenich Florian
 */
public class InitNewGameUseCaseImpl implements InitNewGameUseCase {

    private final GameRepository gameRepository;

    public InitNewGameUseCaseImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public void execute(Callback callback) {
        Board board = new BoardImpl();
        Game game = new GameImpl(board);
        int id = gameRepository.saveGame(game);

        GameStatus status = game.makeStatus(id);

        callback.newGameReady(status);
    }
}
