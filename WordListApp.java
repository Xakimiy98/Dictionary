import java.io.*;
import java.util.*;

public class WordListApp {
    static File baseFolder = new File("wordlist");
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {

        if (!baseFolder.exists()) {
            System.out.println("Base folder not found");
            return;
        }

        String[] degrees = baseFolder.list();
        if (degrees.length == 0) {
            System.out.println("Any degrees not found");
            return;
        }

        System.out.println("\ndegrees = " + Arrays.toString(degrees));
        System.out.print("Enter degree: ");
        String degree = scanner.nextLine().trim().toLowerCase();

        File degreeFolder = new File(baseFolder, degree);

        if (!degreeFolder.exists()) {
            System.out.println(degree + " folder not found");
            return;
        }

        String[] units = degreeFolder.list();
        if (units.length == 0) {
            System.out.println("Any units not found");
            return;
        }

        System.out.println("\nunits = " + Arrays.toString(units));
        System.out.print("Enter unit file name: ");
        String unit = scanner.nextLine().trim().toLowerCase();

        File unitFile = new File(degreeFolder, unit);

        if (!unitFile.exists()) {
            System.out.println(unit + " file not found");
            return;
        }

        Map<String, List<String>> map = new HashMap<>();
        Map<String, Integer> countMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(unitFile))) {

            String line = null;

            while ((line = reader.readLine()) != null) {
                String[] split = line.split("=");

                if (split.length == 2) {
                    String word = split[0].trim();
                    List<String> translate = Arrays.asList(split[1].trim().split("/"));
                    map.put(word, translate);
                    countMap.put(word, 0);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (map.isEmpty()) {
            System.out.println("Dictionary list empty");
            return;
        }

        System.out.println("\nYOU MUST REMEMBER " + map.size() + " WORDS");

        ArrayList<String> words = new ArrayList<>(map.keySet());

        int correct = 0, incorrect = 0;

        while (!map.isEmpty()) {

            int index = new Random().nextInt(words.size());
            String word = words.get(index);

            if (countMap.get(word) >= 2) {
                words.remove(index);
                map.remove(word);
                countMap.remove(word);
                continue;
            }

            System.out.println("\n ✔ = " + correct + "  |  " + incorrect + " = ❌ \n");

            List<String> translate = map.get(word);
            String temp = word;

            boolean fromEngToUzb = new Random().nextBoolean();
            if (!fromEngToUzb) {
                temp = translate.get(0);
                translate = Arrays.asList(word);
            }

            System.out.println(fromEngToUzb ? "eng -> uzb" : "uzb -> eng");
            System.out.print(temp + " = ");
            String answer = scanner.nextLine().trim();

            if (translate.contains(answer)) {
                System.out.println("Correct 😊");
                correct++;
                countMap.put(word, countMap.get(word) + 1);
            } else {
                incorrect++;
                System.out.println("Incorrect 🤨");
                System.out.println(translate);
            }
        }

        System.out.println("\n ✔ = " + correct + "  |  " + incorrect + " = ❌ \n");
        System.out.println("You remembered all words");
        Thread.sleep(500);
    }

}

