package Lab1;

public class AttackCipher {

    public static void main(String[] args) {

        String text = "Fv402d_C";
        for (int shift = 0; shift < 26; shift++) {
            String decrypted = "";
            for (int i = 0; i < text.length(); i++) {

                char c = text.charAt(i);

                if (Character.isUpperCase(c)) {
                    decrypted += (char) ((c - 'A' + 26 - shift) % 26 + 'A');
                } else if (Character.isLowerCase(c)) {
                    decrypted += (char) ((c - 'a' + 26 - shift) % 26 + 'a');
                } else {
                    decrypted += c;
                }
            }
            System.out.println("Shift: " + shift + " " + decrypted);
        }
    }
}
