package ui.cli.render;

import model.Vector;
import ui.cli.basic.Renderer;
import java.text.NumberFormat;
import java.util.stream.IntStream;

public class VectorSolutionRenderer implements Renderer<Vector> {
    private static final NumberFormat FORMAT = NumberFormat.getNumberInstance();

    static {
        FORMAT.setMaximumFractionDigits(0);
        FORMAT.setGroupingUsed(true);
    }

    @Override
    public String render(Vector vector) {
        final var data = new Object() {
            private final StringBuilder builder = new StringBuilder();
            private boolean first = true;
        };

        IntStream.range(0, vector.getSize()).forEach(i -> {
            if (data.first) {
                data.first = false;
            } else {
                data.builder.append("\n");
            }
            data.builder.append(String.format("x%d = ", i + 1))
                    .append(vector.getVector()[i]);
        });

        return data.builder.toString();
    }
}
