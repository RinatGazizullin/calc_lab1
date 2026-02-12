package ui.cli.basic;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public abstract class Command {
    private final Type type;

    protected Command(Type type) {
        this.type = type;
    }

    public abstract Result run(Arguments args);

    @Getter
    public enum Type {
        BUILD("build", "Ввести все коэффицикеты СЛУ"),
        CLEAR("clear", "Очистить терминал"),
        EXIT("exit", "Завершить работу"),
        HELP("help", "Вывести справку по командам"),
        SET("set", "Изменить коэффициент СЛУ"),
        SHOW("show", "Показать СЛУ"),
        SIZE("size", "Задать размер <size>"),
        SOLVE("solve", "Решить СЛУ");

        private final String name;
        private final String description;

        Type(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public static Optional<Type> fromString(String name) {
            return Arrays.stream(values()).filter(t -> t.name.equalsIgnoreCase(name)).findFirst();
        }
    }

    @Getter
    public static class Result {
        private final String message;
        private final Code result;

        public Result(String message, Code result) {
            this.message = message;
            this.result = result;
        }

        public enum Code {
            GOOD,
            ERROR,
            EXIT
        }
    }

    @Getter
    public static class Arguments {
        private final String[] args;

        public Arguments(String[] args) {
            this.args = args;
        }
    }
}
