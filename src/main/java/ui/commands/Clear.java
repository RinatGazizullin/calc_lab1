package ui.commands;

import ui.basic.Command;
import ui.processor.UiProcessor;

public class Clear extends Command {
    private final UiProcessor uiProcessor;

    public Clear(UiProcessor uiProcessor) {
        super(Type.CLEAR);
        this.uiProcessor = uiProcessor;
    }

    @Override
    public Result run(Arguments ignored) {
        return new Result("Терминал очищен!", Result.Code.GOOD);
    }
}
