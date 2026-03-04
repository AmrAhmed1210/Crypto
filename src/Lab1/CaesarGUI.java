package Lab1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CaesarGUI {
    public static void main(String[] args) {

        // Window
        JFrame frame = new JFrame("Caesar Cipher");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Input text
        JTextField inputField = new JTextField();
        inputField.setBounds(50, 50, 300, 30);
        frame.add(inputField);

        // Shift
        JTextField shiftField = new JTextField();
        shiftField.setBounds(50, 100, 100, 30);
        frame.add(shiftField);

        // Output text
        JTextField outputField = new JTextField();
        outputField.setBounds(50, 200, 300, 30);
        outputField.setEditable(false);
        frame.add(outputField);

        // Encrypt button
        JButton encryptBtn = new JButton("Encrypt");
        encryptBtn.setBounds(50, 150, 100, 30);
        frame.add(encryptBtn);

        // Button action
        encryptBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = inputField.getText();
                int shift = Integer.parseInt(shiftField.getText());
                String encrypted = "";

                for (int i = 0; i < text.length(); i++) {
                    char c = text.charAt(i);
                    if (Character.isUpperCase(c)) {
                        encrypted += (char)((c - 'A' + shift) % 26 + 'A');
                    } else if (Character.isLowerCase(c)) {
                        encrypted += (char)((c - 'a' + shift) % 26 + 'a');
                    } else {
                        encrypted += c;
                    }
                }

                outputField.setText(encrypted);
            }
        });

        frame.setVisible(true);
    }
}
