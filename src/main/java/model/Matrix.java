package model;

import basic.SystemOperations;
import exceptions.SystemException;
import lombok.Getter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.IntStream;

@Getter
public class Matrix implements Cloneable, SystemOperations {
    private static final int MAX_SIZE = 20;
    private BigDecimal[][] matrix;
    private int size;

    public Matrix(BigDecimal[][] matrix) throws SystemException {
        if (matrix[0].length != matrix.length) {
            throw new SystemException(Errors.SQUARE.message);
        }
        this.matrix = matrix;
        this.size = matrix.length;
    }

    public static Matrix empty(int size) throws SystemException {
        final BigDecimal[][] empty = new BigDecimal[size][size];

        IntStream.range(0, size).forEach(i ->
                IntStream.range(0, size).forEach(j -> empty[i][j] = BigDecimal.ZERO));
        return new Matrix(empty);
    }

    @Override
    public void swapRows(int row1, int row2) throws SystemException {
        if (row1 == row2) return;
        if (row1 < 0 || row2 < 0 || row1 >= size || row2 >= size) {
            throw new SystemException(Errors.VALID.message);
        }
        final BigDecimal[] tmp = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = tmp;
    }

    @Override
    public void multiplyScalar(BigDecimal scalar, int row) throws SystemException {
        if (row < 0 || row >= size) {
            throw new SystemException(Errors.VALID.message);
        }
        IntStream.range(0, size).forEach(i ->
                matrix[row][i] = scalar.multiply(matrix[row][i]));
    }

    @Override
    public void divideScalar(BigDecimal scalar, int row) throws SystemException {
        if (row < 0 || row >= size) {
            throw new SystemException(Errors.VALID.message);
        }
        if (scalar.compareTo(BigDecimal.ZERO) == 0) {
            throw new SystemException(Errors.ZERO.message);
        }
        IntStream.range(0, size).forEach(i ->
                matrix[row][i] = matrix[row][i].divide(scalar, 20, RoundingMode.HALF_UP));
    }

    @Override
    public void addMultiplyRow(BigDecimal scalar, int row1, int row2) throws SystemException {
        if (row1 < 0 || row2 < 0 || row1 >= size || row2 >= size) {
            throw new SystemException(Errors.VALID.message);
        }
        IntStream.range(0, size).forEach(i ->
                matrix[row2][i] = matrix[row2][i].subtract(scalar.multiply(matrix[row1][i])));
    }

    @Override
    public void changeSize(int newSize) throws SystemException {
        if (newSize <= 0 || newSize >= MAX_SIZE) {
            throw new SystemException(Errors.SIZE.message);
        }

        final BigDecimal[][] newMatrix = new BigDecimal[newSize][newSize];
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                newMatrix[i][j] = i < size && j < size ? matrix[i][j] : BigDecimal.ZERO;
            }
        }

        this.size = newSize;
        this.matrix = newMatrix;
    }

    @Override
    public Matrix clone() throws CloneNotSupportedException {
        final Matrix cloned = (Matrix) super.clone();
        final BigDecimal[][] clonedMatrix = new BigDecimal[this.size][this.size];

        for (int i = 0; i < this.size; i++) {
            System.arraycopy(this.matrix[i], 0, clonedMatrix[i], 0, this.size);
        }
        cloned.matrix = clonedMatrix;

        return cloned;
    }

    private enum Errors {
        SQUARE("Матрица должна быть квадратной"),
        ZERO("Недопустимо деление на ноль"),
        VALID("Выход за границы размерности"),
        SIZE("Недопустимый размер матрицы");

        private final String message;
        Errors(String message) {
            this.message = message;
        }
    }
}
