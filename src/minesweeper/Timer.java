package minesweeper;

import javax.swing.*;
import java.awt.*;

class Timer extends JPanel {
    private static Timer instance;
    private final Runnable counter;
    int seconds;
    SevenSegmentDisplay display;
    private boolean running;

    private Timer() {
        seconds = 0;
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        display = new SevenSegmentDisplay(this, 0);
        display.displayInactive();

        //runnable
        counter = () -> {
            while (running) {
                //tick
                seconds++;
                drawTimer();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static Timer getInstance() {
        if (instance == null) {
            instance = new Timer();
        }
        return instance;
    }

    private void drawTimer() {
        display.drawDigits(seconds);
    }

    public void start() {
        running = true;
        new Thread(counter).start();
    }

    public void stop() {
        running = false;
        drawTimer();
    }

    public void resetTimer() {
        instance = new Timer();
        drawTimer();
    }

    public int getSeconds() {
        return seconds;
    }

}