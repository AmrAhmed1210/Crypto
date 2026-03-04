package algorithms;

import javax.swing.*;
import java.awt.*;

public class RailFenceCipherGUI extends JFrame {

    private JTextField inputField;
    private JTextField railsField;
    private JTextArea outputArea;
    private JCheckBox showVisualization;

    public RailFenceCipherGUI() {

        setTitle("Rail Fence Cipher - Encryption & Decryption");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10,10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel title = new JLabel("Rail Fence Cipher (Zigzag Cipher)", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(title, BorderLayout.NORTH);

        // Center Panel
        JPanel center = new JPanel(new GridLayout(3,1,10,10));

        inputField = new JTextField();
        railsField = new JTextField("3",5);
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Text"));
        inputPanel.add(inputField);

        JPanel railsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        railsPanel.setBorder(BorderFactory.createTitledBorder("Number of Rails"));
        railsPanel.add(new JLabel("Rails (>=2): "));
        railsPanel.add(railsField);

        showVisualization = new JCheckBox("Show visualization", true);
        railsPanel.add(showVisualization);

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder("Output"));
        outputPanel.add(new JScrollPane(outputArea));

        center.add(inputPanel);
        center.add(railsPanel);
        center.add(outputPanel);

        mainPanel.add(center, BorderLayout.CENTER);

        // Buttons
        JPanel buttons = new JPanel();

        JButton encryptBtn = new JButton("E - Encrypt");
        JButton decryptBtn = new JButton("D - Decrypt");

        buttons.add(encryptBtn);
        buttons.add(decryptBtn);

        mainPanel.add(buttons, BorderLayout.SOUTH);

        add(mainPanel);

        encryptBtn.addActionListener(e -> process(true));
        decryptBtn.addActionListener(e -> process(false));
    }

    private void process(boolean encrypt) {

        String text = inputField.getText().trim();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter text");
            return;
        }

        int rails;
        try {
            rails = Integer.parseInt(railsField.getText().trim());
            if (rails < 2) throw new NumberFormatException();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Rails must be number >= 2");
            return;
        }

        String result;
        String output;

        if (encrypt) {
            result = encryptRailFence(text, rails);
            output = "Encrypted Text:\n" + result;
        } else {
            result = decryptRailFence(text, rails);
            output = "Decrypted Text:\n" + result;
        }

        if (showVisualization.isSelected()) {
            output += "\n\nPattern:\n" + visualize(text, rails);
        }

        outputArea.setText(output);
    }

    private String encryptRailFence(String text, int rails) {

        StringBuilder[] rail = new StringBuilder[rails];
        for (int i = 0; i < rails; i++)
            rail[i] = new StringBuilder();

        int row = 0, direction = 1;

        for (char c : text.toCharArray()) {

            rail[row].append(c);

            if (row == 0) direction = 1;
            else if (row == rails - 1) direction = -1;

            row += direction;
        }

        StringBuilder result = new StringBuilder();
        for (StringBuilder r : rail)
            result.append(r);

        return result.toString();
    }

    private String decryptRailFence(String cipher, int rails) {

        char[][] fence = new char[rails][cipher.length()];

        int row = 0, direction = 1;


        for (int i = 0; i < cipher.length(); i++) {

            fence[row][i] = '*';

            if (row == 0) direction = 1;
            else if (row == rails - 1) direction = -1;

            row += direction;
        }


        int index = 0;
        for (int i = 0; i < rails; i++)
            for (int j = 0; j < cipher.length(); j++)
                if (fence[i][j] == '*')
                    fence[i][j] = cipher.charAt(index++);


        StringBuilder result = new StringBuilder();
        row = 0;
        direction = 1;

        for (int i = 0; i < cipher.length(); i++) {

            result.append(fence[row][i]);

            if (row == 0) direction = 1;
            else if (row == rails - 1) direction = -1;

            row += direction;
        }

        return result.toString();
    }

    private String visualize(String text, int rails) {

        char[][] fence = new char[rails][text.length()];

        for (int i = 0; i < rails; i++)
            for (int j = 0; j < text.length(); j++)
                fence[i][j] = ' ';

        int row = 0, direction = 1;

        for (int i = 0; i < text.length(); i++) {

            fence[row][i] = text.charAt(i);

            if (row == 0) direction = 1;
            else if (row == rails - 1) direction = -1;

            row += direction;
        }

        StringBuilder visual = new StringBuilder();
        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < text.length(); j++)
                visual.append(fence[i][j]).append(' ');
            visual.append("\n");
        }

        return visual.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RailFenceCipherGUI().setVisible(true));
    }
}