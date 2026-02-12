package ui.cli.commands;

import model.LinearSystem;
import ui.cli.basic.Command;
import ui.cli.basic.Renderer;

public class Show extends Command {
    private final LinearSystem linearSystem;
    private final Renderer<LinearSystem> renderer;

    public Show(LinearSystem linearSystem, Renderer<LinearSystem> renderer) {
        super(Type.SHOW);
        this.linearSystem = linearSystem;
        this.renderer = renderer;
    }

    @Override
    public Result run(Arguments ignored) {
        return new Result(renderer.render(linearSystem), Result.Code.GOOD);
    }
}
