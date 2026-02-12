package ui.commands;

import basic.Solver;
import exceptions.SolverException;
import model.LinearSystem;
import model.Vector;
import ui.basic.Command;
import ui.basic.Renderer;

public class Solve extends Command {
    private final Solver<LinearSystem> solver;
    private final Renderer<Vector> renderer;
    private final LinearSystem system;

    public Solve(Solver<LinearSystem> solver, Renderer<Vector> renderer, LinearSystem system) {
        super(Type.SOLVE);
        this.solver = solver;
        this.renderer = renderer;
        this.system = system;
    }

    @Override
    public Result run(Arguments ignored) {
        try {
            final var solve = solver.solve(system);
            system.setSolution(solve);
            return new Result(renderer.render(solve), Result.Code.GOOD);
        } catch (SolverException e) {
            return new Result(e.getMessage(), Result.Code.ERROR);
        }
    }
}
