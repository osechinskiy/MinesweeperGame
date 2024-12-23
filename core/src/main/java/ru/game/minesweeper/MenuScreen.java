package ru.game.minesweeper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;

public class MenuScreen implements Screen {

    private final Main game;
    private SpriteBatch batch;
    private BitmapFont font;
    private int bombCount;

    public MenuScreen(Main game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.bombCount = 10; // начальное количество бомб
        initializeFont();
    }

    @Override
    public void show() {
        // Здесь можно инициализировать ресурсы, если нужно
    }

    private void initializeFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/roboto.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.characters = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?:";
        font = generator.generateFont(parameter);
        generator.dispose(); // Не забудьте освободить ресурсы
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "Выберите количество бомб: " + bombCount, Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0, Align.center, false);
        font.draw(batch, "Нажмите UP для увеличения, DOWN для уменьшения", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - 30, 0, Align.center, false);
        font.draw(batch, "Нажмите ENTER для начала игры", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - 60, 0, Align.center, false);
        batch.end();

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.UP)) {
            bombCount++;
        }
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.DOWN)) {
            if (bombCount > 1) bombCount--;
        }
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
            game.startGame(bombCount);
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

}
