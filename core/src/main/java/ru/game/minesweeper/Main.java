package ru.game.minesweeper;

import com.badlogic.gdx.Game;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {

    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }

    public void startGame(int bombCount) {
        setScreen(new GameScreen(this, bombCount));
    }
}
