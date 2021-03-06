package minesweeper;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HighScoresFrame extends JFrame {
    static JTable table;
    private final HighScoresData data;

    public HighScoresFrame() {
        //set window properties
        super("High Scores");
        ImageIcon img = new ImageIcon(System.getProperty("user.dir") + "\\src\\minesweeper.png");
        setIconImage(img.getImage());

        //HighScoresData
        data = HighScoresData.getInstance();

        //init components
        initComponents();
        pack();
        setSize(new Dimension(500, 500));
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        this.setLayout(new BorderLayout());

        //Jatble
        table = new JTable(data);
        table.setFillsViewportHeight(true);

        //set width
        table.getColumnModel().getColumn(0).setPreferredWidth(500 / 2);
        table.getColumnModel().getColumn(1).setPreferredWidth(500 / 3);
        table.getColumnModel().getColumn(2).setPreferredWidth(500 / 6);

        //sort
        table.setRowSorter(new TableRowSorter<>(data));

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();

        sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
        sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));

        sorter.setSortKeys(sortKeys);
        sorter.sort();

        //set renderers
        table.setDefaultRenderer(String.class, new ScoreTableCellRenderer(table.getDefaultRenderer(String.class)));
        table.setDefaultRenderer(Enum.class, new ScoreTableCellRenderer(table.getDefaultRenderer(Enum.class)));
        table.setDefaultRenderer(Integer.class, new ScoreTableCellRenderer(table.getDefaultRenderer(Integer.class)));

        this.add(new JScrollPane(table), BorderLayout.CENTER);

/*
        //Add winners manually (kinda cheating lol...)
        JPanel addPanel = new JPanel();
        addPanel.add(new JLabel("Name:"));
        JTextField newPlayerName = new JTextField(20);
        addPanel.add(newPlayerName);

        addPanel.add(new JLabel("Difficulty:"));
        JTextField newDiff = new JTextField(10);
        addPanel.add(newDiff);

        addPanel.add(new JLabel("Time:"));
        JTextField newTime = new JTextField(3);
        addPanel.add(newTime);

        JButton addButton = new JButton("Add Manually");
        addButton.addActionListener(ae -> data.addScore(newPlayerName.getText(), Difficulty.valueOf(newDiff.getText()), Integer.parseInt(newTime.getText())));
        addPanel.add(addButton);

        this.add(addPanel,BorderLayout.SOUTH);
*/

    }

    static class ScoreTableCellRenderer implements TableCellRenderer {
        private final TableCellRenderer renderer;

        public ScoreTableCellRenderer(TableCellRenderer defRenderer) {
            //"fancy" rendering for the Enum class (uses String default renderer)
            if (defRenderer == table.getDefaultRenderer(Enum.class)) {
                this.renderer = table.getDefaultRenderer(String.class);
            } else
                this.renderer = defRenderer;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
}