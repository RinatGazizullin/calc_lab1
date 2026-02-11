package model;

import basic.SystemOperations;
import exceptions.DataException;
import exceptions.SystemException;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class LinearSystem implements Cloneable, SystemOperations {
    private Matrix coefficients;
    private Vector constants;
    private Vector solution;

    public LinearSystem(Matrix matrix, Vector constants) throws DataException {
        this.coefficients = matrix;
        this.constants = constants;
        if (this.coefficients.getSize() != this.constants.getSize()) {
            throw new DataException(Errors.DIFFERENT_LENGTH.message);
        }
        this.solution = Vector.empty(this.coefficients.getSize());
    }

    @Override
    public void swapRows(int row1, int row2) throws SystemException {
        coefficients.swapRows(row1, row2);
        constants.swapRows(row1, row2);
    }

    @Override
    public void multiplyScalar(BigDecimal scalar, int row) throws SystemException {
        coefficients.multiplyScalar(scalar, row);
        constants.multiplyScalar(scalar, row);
    }

    @Override
    public void divideScalar(BigDecimal scalar, int row) throws SystemException {
        coefficients.divideScalar(scalar, row);
        constants.divideScalar(scalar, row);
    }

    @Override
    public void addMultiplyRow(BigDecimal scalar, int row1, int row2) throws SystemException {
        coefficients.addMultiplyRow(scalar, row1, row2);
        constants.addMultiplyRow(scalar, row1, row2);
    }

    @Override
    public void changeSize(int size) throws SystemException {
        coefficients.changeSize(size);
        constants.changeSize(size);
        solution.changeSize(size);
    }

    @Override
    public LinearSystem clone() throws CloneNotSupportedException {
        final LinearSystem cloned = (LinearSystem) super.clone();

        cloned.coefficients = this.coefficients.clone();
        cloned.constants = this.constants.clone();
        cloned.solution = this.solution.clone();

        return cloned;
    }

    private enum Errors {
        DIFFERENT_LENGTH("Коэффициенты и константы не совпадают по длине");

        private final String message;
        Errors(String message) {
            this.message = message;
        }
    }
}
