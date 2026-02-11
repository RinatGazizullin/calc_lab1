package ui.cli.commands;

import lombok.Getter;
import ui.cli.basic.Arguments;
import ui.cli.basic.Result;

public abstract class Command {
    private final Type type;

    protected Command(Type type) {
        this.type = type;
    }

    public abstract Result run(Arguments args);

    @Getter
    public enum Type {
        CLEAR("clear", "Очистить терминал"),
        SHOW("show", "Показать СЛУ"),
        SIZE("size", "Задать размер <size>"),
        SET("set", "Ввести коэффициенты СЛУ"),
        SOLVE("solve", "Решить СЛУ"),
        HELP("help", "Вывести справку по командам"),
        MODE("mode", "Смена режима вывода");

        private final String name;
        private final String description;
        Type(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }
}
