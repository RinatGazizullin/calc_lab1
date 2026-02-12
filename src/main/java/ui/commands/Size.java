package ui.commands;

import exceptions.SystemException;
import model.LinearSystem;
import ui.basic.Command;

public class Size extends Command {
    private final LinearSystem linearSystem;

    public Size(LinearSystem linearSystem) {
        super(Type.SIZE);
        this.linearSystem = linearSystem;
    }

    @Override
    public Result run(Arguments args) {
        if (args.getArgs().length == 0) {
            return new Result("Не был введен аргумент", Result.Code.ERROR);
        }
        final String s = args.getArgs()[0];
        try {
            final int size = Integer.parseInt(s);
            linearSystem.changeSize(size);
            return new Result("Размерность изменена!", Result.Code.GOOD);
        } catch (NumberFormatException e) {
            return new Result(String.format("Значение <%s> должно быть целочисленным числом", s),
                    Result.Code.ERROR);
        } catch (SystemException e) {
            return new Result(String.format("Не удалось изменить размерность: %s", e.getMessage()),
                    Result.Code.ERROR);
        }
    }
}
