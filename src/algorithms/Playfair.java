package algorithms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Playfair extends JFrame {

    JTextArea inputArea = new JTextArea(5, 30);
    JTextArea outputArea = new JTextArea(5, 30);
    JTextField keyField = new JTextField(20);
    JTable matrixTable;
    DefaultTableModel tableModel;

    public Playfair() {

        setTitle("Playfair Cipher");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450, 700);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(new JLabel("Input Text:"));
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        mainPanel.add(new JScrollPane(inputArea));
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));

        mainPanel.add(new JLabel("Key:"));
        mainPanel.add(keyField);
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton encBtn = new JButton("Encrypt");
        JButton decBtn = new JButton("Decrypt");
        buttonPanel.add(encBtn);
        buttonPanel.add(decBtn);
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));

        mainPanel.add(new JLabel("Playfair Matrix:"));

        tableModel = new DefaultTableModel(5,5) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        matrixTable = new JTable(tableModel);
        matrixTable.setRowHeight(40);
        matrixTable.setFont(new Font("Arial", Font.BOLD, 18));
        matrixTable.setRowSelectionAllowed(false);
        matrixTable.setCellSelectionEnabled(false);
        matrixTable.setTableHeader(null);

        for(int i=0;i<5;i++)
            for(int j=0;j<5;j++)
                tableModel.setValueAt("", i, j);

        mainPanel.add(new JScrollPane(matrixTable));
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));

        mainPanel.add(new JLabel("Output Text:"));
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setEditable(false);
        mainPanel.add(new JScrollPane(outputArea));

        add(mainPanel);

        encBtn.addActionListener(e ->
                outputArea.setText(applyCipher(inputArea.getText(), keyField.getText(), true)));

        decBtn.addActionListener(e ->
                outputArea.setText(applyCipher(inputArea.getText(), keyField.getText(), false)));

        setVisible(true);
    }

    private char[][] generateMatrix(String key) {

        String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        key = (key.toUpperCase().replace("J", "I") + alphabet)
                .replaceAll("[^A-Z]", "");

        String finalKey = "";
        for (int i = 0; i < key.length(); i++)
            if (finalKey.indexOf(key.charAt(i)) == -1)
                finalKey += key.charAt(i);

        char[][] matrix = new char[5][5];

        for (int i = 0; i < 25; i++)
            matrix[i / 5][i % 5] = finalKey.charAt(i);

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                tableModel.setValueAt(matrix[i][j], i, j);

        return matrix;
    }

    private String applyCipher(String text, String key, boolean encrypt) {

        char[][] matrix = generateMatrix(key);
        StringBuilder result = new StringBuilder();
        StringBuilder lettersOnly = new StringBuilder();

        for (int i = 0; i < text.length(); i++)
            if (Character.isLetter(text.charAt(i)))
                lettersOnly.append(Character.toUpperCase(text.charAt(i)));

        for (int i = 0; i < lettersOnly.length() - 1; i += 2)
            if (lettersOnly.charAt(i) == lettersOnly.charAt(i + 1))
                lettersOnly.insert(i + 1, 'X');

        if (lettersOnly.length() % 2 != 0)
            lettersOnly.append('X');

        int letterIndex = 0;
        int move = encrypt ? 1 : 4;

        for (int i = 0; i < text.length(); i++) {

            char ch = text.charAt(i);

            if (!Character.isLetter(ch)) {
                result.append(ch);
            } else {

                char a = lettersOnly.charAt(letterIndex++);
                char b = lettersOnly.charAt(letterIndex++);

                int[] posA = findPosition(matrix, a);
                int[] posB = findPosition(matrix, b);

                char newA, newB;

                if (posA[0] == posB[0]) {
                    newA = matrix[posA[0]][(posA[1] + move) % 5];
                    newB = matrix[posB[0]][(posB[1] + move) % 5];

                } else if (posA[1] == posB[1]) {
                    newA = matrix[(posA[0] + move) % 5][posA[1]];
                    newB = matrix[(posB[0] + move) % 5][posB[1]];

                } else {
                    newA = matrix[posA[0]][posB[1]];
                    newB = matrix[posB[0]][posA[1]];
                }

                result.append(Character.isLowerCase(ch) ?
                        Character.toLowerCase(newA) : newA);

                i++;
                if (i < text.length()) {
                    char nextCh = text.charAt(i);
                    result.append(Character.isLowerCase(nextCh) ?
                            Character.toLowerCase(newB) : newB);
                }
            }
        }

        return result.toString();
    }

    private int[] findPosition(char[][] matrix, char ch) {
        for (int r = 0; r < 5; r++)
            for (int c = 0; c < 5; c++)
                if (matrix[r][c] == ch)
                    return new int[]{r, c};
        return new int[]{0,0};
    }

    public static void main(String[] args) {
        new Playfair();
    }
}