package ui.cli.render;

import java.util.stream.IntStream;

public class UIRenderer {
    private static final String ESC = "\033[";
    private static final String SAVE_CURSOR = ESC + "s";
    private static final String RESTORE_CURSOR = ESC + "u";
    private static final String CLEAR_LINE = ESC + "2K";
    private static final String CLEAR_SCREEN = ESC + "2J";
    private static final String MOVE_UP = ESC + "A";
    private static final String MOVE_DOWN = ESC + "B";
    private static final String MOVE_RIGHT = ESC + "C";
    private static final String MOVE_LEFT = ESC + "D";
    private static final String MOVE_HOME = ESC + "H";
    private boolean rememberMode = true;
    private int clearLength = 0;

    public void setRememberMode(boolean rememberMode) {
        this.rememberMode = rememberMode;
        if (!rememberMode) clearLength = 0;
    }

    public void renderMessage(String message, boolean isClear) {
        final int length = message.split("\n", -1).length;
        clearLength(length, isClear);
        System.out.println(message);
    }

    public void renderError(String message, boolean isClear) {
        final int length = message.split("\n", -1).length;
        clearLength(length, isClear);
        System.out.printf("Err: %s\n", message);
    }

    private void clearLength(int length, boolean isClear) {
        if (isClear) {
            IntStream.range(0, clearLength).forEach(i -> System.out.print(MOVE_UP + CLEAR_LINE));
            if (rememberMode) clearLength = length;
        } else {
            if (rememberMode) clearLength += length;
        }
    }
}
