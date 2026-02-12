package ui.processor;

import basic.Solver;
import ui.commands.*;
import ui.commands.Random;
import ui.commands.Set;
import exceptions.DataException;
import exceptions.SystemException;
import model.LinearSystem;
import ui.basic.Command;
import ui.basic.Renderer;
import ui.builders.ChangeSystemBuilder;
import ui.builders.LinearSystemBuilder;
import ui.render.LinearSystemRenderer;
import ui.render.VectorSolutionRenderer;
import java.util.*;

public class CommandLineCore {
    private final Map<Command.Type, Command> commands;
    private final UiProcessor uiProcessor;

    public CommandLineCore(Solver<LinearSystem> solver) throws DataException {
        try {
            this.commands = new HashMap<>();
            this.uiProcessor = new UiProcessor();
            init(solver, LinearSystem.empty(2));
        } catch (SystemException e) {
            throw new DataException(String.format("Возникла неожиданная ошибка: %s",
                    e.getMessage()));
        }
    }

    private void init(Solver<LinearSystem> solver,
                      LinearSystem linearSystem) {
        final Renderer<LinearSystem> linearSystemRenderer = new LinearSystemRenderer();

        commands.put(Command.Type.BUILD, new Build(linearSystemRenderer,
                new LinearSystemBuilder(uiProcessor),
                linearSystem));
        commands.put(Command.Type.CLEAR, new Clear(uiProcessor));
        commands.put(Command.Type.DET, new Det(linearSystem));
        commands.put(Command.Type.EXIT, new Exit(uiProcessor));
        commands.put(Command.Type.HELP, new Help());
        commands.put(Command.Type.RANDOM, new Random(linearSystem,
                linearSystemRenderer));
        commands.put(Command.Type.SET, new Set(
                new ChangeSystemBuilder(uiProcessor,
                linearSystemRenderer),
                linearSystem,
                linearSystemRenderer));
        commands.put(Command.Type.SHOW, new Show(linearSystem,
                linearSystemRenderer));
        commands.put(Command.Type.SIZE, new Size(linearSystem));
        commands.put(Command.Type.SOLVE, new Solve(solver,
                new VectorSolutionRenderer(), linearSystem));
    }

    public void start() {
        uiProcessor.renderText("Для вывода списка команда введите <help>",
                UiProcessor.Message.FIRST_LINE, true);

        while (true) {
            final String[] input = uiProcessor.readString().trim().split("\\s+");
            if (input.length == 0) {
                uiProcessor.clearLast();
            } else {
                final Optional<Command.Type> type = Command.Type.fromString(input[0]);
                Command.Result result;
                if (type.isPresent()) {
                    result = commands.get(type.get()).run(
                            new Command.Arguments(Arrays.copyOfRange(input, 1, input.length)));
                } else {
                    uiProcessor.renderError(
                            String.format("Команда <%s> не обнаружена", input[0]),
                            UiProcessor.Message.CLEAR_LAST);
                    continue;
                }

                boolean isEnd = false;
                switch (result.getResult()) {
                    case GOOD:
                        uiProcessor.renderText(result.getMessage(),
                                UiProcessor.Message.CLEAR_LAST, true);
                        break;
                    case ERROR:
                        uiProcessor.renderError(result.getMessage(),
                                UiProcessor.Message.CLEAR_LAST);
                        break;
                    case EXIT:
                        uiProcessor.renderText(result.getMessage(),
                                UiProcessor.Message.CLEAR_LAST, true);
                        isEnd = true;
                        break;
                }

                if (isEnd) break;
            }
        }
    }
}
