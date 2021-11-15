package minesweeper;

public class GameLogic {
    Grid grid;
    Frame frame;
    Timer timer;

    int width = 16;
    int height = 16;
    int bombs = 30;

    void initGame() {
        timer = new Timer();
        grid = new Grid(width, height);
        frame = new Frame(grid, this, timer);
        grid.putBombs(bombs);
        grid.checkNeighbours();
    }

    public void startGame() {
        frame.drawGame();
    }

    public void tileLeftClick(int x, int y) {
        if(!timer.isRunning()){timer.start();}
        if (!grid.isTileFlagged(x, y)) {
            grid.reveal(x, y);
            if (grid.getRevealed() == width * height - bombs) {
                grid.revealAll();
                if (!isExploded())
                    System.out.println("YOU WON");
                    timer.stop();
            }
        }
    }

    public void tileRightClick(int x, int y) {
        if(!timer.isRunning()){timer.start();}
        grid.flag(x, y);
    }


    public boolean isExploded() {
        return grid.isExploded();
    }

    void endGame() {

        grid.revealAll();
        System.out.println("GAME OVER");
    }

    public void SetGameAttributes(int width, int height, int bombs) {
        this.width = width;
        this.height = height;
        this.bombs = bombs;
    }
}
