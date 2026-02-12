package ui.render;

import model.LinearSystem;
import ui.basic.Renderer;

import java.text.NumberFormat;
import java.util.stream.IntStream;

public class LinearSystemRenderer implements Renderer<LinearSystem> {
    private static final NumberFormat FORMAT = NumberFormat.getNumberInstance();

    static {
        FORMAT.setMaximumFractionDigits(0);
        FORMAT.setGroupingUsed(true);
    }

    @Override
    public String render(LinearSystem linearSystem) {
        final var data = new Object() {
            private final StringBuilder builder = new StringBuilder();
            private boolean firstNumber = true;
            private boolean firstLine = true;
        };

        IntStream.range(0, linearSystem.getCoefficients().getSize()).forEach(i -> {
            if (data.firstLine) {
                data.firstLine = false;
            } else {
                data.builder.append("\n");
            }
            data.builder.append(String.format("%d) ", i + 1));
            IntStream.range(0, linearSystem.getCoefficients().getSize()).forEach(j -> {
                if (data.firstNumber) {
                    data.firstNumber = false;
                } else {
                    data.builder.append(" + ");
                }
                data.builder.append(String.format("x%d * ", j + 1))
                        .append(linearSystem.getCoefficients().getMatrix()[i][j].stripTrailingZeros());
            });
            data.firstNumber = true;
            data.builder.append(" = ").append(linearSystem.getConstants().getVector()[i]);
        });

        return data.builder.toString();
    }
}
