package model;

import basic.SystemOperations;
import exceptions.SystemException;
import lombok.Getter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.IntStream;

@Getter
public class Vector implements Cloneable, SystemOperations {
    private static final int MAX_SIZE = 20;
    private BigDecimal[] vector;
    private int size;

    public Vector(BigDecimal[] vector) {
        this.vector = vector;
        size = vector.length;
    }

    public static Vector empty(int size) {
        final BigDecimal[] empty = new BigDecimal[size];

        IntStream.range(0, size).forEach(i -> empty[i] = BigDecimal.ZERO);
        return new Vector(empty);
    }

    @Override
    public void swapRows(int row1, int row2) throws SystemException {
        if (row1 == row2) return;
        if (row1 < 0 || row2 < 0 || row1 >= size || row2 >= size) {
            throw new SystemException(Vector.Errors.VALID.message);
        }
        final BigDecimal tmp = vector[row1];
        vector[row1] = vector[row2];
        vector[row2] = tmp;
    }

    @Override
    public void multiplyScalar(BigDecimal scalar, int row) throws SystemException {
        if (row < 0 || row >= size) {
            throw new SystemException(Vector.Errors.VALID.message);
        }
        vector[row] = scalar.multiply(vector[row]);
    }

    @Override
    public void divideScalar(BigDecimal scalar, int row) throws SystemException {
        if (row < 0 || row >= size) {
            throw new SystemException(Vector.Errors.VALID.message);
        }
        if (scalar.compareTo(BigDecimal.ZERO) == 0) {
            throw new SystemException(Vector.Errors.ZERO.message);
        }
        vector[row] = vector[row].divide(scalar, RoundingMode.HALF_UP);
    }

    @Override
    public void addMultiplyRow(BigDecimal scalar, int row1, int row2) throws SystemException {
        if (row1 < 0 || row2 < 0 || row1 >= size || row2 >= size) {
            throw new SystemException(Vector.Errors.VALID.message);
        }
        vector[row2] = vector[row2].subtract(scalar.multiply(vector[row1]));
    }

    @Override
    public void changeSize(int newSize) throws SystemException {
        if (newSize <= 0 || newSize >= MAX_SIZE) {
            throw new SystemException(Vector.Errors.SIZE.message);
        }

        final BigDecimal[] newVector = new BigDecimal[newSize];
        for (int i = 0; i < newSize; i++) {
                newVector[i] = i < size ? vector[i] : BigDecimal.ZERO;
        }
        this.size = newSize;
        this.vector = newVector;
    }

    @Override
    public Vector clone() throws CloneNotSupportedException {
        final Vector cloned = (Vector) super.clone();
        final BigDecimal[] clonedVector = new BigDecimal[this.size];

        System.arraycopy(this.vector, 0, clonedVector, 0, this.size);
        cloned.vector = clonedVector;

        return cloned;
    }

    private enum Errors {
        VALID("Выход за границы размерности"),
        ZERO("Недопустимо деление на ноль"),
        SIZE("Недопустимый размер вектора");

        private final String message;
        Errors(String message) {
            this.message = message;
        }
    }
}
