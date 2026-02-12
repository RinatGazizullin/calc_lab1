package ui.commands;

import exceptions.DataException;
import model.LinearSystem;
import ui.basic.Builder;
import ui.basic.Command;
import ui.basic.Renderer;

public class Build extends Command {
    private final Renderer<LinearSystem> linearSystemRenderer;
    private final Builder<LinearSystem> linearSystemBuilder;
    private final LinearSystem linearSystem;

    public Build(Renderer<LinearSystem> linearSystemRenderer,
                 Builder<LinearSystem> linearSystemBuilder,
                 LinearSystem linearSystem) {
        super(Type.BUILD);
        this.linearSystemRenderer = linearSystemRenderer;
        this.linearSystemBuilder = linearSystemBuilder;
        this.linearSystem = linearSystem;
    }

    @Override
    public Result run(Arguments args) {
        try {
            linearSystemBuilder.build(linearSystem);
            return new Result(linearSystemRenderer.render(linearSystem)
                    + "\nСЛУ успешно изменена!", Result.Code.GOOD);
        } catch (DataException e) {
            return new Result(e.getMessage(), Result.Code.ERROR);
        }
    }
}
