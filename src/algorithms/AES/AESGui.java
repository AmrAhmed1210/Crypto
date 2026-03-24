package algorithms.AES;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class AESGui extends JFrame {

    private JTextField keyField;
    private JTextArea inputArea, outputArea;
    private JComboBox<String> keySizeBox;
    private JLabel status;

    public AESGui() {

        setTitle("AES Cipher");
        setSize(650, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout(10,10));
        main.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        JLabel title = new JLabel("AES Encryption / Decryption", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        main.add(title, BorderLayout.NORTH);

        // Center panel
        JPanel center = new JPanel(new GridLayout(3,1,10,10));

        inputArea = new JTextArea();
        styleArea(inputArea, true);

        outputArea = new JTextArea();
        styleArea(outputArea, false);

        center.add(wrap(inputArea, "Input"));

        // Key panel
        JPanel keyPanel = new JPanel(new BorderLayout());
        keyPanel.setBorder(BorderFactory.createTitledBorder("Key"));

        JPanel topKey = new JPanel(new FlowLayout(FlowLayout.LEFT));

        keySizeBox = new JComboBox<>(new String[]{"128", "192", "256"});
        JButton genBtn = new JButton("Generate");

        topKey.add(new JLabel("Size:"));
        topKey.add(keySizeBox);
        topKey.add(genBtn);

        keyField = new JTextField();
        keyPanel.add(topKey, BorderLayout.NORTH);
        keyPanel.add(keyField, BorderLayout.CENTER);

        center.add(keyPanel);
        center.add(wrap(outputArea, "Output"));

        main.add(center, BorderLayout.CENTER);

        // Buttons
        JPanel bottom = new JPanel(new BorderLayout());

        JPanel btns = new JPanel();

        JButton enc = new JButton("Encrypt");
        JButton dec = new JButton("Decrypt");
        JButton sw = new JButton("Switch");
        JButton clr = new JButton("Clear");

        btns.add(enc);
        btns.add(dec);
        btns.add(sw);
        btns.add(clr);

        bottom.add(btns, BorderLayout.CENTER);

        // Status label
        status = new JLabel("Ready");
        status.setHorizontalAlignment(SwingConstants.CENTER);
        status.setForeground(Color.GRAY);

        bottom.add(status, BorderLayout.SOUTH);

        main.add(bottom, BorderLayout.SOUTH);

        add(main);

        // Actions
        enc.addActionListener(e -> encrypt());
        dec.addActionListener(e -> decrypt());

        sw.addActionListener(e -> {
            inputArea.setText(outputArea.getText());
            outputArea.setText("");
            setStatus("Switched");
        });

        clr.addActionListener(e -> {
            inputArea.setText("");
            outputArea.setText("");
            keyField.setText("");
            setStatus("Cleared");
        });

        genBtn.addActionListener(e -> generateKey());
    }

    // Style text area
    private void styleArea(JTextArea area, boolean editable) {
        area.setEditable(editable);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setMargin(new Insets(10,10,10,10));
    }

    // Wrap area with title
    private JPanel wrap(JTextArea area, String title) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder(title));
        p.add(new JScrollPane(area));
        return p;
    }

    // Update status
    private void setStatus(String msg) {
        status.setText(msg);
    }

    // Encrypt action
    private void encrypt() {
        try {
            String key = keyField.getText();
            String err = AESLogic.validateKey(key);

            if (err != null) {
                setStatus(err);
                return;
            }

            String text = inputArea.getText().trim();
            if (text.isEmpty()) {
                setStatus("Enter text");
                return;
            }

            outputArea.setText(AESLogic.encrypt(text, key));
            setStatus("Encrypted");

        } catch (Exception e) {
            setStatus("Encryption failed");
        }
    }

    // Decrypt action
    private void decrypt() {
        try {
            String key = keyField.getText();
            String err = AESLogic.validateKey(key);

            if (err != null) {
                setStatus(err);
                return;
            }

            String text = inputArea.getText().trim();
            if (text.isEmpty()) {
                setStatus("Enter text");
                return;
            }

            outputArea.setText(AESLogic.decrypt(text, key));
            setStatus("Decrypted");

        } catch (Exception e) {
            setStatus("Invalid input or key");
        }
    }

    // Generate random key
    private void generateKey() {
        int bits = Integer.parseInt((String) keySizeBox.getSelectedItem());
        int len = bits / 8;

        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random r = new Random();

        StringBuilder key = new StringBuilder();
        for (int i = 0; i < len; i++)
            key.append(chars.charAt(r.nextInt(chars.length())));

        keyField.setText(key.toString());
        setStatus("Key generated");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AESGui().setVisible(true));
    }
}