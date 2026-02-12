package ui.processor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
    private static final int TERMINAL_WIDTH = getTerminalWidth();
    private int clearLength = 0;

    private static int getTerminalWidth() {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{
                    "sh", "-c", "tput cols 2>/dev/null || echo 80"
            });
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line = reader.readLine();
                if (line != null && !line.isEmpty()) {
                    return Integer.parseInt(line.trim());
                }
            }
        } catch (Exception ignored) {
        }
        return 80;
    }

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
                totalLines += (line.length() + TERMINAL_WIDTH - 1) / TERMINAL_WIDTH;
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
