package кгru.game.me;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {

    SpriteBatch batch;
    MinesweeperGame game;

    @Override
    public void create() {
        batch = new SpriteBatch();
        game = new MinesweeperGame(10, 10, 10); // 10x10 поле с 10 минами
        int cellSize = 61; // Размер ячейки в пикселях

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                // Преобразование координаты Y
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
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        game.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
