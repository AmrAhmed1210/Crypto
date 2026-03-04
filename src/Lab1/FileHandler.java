package Lab1;

import java.io.*;

public class FileHandler {

    public static void writeFile(String path, String text) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(text);
            writer.close();
            System.out.println("File saved successfully!");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static String readFile(String path) {
        StringBuilder content = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return content.toString();
    }
}
