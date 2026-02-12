package ui.commands;

import model.LinearSystem;
import ui.basic.Command;
import ui.basic.Renderer;

import java.math.BigDecimal;

public class Random extends Command {
    private static final double MIN = -10;
    private static final double MAX = 10;
    private final LinearSystem system;
    private final Renderer<LinearSystem> renderer;

    public Random(LinearSystem system, Renderer<LinearSystem> renderer) {
        super(Type.RANDOM);
        this.system = system;
        this.renderer = renderer;
    }

    @Override
    public Result run(Arguments args) {
        double min, max;
        if (args.getArgs().length <= 1) {
            min = MIN;
            max = MAX;
        } else {
            try {
                min = Double.parseDouble(args.getArgs()[0]);
                max = Double.parseDouble(args.getArgs()[1]);
            } catch (NumberFormatException e) {
                return new Result(String.format("<%s> и <%s> должны быть числами",
                        args.getArgs()[0], args.getArgs()[1]), Result.Code.ERROR);
            }
        }

        for (int i = 0; i < system.getCoefficients().getSize(); i++) {
            for (int j = 0; j < system.getCoefficients().getSize(); j++) {
                system.getCoefficients().getMatrix()[i][j] =
                        BigDecimal.valueOf(min + Math.round(Math.random() * (max - min)));
            }
            system.getConstants().getVector()[i] =
                    BigDecimal.valueOf(min + Math.round(Math.random() * (max - min)));
        }

        system.resetSolution();
        return new Result(renderer.render(system)
                + "\nКоэффициенты СЛУ успешно сгенерированы!", Result.Code.GOOD);
    }
}
