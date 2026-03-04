package Lab1;

public class Affine {

    static int findinv(int a) {
        for (int i = 1; i < 26; i++) {
            if ((a * i) % 26 == 1)
                return i;
        }
        return -1;
    }

    static int GCD(int a, int b) {
        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    static String encrypt(String string, int a, int b) {
        StringBuilder result = new StringBuilder();

        for (char c : string.toCharArray()) {

            if (Character.isUpperCase(c)) {
                int x = c - 'A';
                char e = (char) ((a * x + b) % 26 + 'A');
                result.append(e);

            } else if (Character.isLowerCase(c)) {
                int x = c - 'a';
                char e = (char) ((a * x + b) % 26 + 'a');
                result.append(e);

            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    static String decrypt(String string, int a, int b) {
        StringBuilder result = new StringBuilder();
        int a_inv = findinv(a);

        if (GCD(a,26)!=1){
            return "eroooooor";
        }
        for (char c : string.toCharArray()) {

            if (Character.isUpperCase(c)) {
                int y = c - 'A';
                char d = (char) ((a_inv * (y - b + 26) % 26) + 'A');
                result.append(d);

            } else if (Character.isLowerCase(c)) {
                int y = c - 'a';
                char d = (char) ((a_inv * (y - b + 26) % 26) + 'a');
                result.append(d);

            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {

        String msg = "Cs402a_Z";

        String enc = encrypt(msg, 5, 8);
        String dec = decrypt(enc, 5, 8);

        System.out.println("enc: " + enc);
        System.out.println("Dec: " + dec);
    }
}