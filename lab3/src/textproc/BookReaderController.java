package textproc;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class BookReaderController {
    public BookReaderController(GeneralWordCounter counter) {
        SwingUtilities.invokeLater(
                () -> createWindow(counter, "Bookreader", 900, 600));
    }

    private void createWindow(GeneralWordCounter counter, String title,
                              int width, int height) {
        var frame = new JFrame(title);
        frame.setMinimumSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        var pane = frame.getContentPane();
        frame.pack();
        frame.setVisible(true);

        var listModel = new SortedListModel<Map.Entry<String, Integer>>(
                counter.getWordList());

        var list = new JList<Map.Entry<String, Integer>>(listModel);
        var scrollPane = new JScrollPane(list);
        pane.add(scrollPane);


        var panel = new JPanel();

        var b1 = new JButton("Alphabetic");
        b1.addActionListener(event -> listModel.sort(
                (lhs, rhs) -> lhs.getKey().compareTo(rhs.getKey())
        ));
        panel.add(b1);

        var b2 = new JButton("Frequency");
        b2.addActionListener(event -> listModel.sort(
                (lhs, rhs) -> lhs.getValue() - rhs.getValue()
        ));
        panel.add(b2);

        var searchText = new JTextField(40);
        panel.add(searchText);

        var b3 = new JButton("Search");
        b3.addActionListener(event -> {
            for(int i = 0; i < listModel.getSize(); ++i) {
                var element = listModel.getElementAt(i).getKey().toLowerCase();
                var text = searchText.getText().toLowerCase().strip();

                if(text.length() > element.length()) continue;
                if(element.substring(0, text.length()).equals(text)) {
                    list.ensureIndexIsVisible(i);
                    list.setSelectedIndex(i);
                    return;
                }
            }

            JOptionPane.showMessageDialog(
                    null, "No such word found!",
                    "Warning", JOptionPane.ERROR_MESSAGE);
        });
        panel.add(b3);

        searchText.addActionListener(event -> b3.doClick());

        pane.add(panel, BorderLayout.SOUTH);



    }
}
