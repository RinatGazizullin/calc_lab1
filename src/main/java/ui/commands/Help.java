package ui.commands;

import ui.basic.Command;
import java.util.Arrays;

public class Help extends Command {
    public Help() {
        super(Type.HELP);
    }

    @Override
    public Result run(Arguments ignored) {
        final var data = new Object() {
            private final StringBuilder builder = new StringBuilder();
            private boolean first = true;
            private int counter = 1;
        };

        Arrays.stream(Type.values()).forEach(type -> {
            if (data.first) {
                data.first = false;
            } else {
                data.builder.append("\n");
            }
            data.builder.append(String.format("%d) %s - %s", data.counter++,
                    type.getName(), type.getDescription()));
        });

        return new Result(data.builder.toString(), Result.Code.GOOD);
    }
}
