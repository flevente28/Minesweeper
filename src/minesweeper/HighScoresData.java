package minesweeper;

import javax.swing.table.AbstractTableModel;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class HighScoresData extends AbstractTableModel {
    private static HighScoresData instance;

    private List<Score> scores = new ArrayList<>();

    private HighScoresData() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("scores.dat"));
            scores = (List<Score>) ois.readObject();
            ois.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static HighScoresData getInstance() {
        if (instance == null) {
            instance = new HighScoresData();
        }
        return instance;
    }

    public void addScore(String playerName, Difficulty diff, int time) {
        scores.add(new Score(playerName, diff, time));
        fireTableDataChanged();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("scores.dat"));
            oos.writeObject(getScores());
            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getRowCount() {
        return scores.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Score score = scores.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> score.getPlayerName();
            case 1 -> score.getDiff();
            default -> score.getTime();
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Name";
            case 1 -> "Difficulty";
            default -> "Time";
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> String.class;
            case 1 -> Difficulty.class;
            default -> Integer.class;
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return (columnIndex > 1);
    }

    public List<Score> getScores() {
        return scores;
    }
}