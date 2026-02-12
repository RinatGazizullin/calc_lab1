package ui.cli.builders;

import exceptions.DataException;
import model.LinearSystem;
import ui.cli.basic.Builder;
import ui.cli.processor.UiProcessor;
import java.math.BigDecimal;

public class LinearSystemBuilder implements Builder<LinearSystem> {
    private static final int MAX = 3;
    private final UiProcessor uiProcessor;

    public LinearSystemBuilder(UiProcessor uiProcessor) {
        this.uiProcessor = uiProcessor;
    }

    @Override
    public void build(LinearSystem linearSystem) throws DataException {
        final BigDecimal[][] matrix = linearSystem.getCoefficients().getMatrix();
        final BigDecimal[] vector = linearSystem.getConstants().getVector();

        uiProcessor.renderText("Введите соответствующие коэфициенты!",
                UiProcessor.Message.CLEAR_LAST, true);

        for (int i = 0; i < matrix.length; i++) {
            final StringBuilder builder = new StringBuilder();
            builder.append(String.format("%d) ", i + 1));

            boolean doNew = true;
            for (int j = 0; j < vector.length; j++) {
                builder.append(String.format(doNew ? "x%d * " : " + x%d * ", j + 1));

                boolean result = false;
                for (int k = 0; k < MAX; k++) {
                    uiProcessor.renderText(builder.toString(), doNew ?
                            UiProcessor.Message.NEW_LINE : UiProcessor.Message.IGNORE_LAST, false);
                    if (doNew) {
                        doNew = false;
                    }

                    final String s = uiProcessor.readString();
                    try {
                        final BigDecimal value = new BigDecimal(s);
                        builder.append(value);
                        matrix[i][j] = value;
                        result = true;
                        break;
                    } catch (NumberFormatException e) {
                        uiProcessor.renderError("Значение <%s> должно быть числом",
                                UiProcessor.Message.IGNORE_LAST);
                        doNew = true;
                    }
                }

                if (!result) {
                    throw new DataException("СЛУ не была собрана полностью");
                }
            }

            builder.append(" = ");

            boolean result = false;
            for (int k = 0; k < MAX; k++) {
                uiProcessor.renderText(builder.toString(), UiProcessor.Message.IGNORE_LAST, false);

                final String s = uiProcessor.readString();
                try {
                    final BigDecimal value = new BigDecimal(s);
                    builder.append(value);
                    vector[i] = value;
                    result = true;
                    break;
                } catch (NumberFormatException e) {
                    uiProcessor.renderError("Значение <%s> должно быть числом", UiProcessor.Message.IGNORE_LAST);
                }
            }

            if (!result) {
                throw new DataException("СЛУ не была собрана полностью");
            }

            uiProcessor.renderText(builder.toString(), UiProcessor.Message.IGNORE_LAST, true);
        }
    }
}
