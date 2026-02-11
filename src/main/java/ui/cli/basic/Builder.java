package ui.cli.builders;

import exceptions.DataException;

public interface Builder<T> {
    T build(int size) throws DataException;
}
