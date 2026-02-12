package ui.cli.processor;

import java.util.Scanner;
import java.util.stream.IntStream;

public class UiProcessor {
    private static final String ESC = "\033[";
    private static final String SAVE_CURSOR = ESC + "s";
    private static final String RESTORE_CURSOR = ESC + "u";
    private static final String CLEAR_LINE = ESC + "K";
    private static final String CLEAR_SCREEN = ESC + "2J";
    private static final String MOVE_UP = ESC + "1A";
    private static final String MOVE_DOWN = ESC + "B";
    private static final String MOVE_RIGHT = ESC + "C";
    private static final String MOVE_LEFT = ESC + "D";
    private static final String MOVE_HOME = ESC + "H";
    private static final Scanner SCANNER = new Scanner(System.in);
    private int clearLength = 0;

    public String readString() {
        clearLength++;
        return SCANNER.nextLine();
    }

    public void clearAllLast() {
        IntStream.range(0, clearLength).forEach(i -> clearLast());
    }

    public void clearLast() {
        System.out.print(MOVE_UP + CLEAR_LINE);
    }

    public void clearScreen() {
        System.out.print(CLEAR_SCREEN);
    }

    public void renderError(String text, Message type) {
        renderText(String.format("Err: %s", text), type, true);
    }

    private int countRealLines(String text) {
        if (text == null || text.isEmpty()) return 0;

        final String cleanText = text.replaceAll("\033\\[[;\\d]*[A-Za-z]", "");
        final String[] explicitLines = cleanText.split("\n", -1);
        int totalLines = 0;

        for (String line : explicitLines) {
            if (line.isEmpty()) {
                totalLines++;
            } else {
                totalLines += (line.length() + 79) / 80;
            }
        }

        return totalLines;
    }

    public void renderText(String text, Message type, boolean need) {
        //final int length = text.split("\n", -1).length;
        final int length = countRealLines(text);

        if (!need) clearLength = Math.max(0, clearLength - 1);

        switch (type) {
            case CLEAR_LAST:
                clearAllLast();
                clearLength = length;
                break;
            case IGNORE_LAST:
                clearLast();
                clearLength += length - 1;
                break;
            case CLEAR_ALL:
                clearScreen();
                clearLength = length;
                break;
            case NEW_LINE:
                clearLength += length;
                break;
        }

        System.out.print(text + (need ? "\n" : ""));
    }

    public enum Message {
        CLEAR_LAST,
        IGNORE_LAST,
        CLEAR_ALL,
        NEW_LINE,
        FIRST_LINE
    }
}
