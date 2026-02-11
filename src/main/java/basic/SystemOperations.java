package basic;

import exceptions.SystemException;

import java.math.BigDecimal;

public interface SystemOperations {
    void swapRows(int row1, int row2) throws SystemException;
    void multiplyScalar(BigDecimal scalar, int row) throws SystemException;
    void divideScalar(BigDecimal scalar, int row) throws SystemException;
    void addMultiplyRow(BigDecimal scalar, int row1, int row2) throws SystemException;
    void changeSize(int size) throws SystemException;
}
