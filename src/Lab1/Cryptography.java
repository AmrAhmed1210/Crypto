package Lab1;

public class Cryptography {

    public static String caesar(String text, int key) {
        String result = "";
        for (char ch : text.toUpperCase().toCharArray()) {
            if (Character.isLetter(ch)) {
                result += (char) (((ch - 'A' + key) % 26) + 'A');
            } else {
                result += ch;
            }
        }
        return result;
    }

    public static String monoalphabetic(String text, String keyMap) {
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String result = "";
        for (char ch : text.toUpperCase().toCharArray()) {
            int index = alpha.indexOf(ch);
            if (index != -1) {
                result += keyMap.charAt(index);
            } else {
                result += ch;
            }
        }
        return result;
    }

    public static String hill(String text, int[][] key) {
        String result = "";
        text = text.toUpperCase().replace(" ", "");
        if (text.length() % 2 != 0) text += "X";
        for (int i = 0; i < text.length(); i += 2) {
            int p1 = text.charAt(i) - 'A';
            int p2 = text.charAt(i + 1) - 'A';
            int c1 = (key[0][0] * p1 + key[0][1] * p2) % 26;
            int c2 = (key[1][0] * p1 + key[1][1] * p2) % 26;
            result += (char) (c1 + 'A');
            result += (char) (c2 + 'A');
        }
        return result;
    }

    public static String playfairFormat(String text) {
        text = text.toUpperCase().replace("J", "I").replace(" ", "");
        StringBuilder sb = new StringBuilder(text);
        for (int i = 0; i < sb.length() - 1; i += 2) {
            if (sb.charAt(i) == sb.charAt(i + 1)) {
                sb.insert(i + 1, 'X');
            }
        }
        if (sb.length() % 2 != 0) sb.append('X');
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("Caesar: " + caesar("HELLO", 3));
        System.out.println("Mono: " + monoalphabetic("HELLO", "QWERTYUIOPASDFGHJKLZXCVBNM"));
        System.out.println("Hill: " + hill("HELP", new int[][]{{3, 3}, {2, 5}}));
        System.out.println("Playfair Format: " + playfairFormat("BALLOON"));
    }
}