package ui.cli.commands;

import model.LinearSystem;
import ui.cli.basic.Builder;
import ui.cli.basic.Command;

public class Set extends Command {
    private final Builder<LinearSystem> linearSystemBuilder;
    private final LinearSystem linearSystem;

    public Set(Builder<LinearSystem> linearSystemBuilder, LinearSystem linearSystem) {
        super(Type.SET);
        this.linearSystemBuilder = linearSystemBuilder;
        this.linearSystem = linearSystem;
    }

    @Override
    public Result run(Arguments args) {
        return new Result("In progress", Result.Code.ERROR);
    }
}
