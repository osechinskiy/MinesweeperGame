package ru.game.minesweeper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {

    private SpriteBatch batch;
    private MinesweeperGame game;
    private int cellSize = 61;
    private Main gameInstance;

    public GameScreen(Main gameInstance, int bombCount) {
        this.gameInstance = gameInstance;
        this.batch = new SpriteBatch();
        this.game = new MinesweeperGame(gameInstance, 10, 10, bombCount);

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                int screenHeight = Gdx.graphics.getHeight();
                int gameY = (screenHeight - screenY) / cellSize;
                int gameX = screenX / cellSize;

                if (button == Buttons.LEFT) {
                    game.openCell(gameX, gameY);
                } else if (button == Buttons.RIGHT) {
                    game.toggleFlag(gameX, gameY);
                }
                return true;
            }
        });
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        game.render(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
