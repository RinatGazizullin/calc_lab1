package ui.cli.commands;

import exceptions.DataException;
import model.LinearSystem;
import ui.cli.basic.Builder;
import ui.cli.basic.Command;
import ui.cli.basic.Renderer;

public class Set extends Command {
    private final Builder<LinearSystem> linearSystemBuilder;
    private final Renderer<LinearSystem> renderer;
    private final LinearSystem linearSystem;

    public Set(Builder<LinearSystem> linearSystemBuilder, LinearSystem linearSystem,
               Renderer<LinearSystem> renderer) {
        super(Type.SET);
        this.linearSystemBuilder = linearSystemBuilder;
        this.linearSystem = linearSystem;
        this.renderer = renderer;
    }

    @Override
    public Result run(Arguments args) {
        try {
            linearSystemBuilder.build(linearSystem);
            return new Result(renderer.render(linearSystem) + "\nСЛУ успешно изменена!", Result.Code.GOOD);
        } catch (DataException e) {
            return new Result(e.getMessage(), Result.Code.ERROR);
        }
    }
}
