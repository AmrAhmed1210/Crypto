package algorithms;

import javax.swing.*;
import java.awt.*;

public class Vigenere extends JFrame {

    private JTextField inputField = new JTextField();
    private JTextField keyField = new JTextField();
    private JTextArea outputArea = new JTextArea();

    public Vigenere() {

        setTitle("Vigenère Cipher - Encryption & Decryption");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10,10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel title = new JLabel("Vigenère Cipher", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(title, BorderLayout.NORTH);

        // Center Panels
        JPanel center = new JPanel(new GridLayout(3,1,10,10));

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input"));
        inputPanel.add(new JLabel("Enter Text:"), BorderLayout.NORTH);
        inputPanel.add(inputField, BorderLayout.CENTER);

        JPanel keyPanel = new JPanel(new BorderLayout());
        keyPanel.setBorder(BorderFactory.createTitledBorder("Key"));
        keyPanel.add(new JLabel("Enter Key:"), BorderLayout.NORTH);
        keyPanel.add(keyField, BorderLayout.CENTER);

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder("Output"));
        outputArea.setEditable(false);
        outputPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        center.add(inputPanel);
        center.add(keyPanel);
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
        String key  = keyField.getText().trim();

        if (text.isEmpty() || key.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter text and key");
            return;
        }

        if (!key.matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(this, "Key must contain letters only");
            return;
        }

        String result = vigenere(text, key, encrypt);

        if (encrypt) {
            outputArea.setText("Encrypted Text:\n" + result);
        } else {
            outputArea.setText("Decrypted Text:\n" + result);
        }
    }

    private String vigenere(String text, String key, boolean encrypt) {

        StringBuilder result = new StringBuilder();
        key = key.toUpperCase();
        int k = 0;

        for (char c : text.toCharArray()) {

            if (Character.isLetter(c)) {

                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int shift = key.charAt(k % key.length()) - 'A';

                int value = encrypt ?
                        (c - base + shift) % 26 :
                        (c - base - shift + 26) % 26;

                result.append((char)(value + base));
                k++;

            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Vigenere().setVisible(true));
    }
}