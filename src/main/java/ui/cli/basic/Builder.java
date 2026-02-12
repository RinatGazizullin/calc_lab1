package ui.cli.basic;

import exceptions.DataException;

public interface Builder<T> {
    void build(T t) throws DataException;
}
