package algorithms;

import javax.swing.*;
import java.awt.*;
import java.security.SecureRandom;

public class OneTimePadGUI extends JFrame {

    private JTextField inputField;
    private JTextField keyField;
    private JTextArea outputArea;

    private static final String CHARSET =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int MOD = CHARSET.length();

    public OneTimePadGUI() {

        setTitle("One-Time Pad Cipher - Encryption & Decryption");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10,10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel title = new JLabel("One-Time Pad Cipher", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(3,1,10,10));

        inputField = new JTextField();
        keyField = new JTextField();
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Text"));
        inputPanel.add(inputField);

        JPanel keyPanel = new JPanel(new BorderLayout());
        keyPanel.setBorder(BorderFactory.createTitledBorder("Key (Same length as text)"));
        keyPanel.add(keyField);

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder("Output"));
        outputPanel.add(new JScrollPane(outputArea));

        center.add(inputPanel);
        center.add(keyPanel);
        center.add(outputPanel);

        mainPanel.add(center, BorderLayout.CENTER);

        JPanel buttons = new JPanel();

        JButton encryptBtn = new JButton("E - Encrypt");
        JButton decryptBtn = new JButton("D - Decrypt");
        JButton generateKeyBtn = new JButton("Generate Random Key");

        buttons.add(encryptBtn);
        buttons.add(decryptBtn);
        buttons.add(generateKeyBtn);

        mainPanel.add(buttons, BorderLayout.SOUTH);

        add(mainPanel);

        encryptBtn.addActionListener(e -> process(true));
        decryptBtn.addActionListener(e -> process(false));
        generateKeyBtn.addActionListener(e -> generateKey());
    }


    private void generateKey() {
        String text = inputField.getText();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter text first");
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

        String text = inputField.getText();
        String key = keyField.getText();

        if (text.isEmpty() || key.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter text and key");
            return;
        }

        if (text.length() != key.length()) {
            JOptionPane.showMessageDialog(this,
                    "Key must be same length as text");
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

        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i++) {

            char p = plaintext.charAt(i);
            char k = key.charAt(i);

            int pIndex = CHARSET.indexOf(p);
            int kIndex = CHARSET.indexOf(k);

            if (pIndex != -1 && kIndex != -1) {
                int cIndex = (pIndex + kIndex) % MOD;
                ciphertext.append(CHARSET.charAt(cIndex));
            } else {
                ciphertext.append(p);
            }
        }

        return ciphertext.toString();
    }

    private String decrypt(String ciphertext, String key) {

        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i++) {

            char c = ciphertext.charAt(i);
            char k = key.charAt(i);

            int cIndex = CHARSET.indexOf(c);
            int kIndex = CHARSET.indexOf(k);

            if (cIndex != -1 && kIndex != -1) {
                int pIndex = (cIndex - kIndex + MOD) % MOD;
                plaintext.append(CHARSET.charAt(pIndex));
            } else {
                plaintext.append(c);
            }
        }

        return plaintext.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new OneTimePadGUI().setVisible(true));
    }
}

