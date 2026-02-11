package exceptions;

/**
 * Ошибка данных.
 */
public class DataException extends Exception {
    /**
     * Стандартный конструктор.
     *
     * @param message Сообщение ошибки
     */
    public DataException(String message) {
        super(message);
    }
}
