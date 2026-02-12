package ui.commands;

import exceptions.SolverException;
import exceptions.SystemException;
import model.LinearSystem;
import ui.basic.Command;
import utils.MatrixUtils;

import java.math.BigDecimal;

public class Det extends Command {
    private final LinearSystem linearSystem;

    public Det(LinearSystem linearSystem) {
        super(Type.DET);
        this.linearSystem = linearSystem;
    }

    @Override
    public Result run(Arguments ignored) {
        try {
            final LinearSystem cloned = linearSystem.clone();
            final int count = MatrixUtils.makeTriangle(cloned);

            BigDecimal det = BigDecimal.ONE;
            for (int i = 0; i < cloned.getCoefficients().getSize(); i++) {
                det = det.multiply(cloned.getCoefficients().getMatrix()[i][i]);
            }

            return new Result("Определитель матрицы равен <" + det.multiply(
                    BigDecimal.valueOf(Math.pow(-1, count))) + ">", Result.Code.GOOD);
        } catch (SolverException e) {
            return new Result("Определитель матрицы равен <" + 0 + ">", Result.Code.GOOD);
        } catch (SystemException e) {
            return new Result(e.getMessage(), Result.Code.ERROR);
        } catch (CloneNotSupportedException e) {
            return new Result("Возникла неожиданная ошибка", Result.Code.ERROR);
        }
    }
}
