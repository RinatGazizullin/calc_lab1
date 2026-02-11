package basic;

import exceptions.SolverException;
import model.Vector;

/**
 * Интерфейс для решения СЛУ.
 */
public interface Solver<T> {
    /**
     * Основной метод решения СЛУ.
     *
     * @param t Объект вычислений
     * @return Результат вычислений
     * @throws SolverException Неверные данные
     */
    Vector solve(T t) throws SolverException;
}
