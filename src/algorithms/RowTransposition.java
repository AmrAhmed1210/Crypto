package algorithms;

import javax.swing.*;
import java.awt.*;

public class RowTransposition extends JFrame {

    private JTextField inputField;
    private JTextField keyField;
    private JTextArea outputArea;
    private JButton encryptButton;
    private JButton decryptButton;

    public RowTransposition() {

        setTitle("Row Transposition Cipher");
        setSize(800, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Row Transposition Cipher", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Text"));
        inputField = new JTextField();
        inputPanel.add(inputField);
        gbc.gridx = 0; gbc.gridy = 0;
        centerPanel.add(inputPanel, gbc);

        JPanel keyPanel = new JPanel(new BorderLayout());
        keyPanel.setBorder(BorderFactory.createTitledBorder("Key (Numbers only e.g., 3,1,4,2)"));
        keyField = new JTextField("3,1,4,2");
        keyPanel.add(keyField);
        gbc.gridy = 1;
        centerPanel.add(keyPanel, gbc);

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder("Output"));
        outputArea = new JTextArea(15, 50);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        outputArea.setEditable(false);
        outputPanel.add(new JScrollPane(outputArea));
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        centerPanel.add(outputPanel, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        encryptButton = new JButton("Encrypt");
        encryptButton.setPreferredSize(new Dimension(120, 40));

        decryptButton = new JButton("Decrypt");
        decryptButton.setPreferredSize(new Dimension(120, 40));

        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        encryptButton.addActionListener(e -> process(true));
        decryptButton.addActionListener(e -> process(false));
    }

    private void process(boolean encrypt) {

        String text = inputField.getText();
        String keyText = keyField.getText().trim();

        if (text.isEmpty() || keyText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter text and key");
            return;
        }

        if (!keyText.matches("[0-9,]+")) {
            JOptionPane.showMessageDialog(this, "Key must contain numbers separated by commas only");
            return;
        }

        try {

            String[] parts = keyText.split(",");
            int[] key = new int[parts.length];

            for (int i = 0; i < parts.length; i++)
                key[i] = Integer.parseInt(parts[i].trim());

            if (!isValidKey(key)) {
                JOptionPane.showMessageDialog(this, "Invalid key format");
                return;
            }

            String result;

            if (encrypt)
                result = encryptRow(text, key);
            else
                result = decryptRow(text, key);

            outputArea.setText(result);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error in key format");
        }
    }

    private boolean isValidKey(int[] key) {
        boolean[] used = new boolean[key.length + 1];
        for (int k : key) {
            if (k < 1 || k > key.length || used[k])
                return false;
            used[k] = true;
        }
        return true;
    }

    private String encryptRow(String text, int[] key) {

        int cols = key.length;
        char padding = 'X';
        int rows = (int) Math.ceil((double) text.length() / cols);

        char[][] matrix = new char[rows][cols];
        int index = 0;

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                if (index < text.length())
                    matrix[i][j] = text.charAt(index++);
                else
                    matrix[i][j] = padding;

        StringBuilder result = new StringBuilder();

        for (int k = 1; k <= cols; k++)
            for (int c = 0; c < cols; c++)
                if (key[c] == k)
                    for (int r = 0; r < rows; r++)
                        result.append(matrix[r][c]);

        return result.toString();
    }

    private String decryptRow(String cipher, int[] key) {

        int cols = key.length;
        int rows = (int) Math.ceil((double) cipher.length() / cols);
        char padding = 'X';

        char[][] matrix = new char[rows][cols];

        int[] inverse = new int[cols];
        for (int i = 0; i < cols; i++)
            inverse[key[i] - 1] = i;

        int index = 0;
        for (int k = 1; k <= cols; k++) {
            int col = inverse[k - 1];
            for (int r = 0; r < rows; r++)
                matrix[r][col] = cipher.charAt(index++);
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                result.append(matrix[i][j]);

        String decrypted = result.toString();
        while (decrypted.endsWith(String.valueOf(padding)))
            decrypted = decrypted.substring(0, decrypted.length() - 1);

        return decrypted;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RowTransposition().setVisible(true));
    }
}