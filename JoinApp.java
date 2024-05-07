import java.io.*;

public class JoinApp {
    public static void main(String[] args) {

        File f1 = new File("f1.txt");
        File f2 = new File("f2.txt");

        try (
                BufferedReader reader1 = new BufferedReader(new FileReader(f1));
                BufferedReader reader2 = new BufferedReader(new FileReader(f2));
                PrintWriter writer = new PrintWriter("f3.txt");
        ) {

            String line1 = null, line2 = null;

            while ((line1 = reader1.readLine()) != null){
                line2 = reader2.readLine();

                writer.println(line1.trim()+" = "+line2.trim());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
