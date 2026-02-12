package ui.cli.commands;

import ui.cli.basic.Command;
import ui.cli.processor.UiProcessor;

public class Exit extends Command {
    private final UiProcessor uiProcessor;

    public Exit(UiProcessor uiProcessor) {
        super(Type.EXIT);
        this.uiProcessor = uiProcessor;
    }

    @Override
    public Result run(Arguments ignored) {
        return new Result("Завершение работы!", Result.Code.EXIT);
    }
}
