package utils;

import exceptions.SolverException;
import exceptions.SystemException;
import model.LinearSystem;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MatrixUtils {
    public static int makeTriangle(LinearSystem solving)
            throws SolverException, SystemException {
        final int n = solving.getCoefficients().getSize();
        int count = 0;

        for (int i = 0; i < n - 1; i++) {
            boolean found = true;

            if (solving.getCoefficients().getMatrix()[i][i].compareTo(BigDecimal.ZERO) == 0) {
                found = false;
                for (int k = i; k < n; k++) {
                    if (solving.getCoefficients().getMatrix()[k][i].compareTo(BigDecimal.ZERO) != 0) {
                        if (k != i) {
                            count++;
                            solving.swapRows(i, k);
                        }
                        found = true;
                        break;
                    }
                }
            }

            if (!found) {
                throw new SolverException("Матрица вырождена, система не имеет единственного решения");
            }

            for (int k = i + 1; k < n; k++) {
                final BigDecimal c = solving.getCoefficients().getMatrix()[k][i].divide(
                        solving.getCoefficients().getMatrix()[i][i], 20, RoundingMode.HALF_UP);
                solving.addMultiplyRow(c, i, k);
            }
        }
        return count;
    }
}
