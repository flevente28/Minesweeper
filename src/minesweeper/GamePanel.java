package minesweeper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {
    private final Grid grid;
    private final GameLogic gameLogic;
    private final int height, width;
    private final int tileDim = 25;
    private final BufferedImage[] pictures;

    GamePanel() {
        grid = Grid.getInstance();
        gameLogic = GameLogic.getInstance();
        height = GameLogic.getInstance().getHeight();
        width = GameLogic.getInstance().getWidth();
        pictures = ImageReader.getInstance().getImages();

        //set panel properties, draw the field
        setLayout(new GridLayout(height, width));
        setBorder(new EmptyBorder(12, 12, 12, 12));
        drawGame();
    }

    public void drawGame() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                //tile not revealed (button)
                if (grid.GetTileNeighbours(x, y) == -1) {
                    JButton tmpButton = new TileButton(x, y);
                    tmpButton.addMouseListener(new ButtonMouseListener(x, y));
                    add(tmpButton);
                }
                //tile revealed (label)
                else {
                    JLabel picLabel = new JLabel(new ImageIcon(pictures[grid.GetTileNeighbours(x, y)]));
                    picLabel.setPreferredSize(new Dimension(tileDim, tileDim));
                    add(picLabel);
                }
            }
        }
        updateUI();
    }

    private class TileButton extends JButton {
        TileButton(int x, int y) {
            setPreferredSize(new Dimension(tileDim, tileDim));
            //set the button's icon
            if (grid.isTileFlagged(x, y))
                setIcon(new ImageIcon(pictures[11]));
            else if (grid.isTileMarked(x, y))
                setIcon(new ImageIcon(pictures[12]));
            else
                setIcon(new ImageIcon(pictures[10]));
        }
    }

    public class ButtonMouseListener implements MouseListener {
        int x, y;

        ButtonMouseListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == 1) {
                gameLogic.tileLeftClick(x, y);
                if (gameLogic.isExploded()) {
                    gameLogic.gameOver();
                }
                //redraw
                removeAll();
                drawGame();
            } else if (e.getButton() == 3) {
                gameLogic.tileRightClick(x, y);
                //redraw
                removeAll();
                drawGame();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
