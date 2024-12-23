package кгru.game.me;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MinesweeperGame {


    private final int width;
    private final int height;
    private final int numMines;
    private final Cell[][] cells;
    private final Texture cellTexture;
    private final Texture openedCellTexture;
    private final Texture mineTexture;
    private final Texture flagTexture;
    private final Texture[] numberTextures;
    private static final int CELL_SIZE = 61;
    private boolean gameOver;
    private boolean win;
    private BitmapFont font;
    private String gameOverMessage;


    public MinesweeperGame(int width, int height, int numMines) {
        this.width = width;
        this.height = height;
        this.numMines = numMines;
        this.cells = new Cell[width][height];
        this.cellTexture = new Texture("cell.png");
        this.mineTexture = new Texture("mine.png");
        this.flagTexture = new Texture("flag.png");
        this.openedCellTexture = new Texture("openedCell.png");
        this.numberTextures = new Texture[8];
        for (int i = 1; i <= 8; i++) {
            numberTextures[i - 1] = new Texture("Minesweeper_LAZARUS_61x61_" + i + ".png");
        }
        this.font = new BitmapFont();
        this.font.setColor(Color.RED);
        initialize();
    }

    private void initialize() {
        // Инициализация клеток и установка мин
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell();
            }
        }

        // Установка мин
        int minesPlaced = 0;
        while (minesPlaced < numMines) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);
            if (!cells[x][y].isMine()) {
                cells[x][y].setMine(true);
                minesPlaced++;
            }
        }

        // Подсчет соседних мин
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (!cells[x][y].isMine()) {
                    cells[x][y].setAdjacentMines(countAdjacentMines(x, y));
                }
            }
        }

        gameOver = false;
        win = false;
        gameOverMessage = "";
    }

    private int countAdjacentMines(int x, int y) {
        int count = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = x + dx;
                int ny = y + dy;
                if (nx >= 0 && ny >= 0 && nx < width && ny < height && cells[nx][ny].isMine()) {
                    count++;
                }
            }
        }
        return count;
    }

    public void openCell(int x, int y) {
        if (gameOver || x < 0 || y < 0 || x >= width || y >= height || cells[x][y].isOpened()) {
            return;
        }
        cells[x][y].setOpened(true);
        if (cells[x][y].isMine()) {
            gameOver(false);
        } else {
            if (cells[x][y].getAdjacentMines() == 0) {
                openAdjacentCells(x, y);
            }
            if (checkWin()) {
                gameOver(true);
            }
        }
    }

    private void openAdjacentCells(int x, int y) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = x + dx;
                int ny = y + dy;
                openCell(nx, ny);
            }
        }
    }

    public void toggleFlag(int x, int y) {
        if (gameOver || x < 0 || y < 0 || x >= width || y >= height || cells[x][y].isOpened()) {
            return;
        }
        cells[x][y].setFlagged(!cells[x][y].isFlagged());
    }

    private void gameOver(boolean isWin) {
        gameOver = true;
        win = isWin;
        if (isWin) {
            gameOverMessage = "Поздравляем! Вы выиграли!";
        } else {
            gameOverMessage = "Вы проиграли! Попробуйте снова.";
        }
    }

    private boolean checkWin() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (!cells[x][y].isMine() && !cells[x][y].isOpened()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void render(SpriteBatch batch) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (cells[x][y].isOpened()) {
                    if (cells[x][y].isMine()) {
                        batch.draw(mineTexture, x * CELL_SIZE, y * CELL_SIZE);
                    } else {
                        batch.draw(openedCellTexture, x * CELL_SIZE, y * CELL_SIZE);
                        int adjacentMines = cells[x][y].getAdjacentMines();
                        if (adjacentMines > 0) {
                            batch.draw(numberTextures[adjacentMines - 1], x * CELL_SIZE, y * CELL_SIZE);
                        }
                    }
                } else {
                    if (cells[x][y].isFlagged()) {
                        batch.draw(flagTexture, x * CELL_SIZE, y * CELL_SIZE);
                    } else {
                        batch.draw(cellTexture, x * CELL_SIZE, y * CELL_SIZE);
                    }
                }
            }
        }

        // Отображение сообщения о победе или поражении
        if (gameOver) {
            GlyphLayout layout = new GlyphLayout(font, gameOverMessage);
            float width = layout.width;
            float height = layout.height;
            font.draw(batch, gameOverMessage, (this.width * CELL_SIZE - width) / 2, (this.height * CELL_SIZE + height) / 2);
        }
    }
}
