package solver;

import basic.Solver;
import exceptions.SolverException;
import exceptions.SystemException;
import model.LinearSystem;
import model.Vector;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class GaussSolver implements Solver<LinearSystem> {
    @Override
    public Vector solve(LinearSystem system) throws SolverException {
        try {
            final int n = system.getCoefficients().getSize();
            final LinearSystem solving = system.clone();

            for (int i = 0; i < n - 1; i++) {
                if (solving.getCoefficients().getMatrix()[i][i].compareTo(BigDecimal.ZERO) == 0) {
                    throw new SolverException("Нужна перестановка");
                }

                for (int k = i + 1; k < n; k++) {
                    final BigDecimal c = solving.getCoefficients().getMatrix()[k][i].divide(
                            solving.getCoefficients().getMatrix()[i][i], 20, RoundingMode.HALF_UP);
                    solving.addMultiplyRow(c.negate(), i, k);
                }
            }

            final Vector solution = Vector.empty(n);

            for (int i = n - 1; i >= 0; i--) {
                BigDecimal s = BigDecimal.ZERO;
                for (int j = i + 1; j < n; j++) {
                    s = s.add(solving.getCoefficients().getMatrix()[i][j].multiply(solution.getVector()[j]));
                }

                final BigDecimal a_ii = solving.getCoefficients().getMatrix()[i][i];
                if (a_ii.compareTo(BigDecimal.ZERO) == 0) {
                    throw new SolverException("Нужна перестановка");
                }

                solution.getVector()[i] = (solving.getConstants().getVector()[i].subtract(s))
                        .divide(a_ii, 20, RoundingMode.HALF_UP);
            }

            return solution;
        } catch (SystemException e) {
            throw new SolverException(String.format("Возникла ошибка с СЛУ: %s", e.getMessage()));
        } catch (CloneNotSupportedException e) {
            throw new SolverException("Возникла неизвестная ошибка");
        }
    }
}
