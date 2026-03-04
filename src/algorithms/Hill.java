package algorithms;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Hill extends JFrame {

    JTextArea inputArea = new JTextArea(5, 30);
    JTextArea outputArea = new JTextArea(5, 30);
    JTextField keyField = new JTextField(20);

    public Hill() {

        setTitle("Hill Cipher");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(new JLabel("Input Text:"));
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        mainPanel.add(new JScrollPane(inputArea));
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));

        mainPanel.add(new JLabel("Key (numbers separated by space):"));
        mainPanel.add(keyField);
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton encBtn = new JButton("Encrypt");
        JButton decBtn = new JButton("Decrypt");
        buttonPanel.add(encBtn);
        buttonPanel.add(decBtn);
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));

        mainPanel.add(new JLabel("Output Text:"));
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setEditable(false);
        mainPanel.add(new JScrollPane(outputArea));

        add(mainPanel);

        encBtn.addActionListener(e -> process(true));
        decBtn.addActionListener(e -> process(false));

        setVisible(true);
    }

    private void process(boolean encrypt) {
        try {
            int[][] keyMatrix = parseKey(keyField.getText());
            int det = mod(determinant(keyMatrix),26);

            if (gcd(det,26) != 1) {
                outputArea.setText("Error Key matrix not invertible modulo 26");
                return;
            }

            int[][] inverse = inverseMatrix(keyMatrix);

            System.out.println("Inverse Matrix:");
            for(int i=0;i<inverse.length;i++){
                for(int j=0;j<inverse.length;j++){
                    System.out.print(inverse[i][j]+" ");
                }
                System.out.println();
            }

            outputArea.setText(applyHillCipher(inputArea.getText(), keyMatrix, encrypt));

        } catch(Exception ex) {
            outputArea.setText("Error Invalid key or input");
        }
    }

    private int[][] parseKey(String keyText) {
        String[] parts = keyText.trim().split("\s+");
        int N = (int)Math.sqrt(parts.length);
        if(N*N != parts.length) throw new RuntimeException();
        int[][] matrix = new int[N][N];
        for(int i=0;i<parts.length;i++)
            matrix[i/N][i%N] = Integer.parseInt(parts[i]);
        return matrix;
    }

    private String applyHillCipher(String text, int[][] keyMatrix, boolean encrypt) {

        ArrayList<Integer> letters = new ArrayList<>();
        for(char ch : text.toCharArray())
            if(Character.isLetter(ch))
                letters.add(Character.toUpperCase(ch)-'A');

        int N = keyMatrix.length;
        while(letters.size()%N!=0)
            letters.add('X'-'A');

        int[][] matrixToUse = encrypt ? keyMatrix : inverseMatrix(keyMatrix);
        StringBuilder result = new StringBuilder();

        for(int i=0;i<letters.size();i+=N){
            for(int row=0;row<N;row++){
                int sum=0;
                for(int col=0;col<N;col++)
                    sum+=matrixToUse[row][col]*letters.get(i+col);
                result.append((char)(mod(sum,26)+'A'));
            }
        }

        return result.toString();
    }

    private int determinant(int[][] matrix){
        int N = matrix.length;
        if(N==1) return matrix[0][0];

        int det=0;
        for(int col=0;col<N;col++){
            det += Math.pow(-1,col)*matrix[0][col]*determinant(minor(matrix,0,col));
        }
        return det;
    }

    private int[][] minor(int[][] matrix,int row,int col){
        int N = matrix.length;
        int[][] minor = new int[N-1][N-1];
        int r=0;
        for(int i=0;i<N;i++){
            if(i==row) continue;
            int c=0;
            for(int j=0;j<N;j++){
                if(j==col) continue;
                minor[r][c++] = matrix[i][j];
            }
            r++;
        }
        return minor;
    }

    private int[][] inverseMatrix(int[][] matrix){
        int N = matrix.length;
        int det = mod(determinant(matrix),26);
        int detInv = modInverse(det,26);

        int[][] adj = new int[N][N];

        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                int sign = ((i+j)%2==0)?1:-1;
                adj[j][i] = mod(sign*determinant(minor(matrix,i,j)),26);
            }
        }

        int[][] inv = new int[N][N];
        for(int i=0;i<N;i++)
            for(int j=0;j<N;j++)
                inv[i][j] = mod(adj[i][j]*detInv,26);

        return inv;
    }

    private int mod(int a,int m){
        a%=m;
        if(a<0) a+=m;
        return a;
    }

    private int gcd(int a,int b){
        return b==0?a:gcd(b,a%b);
    }

    private int modInverse(int a,int m){
        for(int x=1;x<m;x++)
            if((a*x)%m==1) return x;
        throw new RuntimeException();
    }

    public static void main(String[] args) {
        new Hill();
    }
}

//2×2 ==> 3 3 2 5
//3×3 ==> 6 24 1 1 16 10 20 17 15