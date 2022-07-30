package flashcards;

import java.io.*;
import java.util.*;


public class Main {
    public static Map<String, FlashCard> flashCards = new HashMap<>();
    public static Scanner scanner = new Scanner(System.in);
    public static List<String> logs = new ArrayList<>();
    public static boolean export = false;

    public static void main(String[] args) {
        String exportFile = "";
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-import")) {
                    importFile(args[i + 1]);
                }
                if (args[i].equals("-export")) {
                    export = true;
                    exportFile = args[i + 1];
                }
            }
        }

        while (true) {
            System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            logs.add("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            String input = scanner.nextLine();
            logs.add(input);
            switch (input) {
                case "add":
                    add();
                    continue;
                case "remove":
                    remove();
                    continue;
                case "import":
                    importFile();
                    continue;
                case "export":
                    exportFile();
                    continue;
                case "ask":
                    ask();
                    continue;
                case "log":
                    log();
                    continue;
                case "hardest card":
                    hardestCard();
                    continue;
                case "reset stats":
                    resetStats();
                    continue;
                default:
                    if (export) {
                        exportFile(exportFile);
                        return;
                    }
                    System.out.println("Bye bye!");
                    return;
            }
        }

    }

    public static void add() {
        System.out.println("The card:");
        logs.add("The card:");
        String term = scanner.nextLine();
        logs.add(term);
        if (flashCards.containsKey(term)) {
            System.out.printf("The card \"%s\" already exists.\n", term);
            logs.add("The card \"" + term + "\" already exists.");
            return;
        }
        System.out.println("The definition of the card:");
        logs.add("The definition of the card:");
        String definition = scanner.nextLine();
        logs.add(definition);
        for (var x : flashCards.entrySet()) {
            if (x.getValue().getDefinition().equals(definition)) {
                System.out.printf("The definition \"%s\" already exists.\n", definition);
                logs.add("The definition \"" + definition + "\" already exists.");
                return;
            }
        }
        flashCards.put(term, new FlashCard(term, definition));
        System.out.printf("The pair (\"%s\":\"%s\") has been added.\n", term, definition);
        logs.add("The pair (\"" + term + "\" : \"" + definition + "\") has been added");
    }

    public static void remove() {
        System.out.println("Which card?");
        logs.add("Which card?");
        String card = scanner.nextLine();
        logs.add(card);
        if (!flashCards.containsKey(card)) {
            System.out.printf("Can't remove \"%s\": there is no such card.\n", card);
            logs.add("Can't remove \"" + card + "\": there is no such card.");
            return;
        }
        flashCards.remove(card);
        System.out.println("The card has been removed.");
        logs.add("The card has been removed.");
    }

    public static void importFile() {
        System.out.println("File name:");
        logs.add("File name:");
        String name = scanner.nextLine();
        logs.add(name);
        File file = new File(name);
        int count = 0;
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNext()) {
                String[] x = new String[3];
                x[0] = sc.nextLine();
                x[1] = sc.nextLine();
                x[2] = sc.nextLine();
                sc.nextLine();
                flashCards.put(x[0], new FlashCard(x[0], x[1], Integer.parseInt(x[2])));
                count++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            logs.add("File not found.");
            return;
        }
        System.out.printf("%d cards have been loaded.\n", count);
        logs.add(count + " cards have been loaded.");
    }

    public static void importFile(String name) {
        File file = new File(name);
        int count = 0;
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNext()) {
                String[] x = new String[3];
                x[0] = sc.nextLine();
                x[1] = sc.nextLine();
                x[2] = sc.nextLine();
                sc.nextLine();
                flashCards.put(x[0], new FlashCard(x[0], x[1], Integer.parseInt(x[2])));
                count++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            logs.add("File not found.");
            return;
        }
        System.out.printf("%d cards have been loaded.\n", count);
        logs.add(count + " cards have been loaded.");
    }

    public static void exportFile() {
        System.out.println("File name:");
        logs.add("File name");
        String name = scanner.nextLine();
        logs.add(name);
        File file = new File(name);
        try (PrintWriter writer = new PrintWriter(file)) {
            for (var x : flashCards.entrySet()) {
                writer.println(x.getKey() + "\n" + x.getValue().getDefinition() + "\n" + x.getValue().getMistakes()
                + "\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            logs.add("File not found.");
            return;
        }
        System.out.printf("%d cards have been saved.\n", flashCards.size());
        logs.add(flashCards.size() + "cards have been saved.");
    }

    public static void exportFile(String name) {
        File file = new File(name);
        try (PrintWriter writer = new PrintWriter(file)) {
            for (var x : flashCards.entrySet()) {
                writer.println(x.getKey() + "\n" + x.getValue().getDefinition() + "\n" + x.getValue().getMistakes()
                        + "\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            logs.add("File not found.");
            return;
        }
        System.out.println("Bye bye!");
        logs.add("Bye bye!");
        System.out.printf("%d cards have been saved.\n", flashCards.size());
        logs.add(flashCards.size() + "cards have been saved.");
    }

    public static void ask() {
        String[] terms = flashCards.keySet().toString().replace("[", "").replace("]", "").split(", ");
        System.out.println("How many times to ask?");
        logs.add("How many times to ask?");
        int n = scanner.nextInt();
        logs.add(String.valueOf(n));
        scanner.nextLine();
        Random random = new Random();

        for (int j = 1; j <= n; j++) {
            int mistakes = 0;
            boolean found = false;
            String correctAnswer = "";
            String term = terms[random.nextInt(terms.length)];
            System.out.printf("Print the definition of \"%s\":\n", term);
            logs.add("Print the definition of \"" + term + "\":");
            String answer = scanner.nextLine();
            logs.add(answer);
            correctAnswer = flashCards.get(term).getDefinition();

            if (answer.equals(correctAnswer)) {
                System.out.println("Correct!");
                logs.add("Correct!");
                found = true;
                continue;
            }
            for (var x : flashCards.entrySet()) {
                if (x.getValue().getDefinition().equals(answer)) {
                    System.out.printf("Wrong. The right answer is \"%s\", "
                            + "but your definition is correct for \"%s\".\n", correctAnswer, x.getKey());
                    logs.add("Wrong. The right answer is \"" + correctAnswer + "\", but your definition is correct for \"" +
                            x.getKey() + "\".");
                    flashCards.get(term).setMistakes(++mistakes);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.printf("Wrong. The right answer is \"%s\".\n", correctAnswer);
                logs.add("Wrong. The right answer is \"" + correctAnswer + "\".");
                flashCards.get(term).setMistakes(++mistakes);
            }
        }
    }

    public static void log() {
        System.out.println("File name:");
        logs.add("File name:");
        String name = scanner.nextLine();
        logs.add(name);
        logs.add("The log has been saved.");
        logs.add("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");

        File file = new File(name);
        try (PrintWriter writer = new PrintWriter(file)) {
            for (String x : logs) {
                writer.println(x);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            logs.add("File not found.");
            return;
        }
        System.out.println("The log has been saved.");
        logs.add("The log has been saved.");

    }

    public static void hardestCard() {
        int errorCount = 0;
        String hardest = "";
        for (var x : flashCards.entrySet()) {
            if (x.getValue().getMistakes() > errorCount) {
                errorCount = x.getValue().getMistakes();
            }
        }
        for (var x : flashCards.entrySet()) {
            if (x.getValue().getMistakes() == errorCount) {
                hardest += x.getKey() + "  ";
            }
        }
        String[] hardestArr = hardest.split("  ");

        if (errorCount == 0) {
            System.out.println("There are no cards with errors.");
            logs.add("There are no cards with errors.");
            return;
        }

        if (hardestArr.length == 1) {
            System.out.printf("The hardest card is \"%s\". You have %d errors answering it.\n", hardestArr[0], errorCount);
            logs.add("The hardest card is \"" + hardestArr[0] + "\". You have " + errorCount + " errors answering it.");
        } else if (hardestArr.length > 1) {
            String print = "";
            for (int i = 0; i < hardestArr.length; i++) {
                print += ", \"" + hardestArr[i] + "\"";
            }
            System.out.printf("The hardest cards are %s. You have %d errors answering them.\n",
                    print.replaceFirst(",", "").replaceFirst(" ", ""), errorCount);
            logs.add("The hardest cards are " + print.replaceFirst(",", "").replaceFirst(" ", "") +
                    ". You have " + errorCount + " errors answering them.");
        }
    }

    public static void resetStats() {
        for (var x : flashCards.entrySet()) {
            x.getValue().eraseMistakes();
        }
        System.out.println("Card statistics have been reset.");
        logs.add("Card statistics have been reset.");
    }
}