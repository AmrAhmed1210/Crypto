package algorithms;

import javax.swing.*;
import java.awt.*;
import java.security.SecureRandom;

public class OneTimePad extends JFrame {

    private JTextField inputField;
    private JTextField keyField;
    private JTextArea outputArea;

    private static final String CHARSET = "abcdefghijklmnopqrstuvwxyz ";
    private static final int MOD = CHARSET.length();

    public OneTimePad() {

        setTitle("One-Time Pad Cipher");
        setSize(650, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("One-Time Pad Cipher", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        mainPanel.add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(3, 1, 10, 10));

        inputField = new JTextField();
        keyField = new JTextField();
        outputArea = new JTextArea();
        outputArea.setEditable(false);

        JPanel p1 = new JPanel(new BorderLayout());
        p1.setBorder(BorderFactory.createTitledBorder("Input Text"));
        p1.add(inputField);

        JPanel p2 = new JPanel(new BorderLayout());
        p2.setBorder(BorderFactory.createTitledBorder("Key (same length)"));
        p2.add(keyField);

        JPanel p3 = new JPanel(new BorderLayout());
        p3.setBorder(BorderFactory.createTitledBorder("Output"));
        p3.add(new JScrollPane(outputArea));

        center.add(p1);
        center.add(p2);
        center.add(p3);

        mainPanel.add(center, BorderLayout.CENTER);

        JPanel buttons = new JPanel();

        JButton encryptBtn = new JButton("Encrypt");
        JButton decryptBtn = new JButton("Decrypt");
        JButton keyBtn = new JButton("Generate Random Key");

        buttons.add(encryptBtn);
        buttons.add(decryptBtn);
        buttons.add(keyBtn);

        mainPanel.add(buttons, BorderLayout.SOUTH);

        add(mainPanel);

        encryptBtn.addActionListener(e -> process(true));
        decryptBtn.addActionListener(e -> process(false));
        keyBtn.addActionListener(e -> generateKey());
    }

    private boolean isValid(String text) {

        for (char c : text.toCharArray()) {
            if (CHARSET.indexOf(c) == -1) {
                return false;
            }
        }

        return true;
    }

    private void generateKey() {

        String text = inputField.getText().toLowerCase();

        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter text first");
            return;
        }

        if (!isValid(text)) {
            JOptionPane.showMessageDialog(this,
                    "Only lowercase letters and space allowed");
            return;
        }

        SecureRandom random = new SecureRandom();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            int index = random.nextInt(MOD);
            key.append(CHARSET.charAt(index));
        }

        keyField.setText(key.toString());
    }

    private void process(boolean encrypt) {

        String text = inputField.getText().toLowerCase();
        String key = keyField.getText().toLowerCase();

        if (text.isEmpty() || key.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter text and key");
            return;
        }

        if (text.length() != key.length()) {
            JOptionPane.showMessageDialog(this,
                    "Key must be same length as text");
            return;
        }

        if (!isValid(text) || !isValid(key)) {
            JOptionPane.showMessageDialog(this,
                    "Only lowercase letters and space allowed");
            return;
        }

        String result;

        if (encrypt)
            result = encrypt(text, key);
        else
            result = decrypt(text, key);

        outputArea.setText(result);
    }

    private String encrypt(String plaintext, String key) {
        StringBuilder cipher = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i++) {

            int p = CHARSET.indexOf(plaintext.charAt(i));
            int k = CHARSET.indexOf(key.charAt(i));

            int c = (p + k) % MOD;

            cipher.append(CHARSET.charAt(c));
        }

        return cipher.toString();
    }

    private String decrypt(String cipher, String key) {

        StringBuilder plain = new StringBuilder();

        for (int i = 0; i < cipher.length(); i++) {

            int c = CHARSET.indexOf(cipher.charAt(i));
            int k = CHARSET.indexOf(key.charAt(i));

            int p = (c - k + MOD) % MOD;

            plain.append(CHARSET.charAt(p));
        }

        return plain.toString();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new OneTimePad().setVisible(true);
        });

    }
}