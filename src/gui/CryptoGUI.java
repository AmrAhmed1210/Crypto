//package gui;
//
//import algorithms.*;
//import utils.FileManager;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.io.IOException;
//
//public class CryptoGUI extends JFrame {
//
//    private JTextArea inputArea;
//    private JTextField keyField;
//    private JComboBox<String> algorithmBox;
//    private JTextField fileInputField;
//    private JTextField fileOutputField;
//
//    public CryptoGUI() {
//        setTitle("Crypto System");
//        setSize(600, 500);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        inputArea = new JTextArea();
//        keyField = new JTextField();
//        fileInputField = new JTextField();
//        fileOutputField = new JTextField();
//        algorithmBox = new JComboBox<>(new String[]{
//                "Vigenere", "One Time Pad", "Rail Fence", "Row Transposition",
//                "Monoalphabetic", "Playfair", "Hill"
//        });
//
//        JButton encryptBtn = new JButton("Encrypt");
//        JButton decryptBtn = new JButton("Decrypt");
//        JButton readFileBtn = new JButton("Read File");
//        JButton writeFileBtn = new JButton("Write File");
//
//        encryptBtn.addActionListener((ActionEvent e) -> process(true));
//        decryptBtn.addActionListener((ActionEvent e) -> process(false));
//        readFileBtn.addActionListener((ActionEvent e) -> readFile());
//        writeFileBtn.addActionListener((ActionEvent e) -> writeFile());
//
//        setLayout(new BorderLayout());
//        add(new JScrollPane(inputArea), BorderLayout.CENTER);
//
//        JPanel bottom = new JPanel(new GridLayout(10,1));
//        bottom.add(new JLabel("Key (if needed):"));
//        bottom.add(keyField);
//        bottom.add(new JLabel("Algorithm:"));
//        bottom.add(algorithmBox);
//        bottom.add(encryptBtn);
//        bottom.add(decryptBtn);
//        bottom.add(new JLabel("Input File Path:"));
//        bottom.add(fileInputField);
//        bottom.add(readFileBtn);
//        bottom.add(new JLabel("Output File Path:"));
//        bottom.add(fileOutputField);
//        bottom.add(writeFileBtn);
//
//        add(bottom, BorderLayout.EAST);
//    }
//
//    private void readFile() {
//        try {
//            String content = FileManager.readFromFile(fileInputField.getText());
//            inputArea.setText(content);
//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(this, "Error reading file: "+e.getMessage());
//        }
//    }
//
//    private void writeFile() {
//        try {
//            FileManager.writeToFile(fileOutputField.getText(), inputArea.getText());
//            JOptionPane.showMessageDialog(this, "File written successfully!");
//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(this, "Error writing file: "+e.getMessage());
//        }
//    }
//
//    private void process(boolean encrypt) {
//        String text = inputArea.getText();
//        String key = keyField.getText();
//        String algo = (String) algorithmBox.getSelectedItem();
//        String result = "";
//
//        try {
//            switch(algo) {
//                case "Vigenere":
//                    result = encrypt ? Vigenere.encrypt(text,key) : Vigenere.decrypt(text,key);
//                    break;
////                case "One Time Pad":
////                    result = encrypt ? OneTimePad.encrypt(text,key) : OneTimePad.decrypt(text,key);
////                    break;
////                case "Rail Fence":
////                    int railKey = Integer.parseInt(key);
////                    result = encrypt ? RailFence.encrypt(text, railKey) : RailFence.decrypt(text, railKey);
////                    break;
////                case "Row Transposition":
////                    result = RowTransposition.encrypt(text,key); // For simplicity decrypt not added
////                    break;
//                case "Monoalphabetic":
//                    result = encrypt ? Monoalphabetic.encrypt(text,key) : Monoalphabetic.decrypt(text,key);
//                    break;
//                case "Playfair":
//                    result = encrypt ? Playfair.encrypt(text,key) : Playfair.decrypt(text,key);
//                    break;
//                case "Hill":
//                    result = encrypt ? Hill.encrypt(text,key) : Hill.decrypt(text,key);
//                    break;
//            }
//        } catch(Exception ex){
//            JOptionPane.showMessageDialog(this, "Error: "+ex.getMessage());
//        }
//
//        inputArea.setText(result);
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new CryptoGUI().setVisible(true));
//    }
//}