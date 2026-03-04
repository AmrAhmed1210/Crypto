package Lab1;

public class TestCipher {

    public static void main(String[] args) {

        String inputFile = "input.txt";
        String outputFile = "output.txt";
        int shift = 3;

        try {
            String text = FileHandler.readFile(inputFile);
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

            FileHandler.writeFile(outputFile, encrypted);
            System.out.println("Encrypted text saved to " + outputFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
